package uk.ac.ucl.model;
/*
This class is responsible for rendering text into markdown as well as rendering any links as hyperlinks.
 */

public class TextRenderer
{
    public static String renderText(String text) {
        text = renderHyperLinks(text);
        text = renderCodeBlocks(text);
        text = renderHeadings(text);
        text = renderBlockQuotes(text);
        text = renderUnorderedLists(text);
        text = renderHorizontalRules(text);
        text = renderBoldText(text);
        text = renderItalicText(text);
        text = renderInlineCode(text);

        // Formatting new lines
        text = text.replaceAll("(</(h[1-6]|p|li|pre)>)\\s*\\n+", "$1\n");
        text = text.replaceAll("\\n{3,}", "\n\n");
        text = text.replaceAll("(?m)^\\s+$", "");
        text = text.replaceAll("\\n", "<br>");
        return text;
    }



    private static String renderHyperLinks(String text)
    {
        String urlPattern = "https?://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

        return text.replaceAll(urlPattern, "<a href=\"$0\" target=\"_blank\">$0</a>");
    }

    private static String renderBoldText(String text) {
        return text.replaceAll("\\*\\*([^*]+)\\*\\*", "<strong>$1</strong>");
    }

    private static String renderItalicText(String text)
    {
        return text.replaceAll("\\*([^*]+)\\*", "<em>$1</em>");
    }

    private static String renderInlineCode(String text)
    {
        return text.replaceAll("`([^`]+)`", "<code>$1</code>");
    }

    private static String renderHeadings(String text)
    {
        String result = text;
        for (int i = 6; i >= 1; i--)
        {
            String hashes = new String(new char[i]).replace("\0", "#");
            result = result.replaceAll("(?m)^\\s*" + hashes + "\\s+(.+)$",
                    "<h" + i + ">$1</h" + i + ">");
        }
        return result;
    }

    private static String renderBlockQuotes(String text)
    {
        return text.replaceAll("(?m)^>\\s+(.+)$", "<blockquote>$1</blockquote>");
    }

    private static String renderUnorderedLists(String text)
    {
        String[] lines = text.split("\n");
        StringBuilder result = new StringBuilder();
        boolean inList = false;

        for (String line : lines)
        {
            String trimmedLine = line.trim();
            if (trimmedLine.matches("^-\\s+.+"))
            {
                if (!inList)
                {
                    result.append("<ul class=\"markdown-list\">\n");
                    inList = true;
                }
                String content = trimmedLine.replaceAll("^-\\s+(.+)$", "$1");
                result.append("<li>").append(content).append("</li>\n");
            }
            else
            {
                if (inList)
                {
                    result.append("</ul>\n");
                    inList = false;
                }
                result.append(line).append("\n");
            }
        }
        if (inList)
        {
            result.append("</ul>\n");
        }
        return result.toString();
    }

    private static String renderCodeBlocks(String text) {
        return text.replaceAll("```([^`]+)```", "<pre><code>$1</code></pre>");
    }

    private static String renderHorizontalRules(String text) {
        return text.replaceAll("(?m)^([-*]{3,})$", "<hr>");
    }
}
