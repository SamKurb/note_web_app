package uk.ac.ucl.model;

import java.io.File;

public class ImageElement extends NoteElement
{
    private String filePath;
    private String caption;
    private int width;
    private int height;

    ImageElement(int ID, String filePath, String caption)
    {
        super(ID, "image");
        this.filePath = filePath;
        this.caption = caption;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public String getCaption()
    {
        return caption;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    @Override
    String render()
    {
        return "<img src=" + filePath + File.separator + "alt=Image: " + caption + ">";
    }
}
