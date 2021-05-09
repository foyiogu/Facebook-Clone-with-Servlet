package com.francis.facebook.data_access_object;

import com.francis.facebook.model.Post;
import com.francis.facebook.model.User;
import com.francis.facebook.services.DB;
import com.francis.facebook.utilities.CurrentDateTime;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    public void addPost(Post post) {
        try(Connection connection = DB.connect();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into posts(user_id, body, created_at) values (?,?,?)");
        ) {
            preparedStatement.setInt(1, post.getUser_id());
            preparedStatement.setString(2, post.getBody());
            preparedStatement.setString(3, CurrentDateTime.now());

            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public List<Post> fetchAllPosts()
    {
        ArrayList<Post> posts = new ArrayList<>();
        try(Connection connection = DB.connect();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from posts ORDER BY created_at DESC ");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                String body = resultSet.getString("body");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                posts.add(new Post(id, userId, body, createdAt));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return posts;
    }

    public Post getPostById(int post_id) {
        Post post = null;

        try(Connection connection = DB.connect();
            PreparedStatement preparedStatement = connection.prepareStatement("Select * from posts where id = ?");
        ) {
            preparedStatement.setInt(1, post_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                String body = resultSet.getString("body");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                post = new Post(id, userId, body, createdAt);
            }
        }catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return post;
    }

    public boolean updatePost(int postId, String body)
    {
        boolean isUpdate = false;
        try(Connection connection = DB.connect();
        PreparedStatement preparedStatement = connection.prepareStatement("Update posts set body = ? where id = ?");)
        {
            preparedStatement.setString(1, body);
            preparedStatement.setInt(2, postId);
            preparedStatement.executeUpdate();
            isUpdate = true;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return isUpdate;
    }

    public boolean deletePost(int postId) {
        boolean isDelete = false;
        try (Connection connection = DB.connect();
        PreparedStatement preparedStatement = connection.prepareStatement("Delete from posts where id = ?");
        ) {
            preparedStatement.setInt(1, postId);
            preparedStatement.executeUpdate();
            isDelete = true;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return isDelete;
    }

}
