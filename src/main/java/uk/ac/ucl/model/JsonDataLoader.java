package uk.ac.ucl.model;

import com.fasterxml.jackson.databind.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class JsonDataLoader {
    private IndexManager indexManager;
    private NoteManager noteManager;
    private final String WORKING_DIR = System.getProperty("user.dir") + File.separator + "data";
    private String ROOT_INDEX_DIR;
    private ObjectMapper mapper;

    public JsonDataLoader(IndexManager indexManager, NoteManager noteManager)
    {
        this.mapper = new ObjectMapper();

        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE);
        mapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        this.indexManager = indexManager;
        this.noteManager = noteManager;
    }

    public boolean loadData()
    {
        try {


            // Then load the main index and all its children
            File metadataFile = new File(WORKING_DIR + File.separator + "global_metadata.json");

            File dataDir = new File(WORKING_DIR);
            if (!dataDir.exists()) {
                dataDir.mkdirs();
                // If this is the first run, just use the default empty model
                return false;
            }

            // Check if metadata file exists
            if (!metadataFile.exists()) {
                // If no metadata exists, this might be first run
                // Just return false to use default empty model
                return false;
            }

            // First load global metadata to set up proper IDs
            loadGlobalMetadata();

            JsonNode metadata = mapper.readTree(metadataFile);
            int mainIndexId = metadata.get("mainIndexId").asInt();

            // Load the index structure first
            loadIndexStructure(mainIndexId);

            // Then load all notes within the indices
            loadAllNotes();

            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private void loadGlobalMetadata() throws IOException
    {
        File metadataFile = new File(WORKING_DIR + File.separator + "global_metadata.json");
        if (!metadataFile.exists())
        {
            return;
        }

        JsonNode metadata = mapper.readTree(metadataFile);
        int highestIndexId = metadata.get("highestIndexId").asInt();
        int highestNoteId = metadata.get("highestNoteId").asInt();

        indexManager.setHighestIndexID(highestIndexId);
        noteManager.setHighestNoteId(highestNoteId);
    }

    private void loadAllNotes() throws IOException
    {

        loadNotesFromDirectory(new File(ROOT_INDEX_DIR));

        for (Note note : noteManager.getAllNotes())
        {
            noteManager.updateNoteCategoryNames(note);
        }
    }

    private void loadNotesFromDirectory(File directory) throws IOException
    {
        if (!directory.isDirectory())
        {
            return;
        }

        File[] files = directory.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                if (file.isFile() && file.getName().endsWith(".json") && !file.getName().startsWith("."))
                {
                    try
                    {

                        Note note = mapper.readValue(file, Note.class);

                        noteManager.addLoadedNote(note);

                        for (int categoryId : note.getCategoryIDs())
                        {
                            Index category = indexManager.getIndexByID(categoryId);
                            if (category != null)
                            {
                                category.addNote(note);
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println("Error loading note file: " + file.getPath());
                        e.printStackTrace();
                    }
                } else if (file.isDirectory()) {
                    loadNotesFromDirectory(file);
                }
            }
        }
    }

    private Index loadIndexFromDirectory(File directory) throws IOException {
        // Find the metadata file
        File[] files = directory.listFiles(file ->
                file.isFile() && file.getName().startsWith(".index_") && file.getName().endsWith("_meta_data.json"));

        if (files == null || files.length == 0) {
            return null;
        }

        // Read the metadata
        JsonNode metadata = mapper.readTree(files[0]);
        int id = metadata.get("id").asInt();
        String name = metadata.get("name").asText();

        // Make sure to handle the parentId field correctly
        int parentId = -1;  // Default value
        if (metadata.has("parentId")) {
            parentId = metadata.get("parentId").asInt();
            // Debug: print the value being loaded
            System.out.println("Loading index " + name + " (ID: " + id + ") with parent ID: " + parentId);
        }

        // Create the index
        Index index = new Index(name, id);
        index.setParentID(parentId);  // Explicitly set the parent ID

        // Add child indices
        if (metadata.has("childIndices")) {
            JsonNode childIndices = metadata.get("childIndices");
            if (childIndices.isArray()) {
                for (JsonNode childId : childIndices) {
                    index.addSubIndex(childId.asInt());
                    // Debug: print child relationships
                    System.out.println("Index " + name + " (ID: " + id + ") has child: " + childId.asInt());
                }
            }
        }

        return index;
    }

    private void loadIndexStructure(int rootIndexId) throws IOException
    {
        // First find and load the root index
        File rootDir = findIndexDirectory(rootIndexId);
        ROOT_INDEX_DIR = rootDir.getAbsolutePath();
        if (rootDir == null || !rootDir.exists()) {
            throw new IOException("Root index directory not found");
        }

        // Load the root index
        Index rootIndex = loadIndexFromDirectory(rootDir);
        if (rootIndex == null) {
            throw new IOException("Failed to load root index");
        }

        // Mark it as main index
        indexManager.setMainIndex(rootIndex);
        indexManager.addLoadedIndex(rootIndex);

        // Now recursively load all child indices
        loadChildIndices(rootIndex);
    }

    private void loadChildIndices(Index parentIndex) throws IOException {
        for (int childId : parentIndex.getChildIndices()) {
            File childDir = findIndexDirectory(childId);
            if (childDir != null && childDir.exists()) {
                Index childIndex = loadIndexFromDirectory(childDir);
                if (childIndex != null) {
                    indexManager.addLoadedIndex(childIndex);
                    loadChildIndices(childIndex); // Recursively load grandchildren
                }
            }
        }
    }

    private File findIndexDirectory(int indexId) throws IOException {
        // Search for the index directory by traversing all directories in the data folder
        File dataDir = new File(WORKING_DIR);
        return findIndexDirectoryRecursive(dataDir, indexId);
    }

    private File findIndexDirectoryRecursive(File directory, int indexId) throws IOException {
        if (!directory.isDirectory()) {
            return null;
        }

        // Check if this directory contains the index metadata file
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().startsWith(".index_") && file.getName().endsWith("_meta_data.json")) {
                    // Found a metadata file, check if it's the index we're looking for
                    JsonNode metadata = mapper.readTree(file);
                    if (metadata.has("id") && metadata.get("id").asInt() == indexId) {
                        return directory;
                    }
                }
            }

            // Not found in this directory, search in subdirectories
            for (File file : files) {
                if (file.isDirectory()) {
                    File result = findIndexDirectoryRecursive(file, indexId);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }

        return null;
    }
    private String getSafeName(String name)
    {
        return name.replaceAll("[^0-9a-zA-Z.-]", "_").toLowerCase();
    }


}