package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Model 
{
  private IndexManager indexManager;
  private NoteManager noteManager;

  private SearchEngine searchEngine;
  private NoteSorter noteSorter;

  private static final Index main = new Index("main", 0);

  public Model()
  {
    this.indexManager = new IndexManager();
    this.noteManager = new NoteManager(indexManager);
    this.searchEngine = new SearchEngine();
    this.noteSorter = new NoteSorter();
  }

  public void saveAllData()
  {
    JsonDataSaver saver = new JsonDataSaver(indexManager, noteManager);
    saver.saveData();
  }

  public Index getIndexByID(int ID)
  {
    return indexManager.getIndexByID(ID);
  }

  public Index getMainIndex()
  {
    return indexManager.getMainIndex();
  }


  public Note newNote(int categoryID)
  {
    return noteManager.createNote(categoryID);
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

  public Note getNote(int ID)
  {
    return noteManager.getNoteById(ID);
  }

  public boolean updateNote(int ID, String title, String summary, String contents, Integer categoryID)
  {
    return noteManager.updateNote(ID, title, summary, contents, categoryID);
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

