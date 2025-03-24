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

@WebServlet("/toggle-view-mode")
public class ToggleViewModeServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, NumberFormatException
    {
        Model model = ModelFactory.getModel();
        String noteIdParam = request.getParameter("noteId");

        if (noteIdParam == null || noteIdParam.isEmpty())
        {
            response.sendRedirect("/note?id=" + noteIdParam);
            return;
        }

        String mode = request.getParameter("mode");

        if (mode == null)
        {
            mode = "edit"; // Default to edit mode
        }
        if (mode.equals("view"))
        {
            request.setAttribute("mode", "view");
        }
        else if (mode.equals("edit"))
        {
            request.setAttribute("mode", "edit");
        }

        response.sendRedirect("/note?id=" + noteIdParam + "&mode=" + mode);
    }
}