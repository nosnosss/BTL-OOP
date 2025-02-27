/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.RoleDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Role;
import model.User;

@WebServlet(name="AdminUpdateUser", urlPatterns={"/update_user"})
public class AdminUpdateUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userIdRaw = request.getParameter("id");
        try {
            int userId = Integer.parseInt(userIdRaw);
            User user = new UserDAO().getUserById(userId);
            List<Role> listRole = new RoleDAO().getAllRoles();
            request.setAttribute("listRole", listRole);
            request.setAttribute("user", user);
            request.getRequestDispatcher("views/admin/update_user.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Da Xay Ra Loi");
            request.getRequestDispatcher("list_user").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userIdRaw = request.getParameter("user_id");
        String roleIdRaw = request.getParameter("role_id");
        String activeRaw = request.getParameter("active");
        try {
            int userId = Integer.parseInt(userIdRaw);
            int roleId = Integer.parseInt(roleIdRaw);
            boolean active = Boolean.parseBoolean(activeRaw);
            User user = new UserDAO().getUserById(userId);
            user.getRole().setId(roleId);
            user.setActive(active);
            int result = new UserDAO().updateUser(user);
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
