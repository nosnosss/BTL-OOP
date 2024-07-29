/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dal.UserDAO;
import extension.Encrypt;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;


public class UserSignUp extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String previousUri = (String) session.getAttribute("previousUri");
            if (previousUri != null) {
                response.sendRedirect(previousUri);
            } else {
                response.sendRedirect("home");
            }
        } else {
            request.getRequestDispatcher("views/user/sign_up.jsp").forward(request, response);

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullName = request.getParameter("full_name");
        String phoneNumber = request.getParameter("phone_number");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passWordEncrypt = Encrypt.toSHA1(password);
        User userRaw = new User();
        userRaw.setFullname(fullName);
        userRaw.setEmail(email);
        userRaw.setPassword(passWordEncrypt);
        userRaw.setPhoneNumber(phoneNumber);
        UserDAO userDB = new UserDAO();
        if (userDB.isExisted(userRaw)) {
            request.setAttribute("fullName", fullName);
            request.setAttribute("phoneNumber", phoneNumber);
            request.setAttribute("isExisted", "Email Đã Tồn Tại");
            request.getRequestDispatcher("views/user/sign_up.jsp").forward(request, response);
        } else {
            int i = userDB.addUser(userRaw);
            System.out.println(i);
            User user = userDB.getUserByEmail(email);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            String previousUri = (String) session.getAttribute("previousUri");
            if (previousUri != null) {
                response.sendRedirect(previousUri);
            } else {
                response.sendRedirect("home");
            }
        }
    }
}
