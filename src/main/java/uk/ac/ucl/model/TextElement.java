package uk.ac.ucl.model;

class TextElement extends NoteElement
{
    private String content;

    TextElement(int ID, String content)
    {
        super(ID, "text");
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }

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