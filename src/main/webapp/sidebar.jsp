<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  Boolean sidebarOpen = (Boolean) session.getAttribute("sidebarOpen");
  if (sidebarOpen == null)
  {
    sidebarOpen = false;
  }
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Sidebar Example</title>
  <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<div class="sidebar <%= sidebarOpen ? "open" : "closed" %>">
  <form action="sidebarHandle.html" method="POST">
    <input type="hidden" name="toggle" value="true">
    <button type="submit" class="toggle-btn">
      <%= sidebarOpen ? "<-" : "->" %>
    </button>
  </form>
  <ul>
    <li><a href="index.jsp">Home</a></li>
  </ul>
</div>
</body>
</html>