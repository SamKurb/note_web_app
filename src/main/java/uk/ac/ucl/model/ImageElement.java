package uk.ac.ucl.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.File;

public class ImageElement extends NoteElement
{
    private String filePath;
    private String caption;
    private int width;
    private int height;

    public ImageElement() {
        super(-1, "image");
        this.filePath = "";
        this.caption = "";
    }

    @JsonCreator
    public ImageElement(
            @JsonProperty("ID") int ID,
            @JsonProperty("filePath") String filePath,
            @JsonProperty("caption") String caption)
    {
        super(ID, "image");
        this.filePath = filePath;
        this.caption = caption;
    }

    @JsonProperty("filePath")
    public String getFilePath()
    {
        return filePath;
    }

    @JsonProperty("caption")
    public String getCaption()
    {
        return caption;
    }

    @JsonProperty("width")
    public int getWidth()
    {
        return width;
    }

    @JsonProperty("height")
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
