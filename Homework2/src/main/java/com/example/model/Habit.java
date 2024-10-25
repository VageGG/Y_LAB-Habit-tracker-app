package com.example.model;

import com.example.enums.Frequency;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;  // Импортируйте java.sql.Date для работы с JDBC
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a habit for the user.
 */
@Data
@NoArgsConstructor
public class Habit {
    private Integer id;
    private String name;
    private String description;
    private Frequency frequency;
    private LocalDate creationDate;
    private Integer userId;
    private List<HabitExecution> executionHistory = new ArrayList<>();

    public Habit(String name, String description, Frequency frequency, Integer userId) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (frequency == null) {
            throw new IllegalArgumentException("Frequency cannot be null.");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null.");
        }
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.creationDate = LocalDate.now();
        this.userId = userId;
    }

    public Date getCreationDateAsSqlDate() {
        return Date.valueOf(creationDate);
    }

    public String getFrequencyAsString() {
        return frequency.name();
    }

    public void markAsCompleted() {
        executionHistory.add(new HabitExecution(LocalDate.now(), true));
    }

    public void markAsNotCompleted() {
        executionHistory.add(new HabitExecution(LocalDate.now(), false));
    }
}
