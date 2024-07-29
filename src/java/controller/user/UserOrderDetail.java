/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.user;

import dal.CategoryDAO;
import dal.OrderDetailDAO;
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
import model.OrderDetail;
import model.User;


@WebServlet(name="UserOrderDetail", urlPatterns={"/orderdetail"})
public class UserOrderDetail extends HttpServlet {
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

        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        try {
            int orderId = Integer.parseInt(request.getParameter("id"));
            
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute("user");
            int userId = user.getId();
            
            List<OrderDetail> orderDetails = orderDetailDAO.getAllById(orderId, userId);
            System.out.println("size: " + orderDetails.size());
            request.setAttribute("orderdetails", orderDetails);
            request.getRequestDispatcher("views/user/orderdetail.jsp").forward(request, response);
        } catch (SQLException ex) {
            
            request.getRequestDispatcher("views/user/error_404.jsp").forward(request, response);
            Logger.getLogger(UserOrderDetail.class.getName()).log(Level.SEVERE, null, ex);
        }  
    } 
}
