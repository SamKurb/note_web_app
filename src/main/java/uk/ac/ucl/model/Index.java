package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Index {
    private String name;

    private Index parentIndex;
    private ArrayList<Index> childIndices;

    private ArrayList<Note> noteList; // All the notes belonging to an index
    private Map<Integer, Note> noteMap; // For fast lookup of notes by ID


    public Index(String name)
    {
        this.name = name;

        this.parentIndex = null;
        this.childIndices = new ArrayList<>();

        this.noteList = new ArrayList<>();
        this.noteMap = new HashMap<>();
    }


    public Index getParent()
    {
        return parentIndex;
    }

    public void addNote(Note note) {
        noteList.add(note);
        noteMap.put(note.getID(), note);
    }


    public Note getNote(int ID)
    {
        return noteMap.get(ID);
    }


    public void removeNote(int ID)
    {
        Note note = noteMap.get(ID);
        noteMap.remove(ID);
        noteList.remove(note);
    }


    public void removeSubIndex(Index child)
    {
        childIndices.remove(child);
    }


    public void addSubIndex(String name)
    {
        Index child = new Index(name);
        childIndices.add(child);
    }

    private void addSubIndex(Index child) {
        child.setParent(this);
        childIndices.add(child);
    }

    public void setParent(Index newParent)
    {
        if (name.equals("main")) {
            return;
        }

        if (childIndices.contains(newParent)) {
            return;
        }

        if (parentIndex != null)
        {
            parentIndex.removeSubIndex(this);
        }

        parentIndex = newParent;
        newParent.addSubIndex(this);
    }

    public boolean hasNoteWithId(int ID)
    {
        return noteMap.containsKey(ID);
    }

    /**
     * Creates and returns a deep copy of the note list, so that encapsulation doesnt get broken if the note list
     * is needed elsewhere
     */
    private ArrayList<Note> cloneNoteList() {
        ArrayList<Note> listClone = new ArrayList<>();
        for (Note note : noteList)
        {
            Note noteCopy = note.clone();
            listClone.add(noteCopy);
        }
        return listClone;
    }


    public ArrayList<Note> getNoteList() {
        return cloneNoteList();
    }


    public String getName() {
        return name;
    }

    /**
     * Returns the list of child indices.
     * This should probably return a defensive copy like getNoteList() does.
     */
    public ArrayList<Index> getChildIndices() {
        return new ArrayList<>(childIndices);
    }
}