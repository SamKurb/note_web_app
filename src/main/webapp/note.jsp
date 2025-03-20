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
    <h1>Note with title: ${note.get_title()}</h1>

    <form action="saveNote" method="post">
        <label for="noteTitle">Title:</label>
        <input type="text" id="noteTitle" name="noteTitle" value="${note.get_title()}" required><br><br>

        <label for="noteContent">Content:</label><br>
        <textarea id="noteContent" name="noteContent" rows="10" cols="30" required>${note.get_contents()}</textarea><br><br>

        <button type="submit">Save Note</button>
    </form>
    </div>
</div>
</body>
</html>
