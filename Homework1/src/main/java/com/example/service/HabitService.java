package com.example.service;

import com.example.enums.Frequency;
import com.example.model.Habit;
import com.example.model.HabitExecution;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing habits.
 */
public class HabitService {
    private final List<Habit> habits = new ArrayList<>();

    /**
     * Creates a new habit.
     *
     * @param name        the name of the habit
     * @param description the description of the habit
     * @param frequency   the frequency of the habit
     */
    public void createHabit(String name, String description, Frequency frequency) {
        Habit habit = new Habit(name, description, frequency);
        habits.add(habit);
    }

    /**
     * Updates an existing habit.
     *
     * @param habit          the habit to update
     * @param newName        the new name
     * @param newDescription the new description
     */
    public void updateHabit(Habit habit, String newName, String newDescription) {
        if (newName != null && !newName.isEmpty()) {
            habit.setName(newName);
        }
        if (newDescription != null && !newDescription.isEmpty()) {
            habit.setDescription(newDescription);
        }
    }

    /**
     * Deletes a habit.
     *
     * @param habit the habit to delete
     */
    public void deleteHabit(Habit habit) {
        habits.remove(habit);
    }

    /**
     * Returns a list of all habits.
     *
     * @return list of habits
     */
    public List<Habit> getAllHabits() {
        return new ArrayList<>(habits);
    }

    /**
     * Returns a filtered list of habits by creation date.
     *
     * @param creationDate the creation date to filter by
     * @return filtered list of habits
     */
    public List<Habit> getHabitsByCreationDate(LocalDate creationDate) {
        return habits.stream()
                .filter(habit -> habit.getCreationDate().equals(creationDate))
                .collect(Collectors.toList());
    }

    /**
     * Marks a habit as completed for today.
     *
     * @param habit the habit to mark
     */
    public void markHabitAsCompleted(Habit habit) {
        habit.markAsCompleted();
    }

    /**
     * Marks a habit as not completed for today.
     *
     * @param habit the habit to mark
     */
    public void markHabitAsNotCompleted(Habit habit) {
        habit.markAsNotCompleted();
    }

    /**
     * Generates statistics for a habit over a given period.
     *
     * @param habit     the habit to analyze
     * @param startDate the start date
     * @param endDate   the end date
     * @return statistics of completed executions
     */
    public long getStatisticsForHabit(Habit habit, LocalDate startDate, LocalDate endDate) {
        return habit.getExecutionHistory().stream()
                .filter(execution -> !execution.getDate().isBefore(startDate) && !execution.getDate().isAfter(endDate))
                .filter(HabitExecution::isCompleted)
                .count();
    }

    /**
     * Gets the current streak for a habit.
     *
     * @param habit the habit to analyze
     * @return current streak count
     */
    public int getCurrentStreak(Habit habit) {
        return habit.getCurrentStreak();
    }

    /**
     * Gets the success rate for a habit over a given period.
     *
     * @param habit     the habit to analyze
     * @param startDate the start date
     * @param endDate   the end date
     * @return success rate percentage
     */
    public double getSuccessRate(Habit habit, LocalDate startDate, LocalDate endDate) {
        return habit.getSuccessRate(startDate, endDate);
    }
}
