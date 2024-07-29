/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class AdminDeleteUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String userIdRaw = request.getParameter("id");
        try {
            int userId = Integer.parseInt(userIdRaw);
            int result = new UserDAO().deleteUser(userId);
            if (result == 1) {
                request.setAttribute("success", "Thanh Cong");
                request.getRequestDispatcher("list_user").forward(request, response);
            } else {
                request.setAttribute("error", "Da Xay Ra Loi");
                request.getRequestDispatcher("list_user").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Da Xay Ra Loi");
            request.getRequestDispatcher("list_user").forward(request, response);
        }
    } 
}
