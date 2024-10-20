package com.example.service;

import com.example.enums.Frequency;
import com.example.model.Habit;
import com.example.repository.HabitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class HabitServiceTest {
    private HabitService habitService;
    private HabitRepository habitRepository;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        try (var statement = connection.createStatement()) {
            statement.execute("CREATE SCHEMA IF NOT EXISTS my_schema");
        }

        habitRepository = mock(HabitRepository.class);
        habitService = new HabitService(connection); // Передаем mock в HabitService
    }


    @Test
    @DisplayName("Обновление привычки")
    void testUpdateHabit() {
        Habit habit = new Habit("Старая привычка", "Описание", Frequency.DAILY, 1);
        habit.setId(1);
        habitService.updateHabit(habit, "Новая привычка", "Новое описание");

        assertEquals("Новая привычка", habit.getName());
        assertEquals("Новое описание", habit.getDescription());
    }

    @Test
    @DisplayName("Отметка привычки как выполненной")
    void testMarkHabitAsCompleted() {
        Habit habit = new Habit("Привычка", "Описание", Frequency.DAILY, 1);
        habitService.markHabitAsCompleted(habit);

        assertEquals(1, habit.getExecutionHistory().size());
        assertTrue(habit.getExecutionHistory().get(0).isCompleted());
    }

    @Test
    @DisplayName("Отметка привычки как невыполненной")
    void testMarkHabitAsNotCompleted() {
        Habit habit = new Habit("Привычка", "Описание", Frequency.DAILY, 1);
        habitService.markHabitAsNotCompleted(habit);

        assertEquals(1, habit.getExecutionHistory().size());
        assertFalse(habit.getExecutionHistory().get(0).isCompleted());
    }

    @Test
    @DisplayName("Получение статистики по привычке за период")
    void testGetStatisticsForHabit() {
        Habit habit = new Habit("Привычка", "Описание", Frequency.DAILY, 1);
        habit.markAsCompleted();
        habit.markAsNotCompleted();
        habit.markAsCompleted();

        LocalDate startDate = LocalDate.now().minusDays(2);
        LocalDate endDate = LocalDate.now();

        long stats = habitService.getStatisticsForHabit(habit, startDate, endDate);
        assertEquals(2, stats);
    }

    @Test
    @DisplayName("Получение процента успешности привычки")
    void testGetSuccessRate() {
        Habit habit = new Habit("Привычка", "Описание", Frequency.DAILY, 1);
        habit.markAsCompleted();
        habit.markAsNotCompleted();

        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now();

        double successRate = habitService.getSuccessRate(habit, startDate, endDate);
        assertEquals(50.0, successRate, 0.01);
    }
}
