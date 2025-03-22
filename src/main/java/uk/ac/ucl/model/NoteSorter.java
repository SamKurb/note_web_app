package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.Collections;

public class NoteSorter {
    public void sortNotes(ArrayList<Note> noteList, String sortBy, boolean ascending) {
        switch(sortBy) {
            case "title":
                sortNotesByTitle(noteList, ascending);
                break;
            case "id":
                sortNotesById(noteList, ascending);
                break;
            case "modTime":
                sortNotesByModifiedTime(noteList, ascending);
                break;
            default:
                // Default sort option if needed
                break;
        }
    }

    public void sortNotesByTitle(ArrayList<Note> noteList, boolean ascending)
    {
        Collections.sort(noteList,
                (note1, note2) -> {
                    int comparison = note1.getTitle().compareTo(note2.getTitle());
                    return ascending ? comparison : -comparison;
                });
    }


    public void sortNotesById(ArrayList<Note> noteList, boolean ascending)
        {
            Collections.sort(noteList,
                    (note1, note2) -> {
                        int comparison = Integer.compare(note1.getID(), note2.getID());

                        return ascending ? comparison : -comparison;
                    });
        }

    public void sortNotesByModifiedTime(ArrayList<Note> noteList, boolean oldestFirst)
    {
        Collections.sort(noteList,
                (note1, note2) -> {
                    int comparison = Integer.compare(note1.getModifiedTime(), note2.getModifiedTime());

                    return oldestFirst ? comparison : -comparison;
                });
    }
}