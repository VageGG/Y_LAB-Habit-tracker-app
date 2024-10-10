package org.example.model;

import org.example.enums.Frequency;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a habit for the user.
 */
public class Habit {
    private String name;
    private String description;
    private Frequency frequency;
    private LocalDate creationDate;
    private final List<HabitExecution> executionHistory;

    public Habit(String name, String description, Frequency frequency) {
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.creationDate = LocalDate.now();
        this.executionHistory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HabitExecution> getExecutionHistory() {
        return new ArrayList<>(executionHistory);
    }

    public int getCurrentStreak() {
        int streak = 0;
        LocalDate today = LocalDate.now();

        for (int i = executionHistory.size() - 1; i >= 0; i--) {
            HabitExecution execution = executionHistory.get(i);
            if (execution.isCompleted() && execution.getDate().isEqual(today.minusDays(streak))) {
                streak++;
            } else {
                break;
            }
        }
        return streak;
    }

    public double getSuccessRate(LocalDate startDate, LocalDate endDate) {
        long completedCount = executionHistory.stream()
                .filter(execution -> !execution.getDate().isBefore(startDate) && !execution.getDate().isAfter(endDate))
                .filter(HabitExecution::isCompleted)
                .count();
        long totalCount = executionHistory.stream()
                .filter(execution -> !execution.getDate().isBefore(startDate) && !execution.getDate().isAfter(endDate))
                .count();
        return totalCount > 0 ? (double) completedCount / totalCount * 100 : 0;
    }

    public void markAsCompleted() {
        executionHistory.add(new HabitExecution(LocalDate.now(), true));
    }

    public void markAsNotCompleted() {
        executionHistory.add(new HabitExecution(LocalDate.now(), false));
    }
}
