package uk.ac.ucl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
public class Index
{
    private String name;
    private int ID;

    @JsonProperty("parentId")
    private int parentIndexID;

    @JsonProperty("childIndices")
    private ArrayList<Integer> childIndexIDs;

    @JsonIgnore
    private ArrayList<Note> noteList; // All the notes belonging to an index

    @JsonIgnore
    private Map<Integer, Note> noteMap; // For fast lookup of notes by ID



    public Index(String name, int ID)
    {
        this.name = name;
        this.ID = ID;

        this.parentIndexID = -1; // -1 = no parent
        this.childIndexIDs = new ArrayList<>();

        this.noteList = new ArrayList<>();
        this.noteMap = new HashMap<>();
    }

    @JsonProperty("id")
    public int getID()
    {
        return ID;
    }

    @JsonProperty("parentId")
    public int getParentID()
    {
        return parentIndexID;
    }

    public void setParentID(int parentID)
    {
        this.parentIndexID = parentID;
    }


    public void addNote(Note note){
        noteList.add(note);
        noteMap.put(note.getID(), note);

        note.addCategory(this.getID());
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

    public boolean isMain()
    {
        return ID == 0;
    }

    public void addSubIndex(int childID)
    {
        if (!childIndexIDs.contains(childID))
        {
            childIndexIDs.add(childID);
        }
    }

    public void removeSubIndex(int childId)
    {
        childIndexIDs.remove(Integer.valueOf(childId));
    }



    public boolean hasNoteWithID(int ID)
    {
        return noteMap.containsKey(ID);
    }

    public boolean hasNoteWithTitle(int IDbeingCompared, String titleBeingCompared)
    {
        for (Note note : noteList)
        {
            String currTitle = note.getTitle();
            int currID = note.getID();
            if (currTitle.equals(titleBeingCompared) && IDbeingCompared != currID)
            {
                System.out.println("wut");
                return true;
            }
        }
        System.out.println("wut");
        return false;
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


    public ArrayList<Note> getNoteList()
    {
        return cloneNoteList();
    }


    public String getName() {
        return name;
    }

    /**
     * Returns the list of child indices.
     * This should probably return a defensive copy like getNoteList() does.
     */
    @JsonProperty("childIndices")
    public ArrayList<Integer> getChildIndices()
    {
        return new ArrayList<>(childIndexIDs);
    }

    @JsonProperty("id")
    public void setID(int id) {
        this.ID = id;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("childIndices")
    public void setChildIndices(ArrayList<Integer> childIndices) {
        this.childIndexIDs = new ArrayList<>(childIndices);
    }
}