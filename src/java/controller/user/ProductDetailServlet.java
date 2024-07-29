package controller.user;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import dal.CategoryDAO;
import dal.CommentDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;
import model.Comment;
import model.Product;
import model.User;


@WebServlet(urlPatterns = {"/productdetail"})
public class ProductDetailServlet extends HttpServlet {
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
        }

        productDB.close();
        catDB.close();
        String pid_raw = (String) request.getParameter("pid");
        productDB = new ProductDAO();
        
        Product product = null;
        try {
            product = productDB.getByID(Integer.parseInt(pid_raw));
        } catch (SQLException ex) {
            Logger.getLogger(ProductDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("product", product);
        request.setAttribute("categories", categories);
        request.setAttribute("branchesmap", branchesMap);
        
        //NVT
        CommentDAO dao = new CommentDAO();
        ArrayList<Comment> listComments = dao.getCommentByProduct_Id(Integer.parseInt(pid_raw));
        request.setAttribute("listcomments", listComments);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);
        
        
        request.getRequestDispatcher("./views/user/productdetail.jsp").forward(request, response);


    }
}
