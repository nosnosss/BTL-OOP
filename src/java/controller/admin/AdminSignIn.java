/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.UserDAO;
import extension.Encrypt;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet(name="AdminSignIn", urlPatterns={"/admin_sign_in"})
public class AdminSignIn extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().getId() == 1) {
            String previousUri = (String) session.getAttribute("previousUri");
            if (previousUri != null) {
                response.sendRedirect(previousUri);
            } else {
                response.sendRedirect("list_user");
            }
        } else {
            request.getRequestDispatcher("views/admin/sign_in.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordEncrypt = Encrypt.toSHA1(password);
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncrypt);
        UserDAO userDB = new UserDAO();
        if (userDB.isValid(user)) {
            user = userDB.getUserByEmail(email);
            if (user.getRole().getId() == 1) {
                HttpSession session = request.getSession();
                session.removeAttribute("user");
                session.setAttribute("user", user);
                String previousUri = (String) session.getAttribute("previousUri");
                if (previousUri != null) {
                    response.sendRedirect(previousUri);
                } else {
                    response.sendRedirect("list_user");
                }
            } else {
                request.setAttribute("wrongUser", "Email Hoặc Mật Khẩu Không Đúng!");
                request.getRequestDispatcher("views/admin/sign_in.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("wrongUser", "Email Hoặc Mật Khẩu Không Đúng!");
            request.getRequestDispatcher("views/admin/sign_in.jsp").forward(request, response);
        }
    }
}
