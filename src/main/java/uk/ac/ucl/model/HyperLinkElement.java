package uk.ac.ucl.model;

import java.io.File;

public class HyperLinkElement extends NoteElement
{
    private String url;
    private String displayText;

    HyperLinkElement(int id, String url, String displayText)
    {
        super(id, "link");
        this.url = url;
        this.displayText = displayText;
    }

    public String getUrl()
    {
        return url;
    }
    public String getDisplayText()
    {
        return displayText;
    }

    @Override
    String render() {
        // Return HTML for link
        return "<a href=" + File.separator + url + File.separator + ">" + displayText + "</a>";
    }
}
