/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;


@WebServlet(name="AddToCart", urlPatterns={"/addtocart"})
public class AddToCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int ok = 0;
        boolean addedItem = false;
        ArrayList<Integer> listIds = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        String  value = "";
        if(cookies != null){
            for(Cookie c : cookies){
            if(c.getName().equals("cart")){
                ok = 1;
                String[] listItems = c.getValue().split("_");
                for(String item : listItems){
                    String[] i = item.split(":");
                    int j = Integer.parseInt(i[0]);
                    int q = Integer.parseInt(i[1]);
                    if(j == id){
                        q += quantity;
                        addedItem = true;
                    }
                    value += "_" + j + ":" + q;
                }
                if(addedItem == false){
                    value += "_" + id + ":" + quantity;
                }
                c.setMaxAge(0);
                response.addCookie(c);
                if(value.charAt(0) == '_') value = value.substring(1);
                Cookie newCookie = new Cookie("cart", value);
                newCookie.setMaxAge(60 * 60 * 24 * 7);
                response.addCookie(newCookie);
            }
        }
        if(ok == 0){
            value = ("" + id + ":" + quantity);
            Cookie newCookie = new Cookie("cart", value);
            newCookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(newCookie);
        }
        }
        response.sendRedirect("productdetail" + "?pid=" + id);
    } 
}
