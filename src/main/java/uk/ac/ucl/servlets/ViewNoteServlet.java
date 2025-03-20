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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        int note_ID = Integer.parseInt(request.getParameter("id"));

        Model model = ModelFactory.getModel();
        Note note = model.get_note(note_ID);
        request.setAttribute("note", note);

        RequestDispatcher dispatch = request.getRequestDispatcher("/note.jsp");
        dispatch.forward(request, response);;
    }
}