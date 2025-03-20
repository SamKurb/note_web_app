
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Patient Data</title>
</head>
<body>
<%@ include file="sidebar.jsp" %>
<div id="content">
    <h2>Note taking</h2>
    <form action = "create-note", method = "POST">
        <button type = "submit"> Create a new note </button>
    </form>
</div>

</body>
</html>
