package com.example.repository;

import com.example.config.DataBaseConnection;
import com.example.config.SQLQueries;
import com.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for managing user data in the database.
 */
public class UserRepository {

    /**
     * Saves a new user to the database.
     *
     * @param user the user to save
     * @throws SQLException if a database access error occurs
     */
    public void save(User user) throws SQLException {
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SQLQueries.SAVE_USER)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setBoolean(4, user.isBlocked());
            stmt.executeUpdate();
        }
    }

    /**
     * Finds a user by their email.
     *
     * @param email the email of the user to find
     * @return the User object, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public User findByEmail(String email) throws SQLException {
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SQLQueries.FIND_USER_BY_EMAIL)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("name"), rs.getString("email"), rs.getString("password"));
            }
        }
        return null;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list of all users
     * @throws SQLException if a database access error occurs
     */
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection connection = DataBaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SQLQueries.FIND_ALL_USERS)) {
            while (rs.next()) {
                users.add(new User(rs.getString("name"), rs.getString("email"), rs.getString("password")));
            }
        }
        return users;
    }

    /**
     * Updates the specified user in the database.
     *
     * @param user the user to update
     * @throws SQLException if a database access error occurs
     */
    public void update(User user) throws SQLException {
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SQLQueries.UPDATE_USER)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setBoolean(3, user.isBlocked());
            stmt.setString(4, user.getEmail());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Обновление не произошло, пользователь с таким email не найден.");
            }
        }
    }


    /**
     * Deletes a user by their email.
     *
     * @param email the email of the user to delete
     * @throws SQLException if a database access error occurs
     */
    public void deleteByEmail(String email) throws SQLException {
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SQLQueries.DELETE_USER_BY_EMAIL)) {
            stmt.setString(1, email);
            stmt.executeUpdate();
        }
    }
}
