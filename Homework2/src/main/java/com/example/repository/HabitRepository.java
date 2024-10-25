package com.example.repository;

import com.example.config.DataBaseConnection;
import com.example.config.SQLQueries;
import com.example.model.Habit;
import com.example.enums.Frequency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabitRepository {


    public void save(Habit habit) {
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SQLQueries.SAVE_HABIT, Statement.RETURN_GENERATED_KEYS)) {
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
            // Используйте логгер вместо e.printStackTrace()
            System.err.println("Ошибка сохранения привычки: " + e.getMessage());
        }
    }

    public List<Habit> findAll() {
        List<Habit> habits = new ArrayList<>();
        try (Connection connection = DataBaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SQLQueries.FIND_ALL_HABITS)) {
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
            System.err.println("Ошибка получения привычек: " + e.getMessage());
        }
        return habits;
    }

    public void delete(Integer habitId) {
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SQLQueries.DELETE_HABIT)) {
            stmt.setInt(1, habitId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка удаления привычки: " + e.getMessage());
        }
    }
}
