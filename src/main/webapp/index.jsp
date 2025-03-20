<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Retrieve sidebar state from session (default: closed)
    Boolean sidebarOpen = (Boolean) session.getAttribute("sidebarOpen");
    if (sidebarOpen == null) {
        sidebarOpen = false; // Default to closed
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Patient Data</title>
    <link rel="stylesheet" href="styles.css"> <!-- Link to your external CSS file -->
</head>
<body>

<!-- Sidebar -->
<div class="sidebar <%= sidebarOpen ? "open" : "closed" %>">
    <form action="sidebarHandle.html" method="POST">
        <input type="hidden" name="toggle" value="true">
        <button type="submit" class="toggle-btn">
            <%= sidebarOpen ? "Close" : "Open" %> Sidebar
        </button>
    </form>
    <ul>
        <li><a href="patientList.jsp">View the Patient ID List</a></li>
        <li><a href="search.jsp">Search</a></li>
    </ul>
</div>

<!-- Main Content Area -->
<div class="main-content">
    <h2>Welcome to the Patient Data App</h2>

    <nav>
        <ul>
            <li><a href="patientList.jsp">View the Patient ID List</a></li>
            <li><a href="search.jsp">Search</a></li>
        </ul>
    </nav>
</div>

</body>
</html>
