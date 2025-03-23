package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Index;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;



import java.io.IOException;

@WebServlet("/save-note")
public class SaveNoteServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Model model = ModelFactory.getModel();
        int note_ID = Integer.parseInt(request.getParameter("note_ID"));
        String note_title = request.getParameter("note_title");
        String note_summary = request.getParameter("note_summary");
        String note_content = request.getParameter("note_content");

        Index currentIndex = (Index) request.getSession().getAttribute("current_category");

        boolean succeeded = model.updateNote(note_ID, note_title, note_summary, note_content, currentIndex.getID());
        if (succeeded)
        {
            response.sendRedirect("/note?id=" + note_ID);
        }
        else
        {
            response.sendRedirect(request.getContextPath() + "/note?id=" + note_ID + "&error=duplicate");
        }
    }
}
