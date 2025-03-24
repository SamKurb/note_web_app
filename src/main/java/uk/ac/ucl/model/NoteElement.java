package uk.ac.ucl.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextElement.class, name = "text"),
        @JsonSubTypes.Type(value = ImageElement.class, name = "image"),
})


abstract class NoteElement
{
    private int ID;
    private String type;

    protected NoteElement(int ID, String type)
    {
        this.ID = ID;
        this.type = type;
    }

    @JsonProperty("ID")
    public int getID()
    {
        return ID;
    }

    @JsonProperty("type")
    public String getType()
    {
        return type;
    }

    abstract String render();
}