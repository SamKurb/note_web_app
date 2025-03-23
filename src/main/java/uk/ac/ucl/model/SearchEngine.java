package uk.ac.ucl.model;

import java.util.ArrayList;

public class SearchEngine
{
    private StringComparer stringComparer;

    public SearchEngine()
    {
        this.stringComparer = new StringComparer();
    }

    public ArrayList<Note> performSearch(ArrayList<Note> noteList, String searchString, String searchTarget) {
        ArrayList<Note> filteredList = new ArrayList<>();
        String searchStringLowerCase = searchString.toLowerCase();

        for (Note note : noteList) {
            String contentToBeSearched = getContentToSearch(note, searchTarget);

            // Try exact match first
            if (stringComparer.containsExactSubstring(searchStringLowerCase, contentToBeSearched)) {
                filteredList.add(note);
                continue;
            }

            // Then try fuzzy matchs
            if (stringComparer.perWordComparison(searchStringLowerCase, contentToBeSearched)) {
                filteredList.add(note);
            }
        }

        return filteredList;
    }

    private String getContentToSearch(Note note, String searchTarget)
    {
        return searchTarget.equals("title") ?
                note.getTitle().toLowerCase() : note.getTextContents().toLowerCase();
    }
}