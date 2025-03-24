package uk.ac.ucl.model;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonDataSaver
{

    private IndexManager indexManager;
    private NoteManager noteManager;


    private final String workingDir = System.getProperty("user.dir") + File.separator + "data";

    private ObjectMapper mapper;

    public JsonDataSaver(IndexManager indexManager, NoteManager noteManager)
    {
        this.mapper = new ObjectMapper();
        this.indexManager = indexManager;
        this.noteManager = noteManager;
    }

    private void deleteAllData(File rootDir)
    {
        if (!rootDir.isDirectory())
        {
            return;
        }

        File[] contents = rootDir.listFiles();

        if (contents != null)
        {
            for (File file : contents)
            {
                if (file.isDirectory())
                {
                    deleteAllData(file);
                }
                else
                {
                    file.delete();
                }
            }
        }

        String rootDirPath = rootDir.getAbsolutePath();

        rootDir.delete();
    }

    private String getSafeName(String name)
    {
        return name.replaceAll("[^0-9a-zA-Z.-]", "_").toLowerCase();
    }

    private String getPathToIndex(Index index)
    {
        int currIndexID = index.getID();
        int currParentID = index.getParentID();

        String currIndexName = getSafeName(index.getName());
        String currFilePath = currIndexName;

        while (Integer.valueOf(currParentID) != -1) // -1 Indicates that there is no parent i.e the current index is the root
        {
            Index currIndex = indexManager.getIndexByID(currParentID);

            currIndexID = currIndex.getID();
            currParentID = currIndex.getParentID();

            currIndexName = getSafeName(currIndex.getName());
            currFilePath = currIndexName + File.separator + currFilePath;
        }
        return currFilePath;
    }

    private void saveNoteData(Note note, String filePath) throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();

        try
        {
            String noteTitle = getSafeName(note.getTitle());
            String fileName = noteTitle + "_" + note.getID() + ".json";

            File noteFile = new File(filePath + File.separator + fileName);
            mapper.writeValue(noteFile, note);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void saveIndexMetaData(Index index, String indexPath) throws IOException
    {
        Map<String, Object> metaData = new HashMap<>();
        metaData.put("id", index.getID());
        metaData.put("name", index.getName());
        metaData.put("parentId", index.getParentID());
        metaData.put("childIndices", index.getChildIndices());

        File metaDataFile = new File(indexPath + File.separator + ".index_" + index.getName() + "_meta_data.json");
        mapper.writeValue(metaDataFile, metaData);
    }

    private void saveIndicesAndNotes(Index rootIndex) throws IOException
    {
        String pathToCurrIndex = workingDir + File.separator + getPathToIndex(rootIndex);
        File indexDir = new File(pathToCurrIndex);
        if (!indexDir.exists())
        {
            boolean created = indexDir.mkdir();
            if (!created)
            {
                System.out.println("Failed to create directory: " + pathToCurrIndex);
                return;
            }
        }

        saveIndexMetaData(rootIndex, pathToCurrIndex);
        for (Note note : rootIndex.getNoteList())
        {
            saveNoteData(note, pathToCurrIndex);
        }


        for (int childID : rootIndex.getChildIndices())
        {
            Index currIndex = indexManager.getIndexByID(childID);
            saveIndicesAndNotes(currIndex);
        }
    }

    private void saveGlobalMetadata(IndexManager indexManager, NoteManager noteManager) throws IOException
    {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("mainIndexId", indexManager.getMainIndex().getID());
        metadata.put("highestIndexId", indexManager.getHighestIndexID()); // You'd need to add this static getter
        metadata.put("highestNoteId", noteManager.getHighestNoteId()); // You'd need to add this getter

        File metadataFile = new File(workingDir + File.separator + "global_metadata.json");
        mapper.writeValue(metadataFile, metadata);
    }

    public void saveData()
    {
        String rootIndexDir = workingDir + File.separator + indexManager.getMainIndex().getName();
        File rootIndexFile = new File(rootIndexDir);
        deleteAllData(rootIndexFile);

        try
        {
            saveGlobalMetadata(indexManager, noteManager);
            Index rootIndex = indexManager.getMainIndex();
            saveIndicesAndNotes(rootIndex);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
