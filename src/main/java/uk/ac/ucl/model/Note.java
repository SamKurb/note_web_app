package uk.ac.ucl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Note implements Cloneable
{
    private String title;

    @JsonProperty("id")
    private final int ID;

    private ArrayList<NoteElement> contents;
    private int latestElementID;

    private String summary;

    private static int modifiedTracker = 0; // keeps track of how many modifications have occurred globally

    public int modifiedId; // keeps track of which modification the current note last had. if modifiedId = the tracker
                           // then this was the last note modified

    private ArrayList<Integer> categoryIDs;
    private transient ArrayList<String> categoryNames;

    public Note()
    {
        this(-1);
    }

    @JsonCreator
    public Note(int ID)
    {
        this.ID = ID;
        this.title = "note with id: " + this.ID;

        this.contents = new ArrayList<>();

        this.summary = "";

        this.categoryIDs = new ArrayList<>();
        this.categoryNames = new ArrayList<>();

        modifiedTracker++;
        this.modifiedId = modifiedTracker;
    }

    @JsonProperty("title")
    public String getTitle()
    {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String newTitle)
    {
        title = newTitle;
    }

    @JsonProperty("contents")
    ArrayList<NoteElement> getContents()
    {
        return new ArrayList<>(contents);
    }

    void addElement(NoteElement element)
    {
        contents.add(element);
        updateModifiedTime();
    }

    @JsonProperty("contents")
    void setContents(ArrayList<NoteElement> contents)
    {
        this.contents = contents;
    }

    boolean removeContentElement(int elementID)
    {
        boolean removed = false;
        for (NoteElement element : contents)
        {
            if (element.getID() == elementID)
            {
                contents.remove(element);
                removed = true;
            }
        }
        if (removed)
        {
            updateModifiedTime();
        }
        return removed;
    }

    NoteElement getContentElementByID(int ID)
    {
        for (NoteElement element : contents)
        {
            if (element.getID() == ID)
            {
                return element;
            }
        }
        return null;
    }

    @JsonProperty("textContents")
    public String getTextContents()
    {
        for (NoteElement element : contents) {
            if (element instanceof TextElement)
            {
                return ((TextElement) element).getContent();
            }
        }
        return "";
    }

    public void setTextContents(String newText)
    {
        for (NoteElement element : contents)
        {
            if (element instanceof TextElement)
            {
                ((TextElement) element).setContent(newText);
                return;
            }
        }

        // If no text element exists, create one
        contents.add(new TextElement(latestElementID++, newText));
    }

    public String renderNote()
    {
        StringBuilder builder = new StringBuilder();
        for (NoteElement element : contents)
        {
            builder.append(element.render());
        }
        return builder.toString();
    }

    @JsonProperty("summary")
    public String getSummary()
    {
        return summary;
    }

    @JsonProperty("summary")
    public void setSummary(String newSummary)
    {
        summary = newSummary;
    }

    @JsonProperty("modifiedId")
    public void updateModifiedTime()
    {
        modifiedTracker += 1;
        this.modifiedId = modifiedTracker;
    }

    @JsonProperty("modifiedId")
    public int getModifiedTime()
    {
        return modifiedId;
    }

    @JsonProperty("id")
    public int getID()
    {
        return ID;
    }

    public ArrayList<String> getCategoryNames()
    {
        return categoryNames;
    }

    @JsonIgnore
    public String getFormattedCategoryNames()
    {
        System.out.println(categoryNames);
        return String.join(", ", categoryNames);
    }

    public void setCategoryNames(ArrayList<String> names)
    {
        System.out.println("hi");
        this.categoryNames = names;
    }

    @JsonProperty("categoryIDs")
    public ArrayList<Integer> getCategoryIDs()
    {
        return new ArrayList<>(categoryIDs);
    }

    @JsonProperty("categoryIDs")
    public void setCategoryIDs(ArrayList<Integer> categoryIDs)
    {
        this.categoryIDs = categoryIDs;
    }

    public void addCategory(int categoryID)
    {
        if (!categoryIDs.contains(categoryID))
        {
            categoryIDs.add(categoryID);
        }
    }

    public void removeCategory(int categoryID)
    {
        categoryIDs.remove(Integer.valueOf(categoryID));
    }

    public boolean isInCategory(int categoryID)
    {
        return categoryIDs.contains(categoryID);
    }



    public void setModifiedTime(int newModifiedId)
    {
        modifiedId = newModifiedId;
    }

    public Note clone()
    {
        int oldModTracker = modifiedTracker;
        Note clone = new Note(this.ID);

        clone.setTitle(this.title);
        clone.setContents(this.contents);
        clone.setSummary(this.summary);
        clone.setModifiedTime(this.modifiedId);
        clone.setCategoryIDs(this.categoryIDs);
        clone.setCategoryNames(new ArrayList<>(this.categoryNames));

        modifiedTracker = oldModTracker;
        return clone;
    }
}
