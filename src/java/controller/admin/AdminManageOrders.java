/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin;

import controller.user.UserOrders;
import dal.OrderDAO;
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
import model.Order;


@WebServlet(name="AdminManageOrders", urlPatterns={"/manageorders"})
public class AdminManageOrders extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        OrderDAO orderDAO = new OrderDAO();
        try {
            List<Order> orders = orderDAO.getAll();
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("views/admin/manageorders.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UserOrders.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//        String phone = request.getParameter("search-input").trim();
//        OrderDAO orderDAO = new OrderDAO();
//        
//        try {
//            List<Order> orders = orderDAO.getByPhonenumber(phone);
//            request.setAttribute("search_input", phone);
//            request.setAttribute("orders", orders);
//            request.getRequestDispatcher("views/admin/manageorders.jsp").forward(request, response);
//        } catch (SQLException ex) {
//            Logger.getLogger(AdminManageOrders.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String phone = request.getParameter("search-input").trim();
        String startDate = request.getParameter("start-date");
        String endDate = request.getParameter("end-date");

        OrderDAO orderDAO = new OrderDAO();

        try {
            List<Order> orders;
            if (startDate != null && endDate != null && !startDate.isEmpty() && !endDate.isEmpty()) {
                orders = orderDAO.getByDateRange(startDate, endDate);
            } else {
                orders = orderDAO.getByPhonenumber(phone);
            }
            request.setAttribute("search_input", phone);
            request.setAttribute("start_date", startDate);
            request.setAttribute("end_date", endDate);
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("views/admin/manageorders.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AdminManageOrders.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
