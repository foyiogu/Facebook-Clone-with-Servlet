package com.francis.facebook.data_access_object;

import com.francis.facebook.services.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LikePostDAO {

    public boolean isPostLikedByUser(int post_id, int user_id) {
//        String query = "Select 1 from like_posts where post_id = ? AND user_id = ?";
        String query = "SELECT EXISTS(SELECT * from like_posts WHERE post_id = ? AND user_id = ?)";
        boolean exists = false;
        try (Connection connection = DB.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, post_id);
            preparedStatement.setInt(2, user_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int outPut = resultSet.getInt(1);
                if (outPut != 0) {
                    exists = true;
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return  exists;
    }

    public boolean likeOrUnlike(int post_id, int user_id) {
        if (isPostLikedByUser(post_id, user_id)) {
           return unlikePost(post_id, user_id);
        } else {
           return likePost(post_id, user_id);
        }
    }

    private boolean likePost(int post_id, int user_id) {
        String query = "Insert into like_posts(post_id, user_id) values (?,?)";
        boolean isLiked = false;
        try (Connection connection = DB.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             )
        {
            preparedStatement.setInt(1, post_id);
            preparedStatement.setInt(2, user_id);
            preparedStatement.executeUpdate();
            isLiked = true;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return isLiked;
    }

    public boolean unlikePost(int post_id, int user_id) {
        String query = "Delete from like_posts where post_id = ? and user_id = ? ";
        boolean isDeleted = false;
        try (Connection connection = DB.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             )
        {
            preparedStatement.setInt(1, post_id);
            preparedStatement.setInt(2, user_id);
            preparedStatement.executeUpdate();
            isDeleted = true;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return isDeleted;
    }

    public int getTotalLikes(int postId) {
        String query = "select count(*) as total_likes from like_posts where post_id = ?";
        int totalLikes = 0;
        try(Connection connection = DB.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                totalLikes = resultSet.getInt("total_likes");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return totalLikes;
    }
}
