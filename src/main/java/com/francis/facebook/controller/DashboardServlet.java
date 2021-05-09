package com.francis.facebook.controller;

import com.francis.facebook.data_access_object.PostDAO;
import com.francis.facebook.model.Post;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DashboardServlet", value = "/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        fetchPosts(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void fetchPosts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PostDAO postDAO = new PostDAO();
        request.setAttribute("posts", postDAO.fetchAllPosts());
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}
