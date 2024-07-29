/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin;

import dal.CategoryDAO;
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
import model.Category;
import model.Product;


@WebServlet(name="AdminCategoryServlet", urlPatterns={"/categories"})
public class AdminCategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        CategoryDAO db = new CategoryDAO();
        List<Category> categories = db.getAll();
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
        int end = Math.min((currentpage)*nummberperpage, categories.size());
        
        int amountofproducts = categories.size();
        int numberofpage = (amountofproducts%nummberperpage==0)?(categories.size()/nummberperpage):(categories.size()/nummberperpage+1);
        List<Category> categoriesPage = new ArrayList<>();
        for(int i = start; i < end; i++) categoriesPage.add(categories.get(i));
//        String state = (String)request.getAttribute("error");
//        request.setAttribute("state", state);
        request.setAttribute("categories", categoriesPage);
        request.setAttribute("page", currentpage);
        request.setAttribute("numberofpage", numberofpage);
        request.getRequestDispatcher("./views/admin/showcategory.jsp").forward(request, response);
    } 
}
