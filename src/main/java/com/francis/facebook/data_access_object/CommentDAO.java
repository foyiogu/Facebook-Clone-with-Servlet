package com.francis.facebook.data_access_object;

import com.francis.facebook.model.Comment;
import com.francis.facebook.model.Comment;
import com.francis.facebook.model.Post;
import com.francis.facebook.services.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommentDAO {

    public boolean addComment(Comment comment) {
        boolean isSuccess = false;
        try (Connection connection = DB.connect();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO comments(user_id, post_id, body) VALUE (?,?,?)");
        ) {
            preparedStatement.setInt(1, comment.getUser_id());
            preparedStatement.setInt(2, comment.getPost_id());
            preparedStatement.setString(3, comment.getBody());

            preparedStatement.executeUpdate();
            isSuccess = true;

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return isSuccess;
    }

    public ArrayList<Comment> getCommentsByPostId(int post_id) {
        ArrayList<Comment> comments = new ArrayList<>();
        try(Connection connection = DB.connect();
        PreparedStatement preparedStatement = connection.prepareStatement("Select * from comments where post_id = ? order by id DESC ");
        ) {
            preparedStatement.setInt(1, post_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int postId = resultSet.getInt("post_id");
                String body = resultSet.getString("body");
                comments.add(new Comment(id, userId, postId, body));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return comments;
    }

}
