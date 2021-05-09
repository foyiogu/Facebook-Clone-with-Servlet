package com.francis.facebook.controller;

import com.francis.facebook.data_access_object.CommentDAO;
import com.francis.facebook.data_access_object.LikePostDAO;
import com.francis.facebook.data_access_object.PostDAO;
import com.francis.facebook.data_access_object.UserDAO;
import com.francis.facebook.model.Comment;
import com.francis.facebook.model.Post;
import com.francis.facebook.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CommentServlet", value = "/comment")
public class CommentServlet extends HttpServlet {

    private CommentDAO commentDAO = null;
    private PostDAO postDAO = null;
    private UserDAO userDAO = null;
    private User authUser = null;
    LikePostDAO likePostDAO = null;

    public CommentServlet() {
        commentDAO = new CommentDAO();
        postDAO = new PostDAO();
        userDAO = new UserDAO();
        likePostDAO = new LikePostDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String post_id = request.getParameter("post_id");
        authUser = (User) request.getSession().getAttribute("user_session");
        if (post_id.equals("") || post_id == null) {
            request.setAttribute("error_message", "Select  a post");
            request.getRequestDispatcher("dashboard.jsp");
        } else  {
            int postId = Integer.parseInt(post_id);
            Post post = postDAO.getPostById(postId);
            request.setAttribute("user", userDAO.getUserById(post.getUser_id()));
            request.setAttribute("post", post);
            request.setAttribute("comments", commentDAO.getCommentsByPostId(postId));
            request.setAttribute("totalLikes", likePostDAO.getTotalLikes(postId));
            request.getRequestDispatcher("comments.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        authUser = (User) request.getSession().getAttribute("user_session");

        int postId = Integer.parseInt(request.getParameter("post_id"));
        int authUserId = authUser.getId();
        String body = request.getParameter("body");

        CommentDAO commentDAO = new CommentDAO();
        boolean isCommentAdded = commentDAO.addComment(new Comment(authUserId, postId, body));
        if (!isCommentAdded) {
//            request.setAttribute("error", "Error adding comment");
            response.sendRedirect(request.getContextPath()+"/comment?post_id="+postId);

        } else {
            response.sendRedirect(request.getContextPath()+"/comment?post_id="+postId+"&comment_success=true");
        }

    }
}
