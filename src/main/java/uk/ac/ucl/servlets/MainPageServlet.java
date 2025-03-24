package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/main")
public class MainPageServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println(System.getProperty("user.dir"));
        Model model = ModelFactory.getModel();

        // Check for an index ID in the request parameters
        String indexIdParam = request.getParameter("id");
        Index currentIndex;

        if (indexIdParam != null && !indexIdParam.isEmpty()) {
            try {
                // Try to parse and get the requested index
                int indexId = Integer.parseInt(indexIdParam);
                Index requestedIndex = model.getIndexManager().getIndexByID(indexId);

                if (requestedIndex != null) {
                    // If a valid index was requested, update the session
                    currentIndex = requestedIndex;
                    request.getSession().setAttribute("current_category", currentIndex);
                } else {
                    // If index ID not found, use current or default
                    currentIndex = (Index) request.getSession().getAttribute("current_category");
                    if (currentIndex == null) {
                        currentIndex = model.getMainIndex();
                        request.getSession().setAttribute("current_category", currentIndex);
                    }
                }
            } catch (NumberFormatException e) {
                // Invalid ID format, use current or default
                currentIndex = (Index) request.getSession().getAttribute("current_category");
                if (currentIndex == null) {
                    currentIndex = model.getMainIndex();
                    request.getSession().setAttribute("current_category", currentIndex);
                }
            }
        } else {
            // No index ID provided, use current or default
            currentIndex = (Index) request.getSession().getAttribute("current_category");
            if (currentIndex == null) {
                currentIndex = model.getMainIndex();
                request.getSession().setAttribute("current_category", currentIndex);
            }
        }

        // Set up notes list
        ArrayList<Note> searchResults = (ArrayList<Note>) request.getAttribute("search_results");
        if (searchResults != null) {
            request.setAttribute("notes", searchResults);
        } else {
            request.setAttribute("notes", currentIndex.getNoteList());
        }

        // Get and set up child indices for display
        ArrayList<Index> childIndices =
                model.getIndexManager().getChildIndices(currentIndex.getID());
        request.setAttribute("child_indices", childIndices);

        // Also add parent index for navigation
        int parentId = currentIndex.getParentID();
        if (parentId >= 0) { // -1 means no parent
            Index parentIndex = model.getIndexManager().getIndexByID(parentId);
            request.setAttribute("parent_index", parentIndex);
        }

        // Add breadcrumb trail for navigation
        ArrayList<Index> breadcrumbs = new ArrayList<>();
        Index temp = currentIndex;
        while (temp != null && temp.getID() != 0) { // Stop at main index
            breadcrumbs.add(0, temp); // Add at the beginning
            int pid = temp.getParentID();
            if (pid < 0) break;
            temp = model.getIndexManager().getIndexByID(pid);
        }
        // Always add main index at the start
        if (model.getMainIndex().getID() != currentIndex.getID()) {
            breadcrumbs.add(0, model.getMainIndex());
        }
        request.setAttribute("breadcrumbs", breadcrumbs);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/main.jsp");
        dispatcher.forward(request, response);
    }
}