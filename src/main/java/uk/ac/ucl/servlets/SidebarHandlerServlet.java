package uk.ac.ucl.servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/sidebarHandle.html")
public class SidebarHandlerServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("hi");
        HttpSession session = request.getSession();
        Boolean sidebarOpen = (Boolean) session.getAttribute("sidebarOpen");
        if (sidebarOpen == null)
        {
            sidebarOpen = true;
        } else
        {
            sidebarOpen = !sidebarOpen;
        }

        session.setAttribute("sidebarOpen", sidebarOpen);
        response.sendRedirect("sidebar.jsp");
    }
}