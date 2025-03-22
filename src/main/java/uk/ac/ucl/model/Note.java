package uk.ac.ucl.model;

public class Note implements Cloneable
{
    private String title;
    private final int ID;
    private String contents;
    private String summary;

    private static int modifiedTracker = 0; // keeps track of how many modifications have occurred globally

    public int modifiedId; // keeps track of which modification the current note last had. if modifiedId = the tracker
                           // then this was the last note modified

    private Index category;

    public Note(int ID, Index category)
    {
        this.ID = ID;
        this.category = category;
        this.title = "note with id: " + this.ID;
        this.contents = "edit me!!!";
        this.summary = "";

        modifiedTracker += 1;
        this.modifiedId = modifiedTracker;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String newTitle)
    {
        title = newTitle;
    }

    public String getContents()
    {
        return contents;
    }

    public void setContents(String newContents)
    {
        contents = newContents;
    }

    public int getID()
    {
        return ID;
    }

    public String getCategoryName()
    {
        return category.getName();
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String newSummary)
    {
        summary = newSummary;
    }

    public void updateModifiedTime()
    {
        modifiedTracker += 1;
        this.modifiedId = modifiedTracker;
        System.out.println(modifiedTracker);
        System.out.println("note id: " + modifiedId);
    }

    public int getModifiedTime()
    {
        return modifiedId;
    }

    public void setModifiedTime(int newModifiedId)
    {
        modifiedId = newModifiedId;
    }

    public Note clone()
    {
        int oldModTracker = modifiedTracker;
        Note clone = new Note(this.ID, this.category);

        clone.setTitle(this.title);
        clone.setContents(this.contents);
        clone.setSummary(this.summary);
        clone.setModifiedTime(this.modifiedId);

        modifiedTracker = oldModTracker;
        return clone;
    }
}
