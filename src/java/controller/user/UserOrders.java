/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.user;

import dal.CategoryDAO;
import dal.OrderDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;
import model.Order;
import model.User;


@WebServlet(name="UserOrders", urlPatterns={"/orders"})
public class UserOrders extends HttpServlet {
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
        }
        request.setAttribute("categories", categories);
        request.setAttribute("branchesmap", branchesMap);
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        OrderDAO orderDAO = new OrderDAO();
        try {
            List<Order> orders = orderDAO.getAllByUserId(user.getId());
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("views/user/orders.jsp").forward(request, response);
        } catch (SQLException ex) {
            
            Logger.getLogger(UserOrders.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        OrderDAO orderDAO = new OrderDAO();
        try {
            int id = Integer.parseInt(request.getParameter("orderid"));
            orderDAO.updateStatus(id, -1);
            Order order = orderDAO.getById(id);
            System.out.println(order.getStatus());
            response.sendRedirect("orders");
           
        } catch (SQLException ex) {
            
        }
    }
}
