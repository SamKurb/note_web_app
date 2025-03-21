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
        <input type="text" id="note_title" name="note_title" value="${note.get_title()}" required><br><br>

        <label for="note_content">Content</label><br>

        <textarea id="note_content" name="note_content"
                  required>${note.get_contents()}</textarea>

        <button type="submit">Save Note</button>
    </form>
    </div>
</div>
</body>
</html>
