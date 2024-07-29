/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dal.CategoryDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Category;
import model.Product;
import model.User;


@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        List<Category> categories = new ArrayList<>();
        Map<Integer, List<String>> branchesMap = new HashMap<>();
        CategoryDAO catDB = new CategoryDAO();
        ProductDAO productDB = new ProductDAO();
        
        categories = catDB.getAll();
        for (Category category : categories) {
            branchesMap.put(category.getId(), productDB.getBranches(category.getId()));
            category.setProducts(productDB.getByCatId(category.getId()));
        }

        productDB.close();
        catDB.close();
        
        
        
        request.setAttribute("categories", categories);
        request.setAttribute("branchesmap", branchesMap);
        request.getRequestDispatcher("./views/user/home.jsp").forward(request, response);
    }
}
