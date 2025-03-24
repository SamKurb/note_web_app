package uk.ac.ucl.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class TextElement extends NoteElement
{
    private String content;

    public TextElement()
    {
        super(-1, "text");
        this.content = "";
    }

    @JsonCreator
    TextElement(@JsonProperty("ID") int ID, @JsonProperty("content") String content)
    {
        super(ID, "text");
        this.content = content;
    }

    @JsonProperty("content")
    public String getContent()
    {
        return content;
    }

    @JsonProperty("content")
    public void setContent(String content)
    {
        this.content = content;
    }

    @Override
    String render()
    {
        return content;
    }
}