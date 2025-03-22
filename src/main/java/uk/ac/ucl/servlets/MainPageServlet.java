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
        Model model = ModelFactory.getModel();

        // Check if currentCategory exists in session, if not, set it
        if (request.getSession().getAttribute("current_category") == null)
        {
            System.out.println("hi");
            request.getSession().setAttribute("current_category", model.getMainCategory());
        }

        Index currentIndex = (Index) request.getSession().getAttribute("current_category");

        ArrayList<Note> searchResults = (ArrayList<Note>) request.getAttribute("search_results");

        if (searchResults != null)
        {
            request.setAttribute("notes", searchResults);
        }
        else
        {
            request.setAttribute("notes", currentIndex.getNoteList());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/main.jsp");
        dispatcher.forward(request, response);
    }
}
