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
import java.util.List;

@WebServlet("/index.jsp")
public class IndexServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("hello");
        Model model = ModelFactory.getModel();
        Category category = model.get_index();

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(category);
        System.out.println(categories);

        request.setAttribute("categories", categories);
        RequestDispatcher dispatch = request.getRequestDispatcher("/index.jsp");
        dispatch.forward(request, response);
    }
}
