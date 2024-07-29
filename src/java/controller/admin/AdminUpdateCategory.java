/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin;

import dal.CategoryDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;
import model.Product;


@WebServlet(name="AdminUpdateCategory", urlPatterns={"/update_category"})
public class AdminUpdateCategory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        CategoryDAO db = new CategoryDAO();
        String id = (String) request.getParameter("id");
        Category category = db.getById(Integer.parseInt(id));
        db.close();
        String page = request.getParameter("page");
        request.setAttribute("page", page);
        request.setAttribute("category", category);
        request.getRequestDispatcher("./views/admin/updatecategory.jsp").forward(request, response);

    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String id_raw = (String) request.getParameter("id");
        String name_raw = (String) request.getParameter("name");
        CategoryDAO db = new CategoryDAO();
        try {
            db.update(Integer.parseInt(id_raw), name_raw);
        } catch (SQLException ex ) {
            System.err.println(ex);
        }
        db.close();
         response.sendRedirect("categories");
    }
}
