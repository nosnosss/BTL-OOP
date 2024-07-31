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


@WebServlet(name="AdminDeleteCategory", urlPatterns={"/delete_category"})
public class AdminDeleteCategory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        CategoryDAO db = new CategoryDAO();
        String id = (String) request.getParameter("id");
        String message = "Accept";
        try{
            db.deleteById(Integer.parseInt(id));
        }catch(NumberFormatException ex){
            message = "Delete Fail";
           
        }                  
        request.setAttribute("message", message);
        request.getRequestDispatcher("categories").forward(request, response);
    } 
}
