/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.user;

import dal.CategoryDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Cart;
import model.Category;
import model.Item;


public class UserProfile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String cookieTxt = "";
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("cart")){
                    cookieTxt += cookie.getValue();
                }
            }
        }
        Cart cart = new Cart(cookieTxt);
        List<Item> items = cart.getItems();
        request.setAttribute("cart", cart);
        request.setAttribute("items", items);
        
        
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
        request.getRequestDispatcher("views/user/profile.jsp").forward(request, response);
    } 
}
