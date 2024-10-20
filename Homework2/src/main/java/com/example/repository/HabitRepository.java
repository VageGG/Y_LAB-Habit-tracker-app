package com.example.repository;

import com.example.model.Habit;
import com.example.enums.Frequency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabitRepository {
    private final Connection connection;

    public HabitRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Habit habit) {
        String sql = "INSERT INTO my_schema.habit (name, description, frequency, creation_date, user_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, habit.getName());
            stmt.setString(2, habit.getDescription());
            stmt.setString(3, habit.getFrequencyAsString());
            stmt.setDate(4, habit.getCreationDateAsSqlDate());
            stmt.setInt(5, habit.getUserId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        habit.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Habit> findAll() {
        List<Habit> habits = new ArrayList<>();
        String sql = "SELECT * FROM my_schema.habit";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Habit habit = new Habit();
                habit.setId(rs.getInt("id"));
                habit.setName(rs.getString("name"));
                habit.setDescription(rs.getString("description"));
                habit.setFrequency(Frequency.valueOf(rs.getString("frequency").toUpperCase()));
                habit.setCreationDate(rs.getDate("creation_date").toLocalDate());
                habit.setUserId(rs.getInt("user_id"));
                habits.add(habit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return habits;
    }

    public void delete(Integer habitId) {
        String sql = "DELETE FROM my_schema.habit WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, habitId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
