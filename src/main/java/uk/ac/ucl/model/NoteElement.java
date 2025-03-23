package uk.ac.ucl.model;

abstract class NoteElement
{
    private int ID;
    private String type;

    protected NoteElement(int ID, String type)
    {
        this.ID = ID;
        this.type = type;
    }

    public int getID()
    {
        return ID;
    }

    public String getType()
    {
        return type;
    }

    abstract String render();
}