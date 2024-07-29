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
import model.Category;


@WebServlet(name = "AddProduct", urlPatterns = {"/addproduct"})
public class AdminAddProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoryDAO cdb = new CategoryDAO();
        List<Category> categories = cdb.getAll();
        cdb.close();
        String catid = request.getParameter("catid");
        if (catid == null) {
            catid = "1";
        }
        request.setAttribute("catid", catid);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("./views/admin/addproduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name_raw = (String) request.getParameter("name");
        String thumbnail_raw = (String) request.getParameter("thumbnail");
        String description_raw = (String) request.getParameter("description");
        String unit_raw = (String) request.getParameter("unit");
        String branch_raw = (String) request.getParameter("branch");
        String category_id_raw = (String) request.getParameter("category_id");
        String capacity_raw = (String) request.getParameter("capacity");
        String alcohol_raw = (String) request.getParameter("alcohol");
        String quantity_raw = (String) request.getParameter("quantity");
        String price_raw = (String) request.getParameter("price");
        String price_sale_raw = (String) request.getParameter("price_raw");
        
        
        
        int capacity = 0;
        if(capacity_raw!= null){
            capacity = Integer.parseInt(capacity_raw);
        }
        int category_id = Integer.parseInt(category_id_raw);
        double alcohol = 0.0;
        if(alcohol_raw != null){
            alcohol = Double.parseDouble(alcohol_raw);
        }
        
        int quantity = 0;
        if(quantity_raw != null){
            quantity = Integer.parseInt(quantity_raw);
        }
        
        int price = 0;
        if(price_raw != null){
            price = Integer.parseInt(price_raw);
        }
        
        int price_sale = 0;
        if(price_sale_raw != null){
            price_sale = Integer.parseInt(price_sale_raw);
        }
        ProductDAO db = new ProductDAO();
        try {
            db.add((name_raw!=null)?name_raw:"", (thumbnail_raw!=null)?thumbnail_raw:"", (description_raw!=null)?description_raw:"", (unit_raw!=null)?unit_raw:"", (branch_raw!=null)?branch_raw:"", category_id, capacity, alcohol, quantity,price, price_sale);
        } catch (SQLException x) {
            System.err.println(x);
        }
        db.close();
        response.sendRedirect("products");
    }
}
