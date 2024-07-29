/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dal.CommentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "DeleteComments", urlPatterns = {"/DeleteComments"})
public class DeleteComments extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int comment_id = Integer.parseInt(request.getParameter("cid"));
        CommentDAO dao = new CommentDAO();
        dao.deleteComment(comment_id);
        String product_id = request.getParameter("productId");
        response.sendRedirect("productdetail?pid=" + product_id);
    }
}
