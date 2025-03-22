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

        <form action="save-note" method="post">
            <label for="note_title">Title</label>
            <input type="hidden" name="note_ID" value="${note.getID()}">
            <input type="text" id="note_title" name="note_title" value="${note.getTitle()}" required><br><br>

            <label for="note_summary">Summary</label>

            <textarea id="note_summary" name="note_summary">${note.getSummary()}</textarea><br><br>

            <label for="note_content">Content</label><br>

            <textarea id="note_content" name="note_content"
                      required>${note.getContents()}</textarea>

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
