package org.example.model;

import java.time.LocalDate;

/**
 * Represents the execution of a habit on a specific date.
 */
public class HabitExecution {
    private final LocalDate date;
    private final boolean completed;

    /**
     * Constructs a HabitExecution instance.
     *
     * @param date      the date of the habit execution
     * @param completed the status of whether the habit was completed
     * @throws IllegalArgumentException if the provided date is in the future
     */
    public HabitExecution(LocalDate date, boolean completed) {
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Execution date cannot be in the future.");
        }
        this.date = date;
        this.completed = completed;
    }

    /**
     * Returns the date of the habit execution.
     *
     * @return the execution date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns whether the habit was completed on this date.
     *
     * @return true if the habit was completed, false otherwise
     */
    public boolean isCompleted() {
        return completed;
    }
}
