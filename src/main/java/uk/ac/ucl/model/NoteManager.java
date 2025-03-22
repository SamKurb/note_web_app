package uk.ac.ucl.model;

public class NoteManager
{
    private Index mainCategory;
    private static int highestNoteId = 0;

    public NoteManager(Index mainCategory) {
        this.mainCategory = mainCategory;
    }

    public Note createNote()
    {
        Note note = new Note(highestNoteId, mainCategory);
        highestNoteId++;
        mainCategory.addNote(note);
        return note;
    }

    public Note createNote(Index parentCat)
    {
        Note note = new Note(highestNoteId, mainCategory);
        parentCat.addNote(note);
        highestNoteId++;
        mainCategory.addNote(note);
        return note;
    }

    public Index getMainCategory()
    {
        return mainCategory;
    }

    public Note getNote(int id)
    {
        return mainCategory.getNote(id);
    }

    public void updateNote(int id, String title, String summary, String contents) {
        Note note = getNote(id);
        note.setTitle(title);
        note.setSummary(summary);
        note.setContents(contents);
        note.updateModifiedTime();
    }

    public void deleteNote(int id) {
        mainCategory.removeNote(id);
    }

    public boolean noteExists(int id) {
        return mainCategory.hasNoteWithId(id);
    }
}