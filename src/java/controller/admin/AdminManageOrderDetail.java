/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin;

import dal.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Order;


@WebServlet(name="AdminManageOrderDetail", urlPatterns={"/manageorderdetail"})
public class AdminManageOrderDetail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("orderid"));
        
        OrderDAO orderDAO = new OrderDAO();
        try {
            Order order = orderDAO.readById(id);
            request.setAttribute("orderdetail", order);
            request.getRequestDispatcher("views/admin/manageorderdetail.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AdminManageOrderDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int value = Integer.parseInt(request.getParameter("value"));
        int orderId = Integer.parseInt(request.getParameter("orderid"));
        
        OrderDAO orderDAO = new OrderDAO();
        try {
            orderDAO.updateStatus(orderId, value);
            response.sendRedirect("manageorderdetail?orderid=" + orderId);
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminManageOrders.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
