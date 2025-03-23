<%@ page import="java.util.ArrayList" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="uk.ac.ucl.model.Index" %>
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
    <% Index current_category = (Index) session.getAttribute("current_category");%>
    <h1> Notes in category: <%=current_category.getName() %> </h1>
    <form action = "create-note" method = "POST">
        <button type = "submit"> Create a new note </button>
    </form>
    <form action = "save-all" method = "POST">
        <button type = "submit"> Save all notes </button>
    </form>

    <div id="search">
        <form action="index-search" method="GET">
            <span class="sort_by_txt">Sort by:</span>
            <select name="sort_by">
                <option value="title">Title</option>
                <option value="id">Date</option>
                <option value="mod_time">Last modified </option>
            </select>
            <select name="order">
                <option value="asc">Ascending</option>
                <option value="desc">Descending</option>
            </select>
            <span class="srch_for_txt">Search for:</span>
            <select name="search_for">
                <option value="title">Title</option>
                <option value="contents">Contents</option>
            </select>
            <input type="text" name="search" placeholder="Search notes">
            <button type="submit">Apply</button>
        </form>
    </div>

    <div id = "index">
        <table>
            <tr>
                <th>Note name</th>
                <th>Note indices</th>
                <th>Note ID (higher = newer)</th>
            </tr>
            <%
                if (request.getAttribute("notes") != null)
                {
                    ArrayList<Note> notes = (ArrayList<Note>) request.getAttribute("notes");
                    for (Note note : notes)
                    {
                    %>
                    <tr>
                        <td>
                            <a href="note?id=<%= note.getID() %>" title="<%=note.getSummary() %>"> <%= note.getTitle() %> </a>
                        </td>
                        <td>
                            <%= note.getFormattedCategoryNames()%>
                        </td>
                        <td> <%= note.getID()%> </td>
                    </tr>
                    <%

                    }
                }
            %>

        </table>
    </div>
</div>

</body>
</html>
