package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NoteManager
{
    private IndexManager indexManager;
    private Map<Integer, Note> allNotes; // Central repository of all notes
    private static int highestNoteId = 0;

    private int latestElementID = 0;

    public NoteManager(IndexManager indexManager)
    {
        this.indexManager = indexManager;
        this.allNotes = new HashMap<>();
    }

    public int getHighestNoteId()
    {
        return highestNoteId;
    }

    public Note createNote()
    {
        // Create in main index by default
        return createNote(indexManager.getMainIndex().getID());
    }

    public Note createNote(int categoryID)
    {
        Index category = indexManager.getIndexByID(categoryID);

        if (category == null)
        {
            category = indexManager.getMainIndex();
            categoryID = category.getID();
        }

        Note note = new Note(highestNoteId);
        highestNoteId++;

        TextElement initialContent = new TextElement(highestNoteId++, "");
        note.addElement(initialContent);

        allNotes.put(note.getID(), note);

        addNoteToCategory(note.getID(), categoryID);
        return note;
    }

    public void addNoteToCategory(int noteId, int categoryId) {
        Note note = getNoteById(noteId);
        Index category = indexManager.getIndexByID(categoryId);

        if (note != null && category != null)
        {
            note.addCategory(categoryId);
            category.addNote(note);

            updateNoteCategoryNames(note);
        }
    }

    public void removeNoteFromCategory(int noteId, int categoryId)
    {
        Note note = getNoteById(noteId);
        Index category = indexManager.getIndexByID(categoryId);

        if (note != null && category != null)
        {
            note.removeCategory(categoryId);
            category.removeNote(noteId);
        }
        updateNoteCategoryNames(note);
    }

    public void updateNoteCategoryNames(Note note)
    {
        if (note != null)
        {
            ArrayList<String> categoryNames = new ArrayList<>();
            for (int categoryId : note.getCategoryIDs())
            {
                Index category = indexManager.getIndexByID(categoryId);
                if (category != null)
                {
                    categoryNames.add(category.getName());
                }
            }
            note.setCategoryNames(categoryNames);
        }
    }



    public Note getNoteById(int ID)
    {
        return allNotes.get(ID);
    }

    /*
     * Updates a note, returns true if successfully updated (no other note had the same title)
     * or false otherwise.
     */
    public boolean updateNote(int id, String title, String summary, String textContents, Integer currentIndexID)
    {
        Note note = getNoteById(id);

        Index currNoteIndex = indexManager.getIndexByID(currentIndexID);

        if (currNoteIndex.hasNoteWithTitle(id, title))
        {
            return false;
        }

        if (note != null) {
            note.setTitle(title);
            note.setSummary(summary);
            note.setTextContents(textContents); // This will update or create the text element
            note.updateModifiedTime();
        }
        return true;
    }

    public void deleteNote(int id)
    {
        Note note = getNoteById(id);
        if (note != null)
        {
            ArrayList<Integer> categoryIDs = note.getCategoryIDs();

            for (int catID : categoryIDs)
            {
                removeNoteFromCategory(id, catID);
            }

            allNotes.remove(id);
        }
    }

    public boolean noteExists(int id)
    {
        return allNotes.containsKey(id);
    }

    public void updateNoteCategories(int noteId, ArrayList<Integer> newCategoryIds) {
        Note note = getNoteById(noteId);
        if (note == null) return;

        if (newCategoryIds == null || newCategoryIds.isEmpty())
        {
            newCategoryIds = new ArrayList<>();
            newCategoryIds.add(indexManager.getMainIndex().getID());
        }

        ArrayList<Integer> currentCategories = note.getCategoryIDs();

        removeFromOldCategories(note, currentCategories, newCategoryIds);

        addToNewCategories(note, currentCategories, newCategoryIds);

        ensureNoteHasCategory(note);

        updateNoteCategoryNames(note);
    }

    /**
     * Removes note from categories that are not in the new list
     */
    private void removeFromOldCategories(Note note, ArrayList<Integer> currentCategories,
                                         ArrayList<Integer> newCategoryIds)
    {
        for (int categoryId : new ArrayList<>(currentCategories))
        {
            if (!newCategoryIds.contains(categoryId)) {
                removeNoteFromCategory(note.getID(), categoryId);
            }
        }
    }

    /**
     * Adds note to new categories that weren't in the original list
     */
    private void addToNewCategories(Note note, ArrayList<Integer> currentCategories,
                                    ArrayList<Integer> newCategoryIds)
    {
        for (int categoryId : newCategoryIds)
        {
            if (!currentCategories.contains(categoryId))
            {
                addNoteToCategory(note.getID(), categoryId);
            }
        }
    }

    private void ensureNoteHasCategory(Note note)
    {
        if (note.getCategoryIDs().isEmpty())
        {
            int mainId = indexManager.getMainIndex().getID();
            addNoteToCategory(note.getID(), mainId);
        }
    }

    public ArrayList<Note> getAllNotes()
    {
        return new ArrayList<>(allNotes.values());
    }

    public ArrayList<Note> getNotesInCategory(int categoryId)
    {
        Index category = indexManager.getIndexByID(categoryId);
        if (category != null)
        {
            return category.getNoteList();
        }
        return new ArrayList<>();
    }

}