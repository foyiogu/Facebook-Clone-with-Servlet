package com.francis.facebook.controller;

import com.francis.facebook.data_access_object.LikePostDAO;
import com.francis.facebook.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LikePostServlet", value = "/like_post")
public class LikePostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        likePost(request, response);
    }
    protected void likePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int postId = Integer.parseInt(request.getParameter("post_id"));
        User authUser = (User) request.getSession().getAttribute("user_session");

//
        int userId = authUser.getId();

        LikePostDAO likePostDAO = new LikePostDAO();

        boolean isLiked = likePostDAO.likeOrUnlike(postId, userId);
        response.getWriter().println(isLiked + " success here");
        response.sendRedirect(request.getContextPath()+"/comment?post_id="+postId);

    }
}
