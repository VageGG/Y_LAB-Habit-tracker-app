package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Represents an execution of a habit.
 */
@AllArgsConstructor
@Getter
@Setter
public class HabitExecution {
    private LocalDate date;
    private boolean completed;
}
