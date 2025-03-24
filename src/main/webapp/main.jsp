<%@ page import="java.util.ArrayList" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="uk.ac.ucl.model.Index" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
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

    <div id="add_index">
        <form action="create-index" method="POST">
            <input type="text" name="index_name" placeholder="Enter index name">
            <button type="submit">Create new index</button>
        </form>
    </div>

    <!-- Add breadcrumb navigation at the top -->
    <div id="breadcrumb_nav">
        <%
            ArrayList<Index> breadcrumbs = (ArrayList<Index>) request.getAttribute("breadcrumbs");
            if (breadcrumbs != null && !breadcrumbs.isEmpty()) {
                for (int i = 0; i < breadcrumbs.size(); i++) {
                    Index crumb = breadcrumbs.get(i);
        %>
        <a href="main?id=<%= crumb.getID() %>"><%= crumb.getName() %></a>
        <% if (i < breadcrumbs.size() - 1) { %> > <% } %>
        <%
                }
            }
        %>
    </div>

    <div id="navigate_parent">
        <%
            Index parent = (Index) request.getAttribute("parent_index");
            if (parent != null) {
        %>
        <a href="main?id=<%= parent.getID() %>">&larr; Back to <%= parent.getName() %></a>
        <% } %>
    </div>

        <div class="table-container">
            <!-- Left column - Child indices table -->
            <div id="navigate_indices" class="left-column">
                <%
                    ArrayList<Index> childCategories = (ArrayList<Index>) request.getAttribute("child_indices");
                    if (childCategories != null && !childCategories.isEmpty()) {
                %>
                <table>
                    <tr>
                        <th>Child indices</th>
                    </tr>
                    <% for (Index childCat : childCategories) { %>
                    <tr>
                        <td>
                            <a href="main?id=<%= childCat.getID() %>"><%= childCat.getName() %></a>
                        </td>
                    </tr>
                    <% } %>
                </table>
                <% } else { %>
                <span class="indices_txt">No child indices</span>
                <% } %>
            </div>

            <!-- Right column - Search and Notes table -->
            <div class="right-column">
                <!-- Search form -->
                <div id="search">
                    <form action="index-search" method="GET">
                        <div class="search-row">
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
                        </div>
                        <div class="search-row">
                            <span class="srch_for_txt">Search for:</span>
                            <select name="search_for">
                                <option value="title">Title</option>
                                <option value="contents">Contents</option>
                            </select>
                            <input type="text" name="search" placeholder="Search notes">
                            <button type="submit">Apply</button>
                        </div>
                        <div class="search-row">
                            <span class="srch_for_txt">In:</span>
                            <select name="search_in">
                                <option value="curr_category">Current category</option>
                                <option value="all_sub_categories">All sub categories (and current)</option>
                                <option value="all_categories">All categories</option>
                            </select>
                        </div>
                    </form>
                </div>

                <!-- Notes table -->
                <div id="index">
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
        </div>

</body>
</html>
