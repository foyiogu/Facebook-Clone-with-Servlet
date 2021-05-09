package com.francis.facebook.controller;

import com.francis.facebook.data_access_object.PostDAO;
import com.francis.facebook.data_access_object.LikePostDAO;
import com.francis.facebook.data_access_object.PostDAO;
import com.francis.facebook.model.Post;
import com.francis.facebook.model.User;
import com.francis.facebook.services.DB;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "PostServlet", value = "/post")
public class PostServlet extends HttpServlet {
    PostDAO postDAO = null;
    public PostServlet() {
        postDAO = new PostDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");
        switch (page) {
            case "add":
                addPost(request, response);
                break;
            case "update_post":
                updatePost(request, response);
                break;

            case "delete_post":
                deletePost(request, response);
                break;

            default:
                break;
        }
    }

    protected void addPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String  body = request.getParameter("body");
        User user = (User) request.getSession().getAttribute("user_session");
        boolean isError = false;

        if (body == null || body == "") {
            request.setAttribute("bodyValidation", "field is required!");
            isError = true;
        } else {
//            response.getWriter().println(user.getId());
            Post post = new Post(user.getId(), body);
            postDAO.addPost(post);
            response.sendRedirect(request.getContextPath()+"/dashboard");
        }

    }

    protected void updatePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User authUser = (User) request.getSession().getAttribute("user_session");
        int postId =Integer.parseInt(request.getParameter("post_id"));
        int postAuthorId = Integer.parseInt(request.getParameter("post_author_id"));
        String body = request.getParameter("body");

        if (body.equals("")) {
            response.sendRedirect(request.getContextPath()+"/comment?post_id="+postId+"&invalid_field=true");
        } else if(authUser.getId() != postAuthorId) {
            response.getWriter().println("You dont have access to update this post");
        }
        else {
            boolean isUpdate = postDAO.updatePost(postId, body);
            if(!isUpdate) {
                response.getWriter().println("Something went wrong in database");
            } else {
                response.sendRedirect(request.getContextPath()+"/comment?post_id="+postId+"&post_success=true");
            }
        }
    }

    protected void deletePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int postId = Integer.parseInt(request.getParameter("post_id"));
        int postAuthorId = Integer.parseInt(request.getParameter("post_user_id"));
        User authenticatedUser = (User) request.getSession().getAttribute("user_session");

        if (postAuthorId != authenticatedUser.getId()) {
            response.getWriter().println("You dont have access to be delete this post because you are not the owner of the post");
//            response.sendRedirect(request.getContextPath()+"/comment?post_id="+postId+"&deleted_denied=true");
        } else {
            boolean isDeleted = postDAO.deletePost(postId);
            if (!isDeleted) {
                response.sendRedirect(request.getContextPath()+"/comment?post_id="+postId+"&is_deleted=false");
            } else {
                response.sendRedirect(request.getContextPath()+"/dashboard?is_deleted=true");

            }
        }

    }

    }

