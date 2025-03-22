package uk.ac.ucl.model;

import java.util.ArrayList;

public class Model 
{
  private NoteManager noteManager;
  private SearchEngine searchEngine;
  private NoteSorter noteSorter;

  private static final Index main = new Index("main");

  public Model()
  {
    this.noteManager = new NoteManager(main);
    this.searchEngine = new SearchEngine();
    this.noteSorter = new NoteSorter();
  }

  public Note newNote() {
    return noteManager.createNote();
  }

  public Note newNote(Index parentCat) {
    return noteManager.createNote(parentCat);
  }

  public ArrayList<Note> resolveNoteQuery(String searchString, String sortedBy,
                                          String order, String searchFor, Index category) {

    ArrayList<Note> noteList = category.getNoteList();

    if (searchString != null && !searchString.isEmpty()) {
      noteList = searchEngine.performSearch(noteList, searchString, searchFor);
    }

    boolean ascending = "asc".equals(order);
    noteSorter.sortNotes(noteList, sortedBy, ascending);

    return noteList;
  }

  public Index getMainCategory() {

    return noteManager.getMainCategory();
  }

  public Index getCategory()
  {
    return noteManager.getMainCategory();
  }

  public Note getNote(int ID)
  {
    return noteManager.getNote(ID);
  }

  public void updateNote(int ID, String title, String summary, String contents)
  {
    noteManager.updateNote(ID, title, summary, contents);
  }

  public void deleteNote(int ID)
  {
    noteManager.deleteNote(ID);
  }

  public boolean noteExists(int ID)
  {
    return noteManager.noteExists(ID);
  }
}

