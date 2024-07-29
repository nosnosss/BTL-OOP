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
import java.util.List;
import model.User;


public class AdminListUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pageStr = request.getParameter("page");
        int page;
        if (pageStr != null) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                page = 1;
            }
        } else {
            page = 1;
        }
        List<User> listUserRaw = new UserDAO().getAllUsers();
        int itemsPerPage = 10;
        int totalPages = (int) Math.ceil(listUserRaw.size() * 1.0 / itemsPerPage);
        int start = (page - 1) * itemsPerPage;
        int end = Math.min(page * itemsPerPage, listUserRaw.size());
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("listUser", new UserDAO().getListUserByPage(listUserRaw, start, end));
        request.getRequestDispatcher("views/admin/list_user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pageStr = request.getParameter("page");
        int page;
        if (pageStr != null) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                page = 1;
            }
        } else {
            page = 1;
        }
        List<User> listUserRaw = new UserDAO().getAllUsers();
        int itemsPerPage = 10;
        int totalPages = (int) Math.ceil(listUserRaw.size() * 1.0 / itemsPerPage);
        int start = (page - 1) * itemsPerPage;
        int end = Math.min(page * itemsPerPage, listUserRaw.size());
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("listUser", new UserDAO().getListUserByPage(listUserRaw, start, end));
        request.getRequestDispatcher("views/admin/list_user.jsp").forward(request, response);
    }
}
