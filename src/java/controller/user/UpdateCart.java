/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.user;

import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Product;

@WebServlet(name="UpdateCart", urlPatterns={"/updatecart"})
public class UpdateCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String delete = request.getParameter("delete");
        String update = request.getParameter("update");
        Cookie[] cookies = request.getCookies();
        String value = "";
        ProductDAO productDao = new ProductDAO();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("cart")){

                    String[] cart = cookie.getValue().split("_");
                    for(String item : cart){
                        String[] tmp = item.split(":");
                        if(!tmp[0].equals(delete)){
                            if(update != null){
                                int quantity = Integer.parseInt(request.getParameter("quantity"));

                                try {
                                    Product product = productDao.getByID(Integer.parseInt(update));
                                    
                                    if(tmp[0].equals(update)){
                                        if(product.getQuantity() < quantity){
                                            quantity = product.getQuantity();
                                        }
                                        value += "_" + tmp[0] + ":" + quantity;
                                        System.out.println("update" + product.getId());
                                    }
                                    else value += "_" + item;
                                } catch (SQLException ex) {
                                    response.sendRedirect("views/user/error_404.jsp");
                                    Logger.getLogger(UpdateCart.class.getName()).log(Level.SEVERE, null, ex);
                                }  
                            }
                            else value += "_" + item;
                        }
                    }
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    if(!value.equals("") && value.charAt(0) == '_') value = value.substring(1);
                    if(!value.equals("")){
                        Cookie newCookie = new Cookie("cart", value);
                        newCookie.setMaxAge(60 * 60 * 24 * 7);
                        response.addCookie(newCookie);
                    }
                    
                }
            }  
        }
        response.sendRedirect("/webn1/cart");
    } 
}
