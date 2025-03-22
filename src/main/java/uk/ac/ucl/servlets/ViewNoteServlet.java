package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;
import uk.ac.ucl.model.Note;


import java.io.IOException;
import java.util.List;

@WebServlet("/note")
public class ViewNoteServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, NumberFormatException
    {

        try
        {
            int note_ID = Integer.parseInt(request.getParameter("id"));
            Model model = ModelFactory.getModel();

            if (model.noteExists(note_ID))
            {
                Note note = model.getNote(note_ID);
                request.setAttribute("note", note);
                RequestDispatcher dispatch = request.getRequestDispatcher("/note.jsp");
                dispatch.forward(request, response);
                return;
            }
        }
        catch (NumberFormatException e) { }
        handleNoteNotFound(request, response);
    }

    private void handleNoteNotFound(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("note_ID", request.getParameter("id"));
        RequestDispatcher dispatch = request.getRequestDispatcher("/note_doesnt_exist.jsp");
        dispatch.forward(request, response);
    }
}