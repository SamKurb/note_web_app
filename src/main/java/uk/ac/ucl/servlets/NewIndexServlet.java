package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Index;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;

@WebServlet("/create-index")
public class NewIndexServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Starting create-index servlet");
        try {
            // Get the model and current category
            Model model = ModelFactory.getModel();
            Index current_category = (Index) request.getSession().getAttribute("current_category");

            if (current_category == null) {
                System.out.println("No current category found");
                // Fallback to main index if no current category
                current_category = model.getMainIndex();
            } else {
                System.out.println("Current category: " + current_category.getName() + " (ID: " + current_category.getID() + ")");
            }

            // Get the new index name from the request parameter
            String newIndexName = request.getParameter("index_name");
            System.out.println("New index name: " + newIndexName);

            if (newIndexName == null || newIndexName.trim().isEmpty()) {
                System.out.println("Index name is empty");
                response.sendRedirect(request.getContextPath() + "/main");
                return;
            }

            // Create the new index
            int parentID = current_category.getID();
            System.out.println("Parent ID: " + parentID);

            Index newIndex = model.newIndex(newIndexName, parentID);
            System.out.println("New index created: " + newIndex.getName() + " (ID: " + newIndex.getID() +
                    "), Parent ID: " + newIndex.getParentID());


            model.saveAllData();

            // Redirect back to main page
            System.out.println("Redirecting to main page");
            response.sendRedirect(request.getContextPath() + "/main?id=" + parentID);

        } catch (Exception e) {
            System.out.println("ERROR in create-index: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/main");
        }
    }
}