package com.francis.facebook.data_access_object;

import com.francis.facebook.model.User;
import com.francis.facebook.services.DB;
import com.francis.facebook.utilities.CurrentDateTime;

import java.io.PrintWriter;
import java.sql.*;

public class UserDAO   {

    public void register(User user)
    {
        String insertStatment = "insert into users(first_name, last_name, email, password, gender,created_at,updated_at) " +
                "VALUES(?,?,?,?,?,?,?)";
        try (Connection connection = DB.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(insertStatment);
        ) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getGender());
            preparedStatement.setString(6, CurrentDateTime.now());
            preparedStatement.setString(7, CurrentDateTime.now());
            preparedStatement.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public User login(String email, String password) {
        User user = null;
        String sqlStatement = "Select * from users where email = ? AND password = ?";
        try (Connection connection = DB.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        ) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet == null) {
                return null;
            } else  {
                user = getUserData(resultSet);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return  user;
    }

    public User getUserById(int userId) {
        User user = null;
        try(Connection connection = DB.connect();
            PreparedStatement preparedStatement = connection.prepareStatement("Select * from users where id = ?");
        ) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = getUserData(resultSet);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return user;
    }

    private User getUserData(ResultSet resultSet) throws SQLException {
        User user = null;
        if (resultSet == null) {
            return user;
        } else {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String userEmail = resultSet.getString("email");
                String gender = resultSet.getString("gender");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                user = new User(id, firstName, lastName, userEmail, gender, createdAt);
            }
        }
        return user;
    }
}
