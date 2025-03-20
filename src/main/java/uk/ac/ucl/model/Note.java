package uk.ac.ucl.model;

public class Note
{
    private String title;
    private int ID;
    public String contents;

    public Note(int ID)
    {
        this.ID = ID;
        this.title = "note with id: " + this.ID;
        this.contents = "edit me!!!";
    }

    public String get_title()
    {
        return title;
    }

    public String get_contents()
    {
        return contents;
    }

    public int get_ID()
    {
        return ID;
    }
}
