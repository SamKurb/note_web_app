package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Category;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;
import uk.ac.ucl.model.Note;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@WebServlet("/index")
public class IndexServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Model model = ModelFactory.getModel();
        Category category = model.get_category();

        ArrayList<Note> notes = category.get_notes_by_title(true);
        ArrayList<String> titles = new ArrayList<>();

        for (Note note : notes)
        {
            titles.add(note.get_title());
        }

        System.out.println(titles);
        request.setAttribute("notes_"+category.get_name(), notes);
        RequestDispatcher dispatch = request.getRequestDispatcher("/index.jsp");
        dispatch.forward(request, response);
    }
}
