/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dal.CategoryDAO;
import dal.ProductDAO;
import dal.UserDAO;
import extension.Encrypt;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Category;
import model.User;


public class UserChangePassword extends HttpServlet {
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
            category.setProducts(productDB.getByCatId(category.getId()));
        }

        productDB.close();
        catDB.close();
        request.setAttribute("categories", categories);
        request.setAttribute("branchesmap", branchesMap);
        request.getRequestDispatcher("views/user/change_password.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String oldPassword = request.getParameter("old_password");
        String newPassword = request.getParameter("password");
        String oldPasswordEncrypt = Encrypt.toSHA1(oldPassword);
        String newPasswordEncrypt = Encrypt.toSHA1(newPassword);
        if (user.getPassword().equals(oldPasswordEncrypt)) {
            user.setPassword(newPasswordEncrypt);
            UserDAO userDB = new UserDAO();
            int result = userDB.updateUser(user);
            if (result == 1) {
                session.removeAttribute("user");
                session.setAttribute("user", user);
                request.setAttribute("changePasswordSuccess", "Thay Đổi Mật Khẩu Thành Công");
                request.getRequestDispatcher("views/user/profile.jsp").forward(request, response);
            } else {
                List<Category> categories = new ArrayList<>();
                Map<Integer, List<String>> branchesMap = new HashMap<>();
                CategoryDAO catDB = new CategoryDAO();
                ProductDAO productDB = new ProductDAO();

                categories = catDB.getAll();
                for (Category category : categories) {
                    branchesMap.put(category.getId(), productDB.getBranches(category.getId()));
                    category.setProducts(productDB.getByCatId(category.getId()));
                }

                productDB.close();
                catDB.close();
                request.setAttribute("categories", categories);
                request.setAttribute("branchesmap", branchesMap);
                request.setAttribute("changePasswordError", "Đã Xảy Ra Lỗi Vui Lòng Thử Lại");
                request.getRequestDispatcher("views/user/change_password.jsp").forward(request, response);
            }
        } else {
            List<Category> categories = new ArrayList<>();
            Map<Integer, List<String>> branchesMap = new HashMap<>();
            CategoryDAO catDB = new CategoryDAO();
            ProductDAO productDB = new ProductDAO();

            categories = catDB.getAll();
            for (Category category : categories) {
                branchesMap.put(category.getId(), productDB.getBranches(category.getId()));
                category.setProducts(productDB.getByCatId(category.getId()));
            }

            productDB.close();
            catDB.close();
            request.setAttribute("categories", categories);
            request.setAttribute("branchesmap", branchesMap);
            request.setAttribute("wrongOldPassword", "Mật Khẩu Cũ Không Đúng");
            request.getRequestDispatcher("views/user/change_password.jsp").forward(request, response);
        }
    }
}
