/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin;

import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Product;

@WebServlet(name="ProductServlet", urlPatterns={"/products"})
public class AdminProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ProductDAO db = new ProductDAO();
        List<Product> products = db.getAll();
        db.close();
        
        Integer currentpage,nummberperpage = 6;
        String page_raw = (String)request.getParameter("page");
        if(page_raw == null){
            currentpage = 1;
        }
        else{
            currentpage = Integer.parseInt(page_raw);
        }
        int start = (currentpage-1)*nummberperpage;
        int end = Math.min((currentpage)*nummberperpage, products.size());
        
        int amountofproducts = products.size();
        int numberofpage = (amountofproducts%nummberperpage==0)?(products.size()/nummberperpage):(products.size()/nummberperpage+1);
        List<Product> productPage = new ArrayList<>();
        for(int i = start; i < end; i++) productPage.add(products.get(i));
//        String state = (String)request.getAttribute("error");
//        request.setAttribute("state", state);
        request.setAttribute("products", productPage);
        request.setAttribute("page", currentpage);
        request.setAttribute("numberofpage", numberofpage);
        request.getRequestDispatcher("./views/admin/showproduct.jsp").forward(request, response);
    } 
}
