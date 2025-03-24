
<%@ page import="java.util.ArrayList" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Note 1 </title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<%@ include file="sidebar.jsp" %>



<div id="content">
    <div class = "note">
        <h1>Note Editor</h1>

        <div class="mode-toggle">
            <%
                String mode = request.getParameter("mode");
                if (mode != null && mode.equals("view"))
                {
            %>
            <form action="/toggle-view-mode" method="POST">
                <input type="hidden" name="mode" value="edit">
                <input type="hidden" name="noteId" value="${note.getID()}">
                <button type="submit">Edit</button>
            </form>
            <%
            }
            else
            {
            %>
            <form action="/toggle-view-mode" method="POST">
                <input type="hidden" name="noteId" value="${note.getID()}">
                <input type="hidden" name="mode" value="view">
                <button type="submit">View</button>
            </form>
            <%
                }
            %>
        </div>
        <% if (request.getParameter("mode") != null && request.getParameter("mode").equals("view")) { %>

        <h2>${note.getTitle()}</h2>
        <div class="summary-section">
            <h3>Summary</h3>
            <div class="summary-content">${note.getSummary()}</div>
        </div>

        <div class="content-section">
            <h3>Rendered Content</h3>
            <div class="markdown-content">
                <%= request.getAttribute("renderedContent") %>
            </div>
        </div>
        <%
        }
        else
        {
        %>


        <form action="/save-note" method="POST">
            <label for="note_title">Title</label>
            <input type="hidden" name="note_ID" value="${note.getID()}">
            <input type="text" id="note_title" name="note_title" value="${note.getTitle()}" required><br><br>

            <label for="note_summary">Summary</label>

            <textarea id="note_summary" name="note_summary">${note.getSummary()}</textarea><br><br>

            <label for="note_content">Content</label><br>

            <textarea id="note_content" name="note_content"
                      required>${note.getTextContents()}</textarea>

            <% if (request.getAttribute("error_message") != null)
            { %>
            <div id="error_msg">
                <%= request.getAttribute("error_message") %>
            </div>
            <% } %>
        <% } %>

            <button type="submit">Save Note</button>
        </form>

        <form  action="delete-note" method = "post">
            <input type="hidden" name="note_ID" value="${note.getID()}">
            <button type="submit">Delete Note</button>
        </form>
    </div>
</div>
</body>
</html>
