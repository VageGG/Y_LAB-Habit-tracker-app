package com.example.service;

import com.example.enums.Frequency;
import com.example.model.Habit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HabitServiceTest {
    private HabitService habitService;

    @BeforeEach
    void setUp() {
        habitService = new HabitService();
    }

    @Test
    @DisplayName("Создание привычки с валидными данными должно пройти успешно")
    void createHabit_withValidData_shouldSucceed() {
        // Arrange
        String name = "Exercise";
        String description = "Morning exercise routine";
        Frequency frequency = Frequency.DAILY;

        // Act
        habitService.createHabit(name, description, frequency);
        List<Habit> habits = habitService.getAllHabits();

        // Assert
        assertThat(habits).hasSize(1);
        assertThat(habits.get(0).getName()).isEqualTo(name);
        assertThat(habits.get(0).getDescription()).isEqualTo(description);
        Assertions.assertThat(habits.get(0).getFrequency()).isEqualTo(frequency);
    }

    @Test
    @DisplayName("Обновление привычки с валидными данными должно изменить привычку")
    void updateHabit_withValidData_shouldUpdateHabit() {
        // Arrange
        String name = "Exercise";
        String description = "Morning exercise routine";
        Frequency frequency = Frequency.DAILY;
        habitService.createHabit(name, description, frequency);

        Habit habit = habitService.getAllHabits().get(0);

        // Act
        habitService.updateHabit(habit, "Evening Walk", "Walk in the park");
        Habit updatedHabit = habitService.getAllHabits().get(0);

        // Assert
        assertThat(updatedHabit.getName()).isEqualTo("Evening Walk");
        assertThat(updatedHabit.getDescription()).isEqualTo("Walk in the park");
    }

    @Test
    @DisplayName("Удаление существующей привычки должно удалить привычку")
    void deleteHabit_withExistingHabit_shouldRemoveHabit() {
        // Arrange
        String name = "Exercise";
        String description = "Morning exercise routine";
        Frequency frequency = Frequency.DAILY;
        habitService.createHabit(name, description, frequency);

        Habit habit = habitService.getAllHabits().get(0);

        // Act
        habitService.deleteHabit(habit);

        // Assert
        Assertions.assertThat(habitService.getAllHabits()).isEmpty();
    }

    @Test
    @DisplayName("Получение привычек по дате создания должно вернуть привычки")
    void getHabitsByCreationDate_withValidDate_shouldReturnHabits() {
        // Arrange
        String name1 = "Exercise";
        String description1 = "Morning exercise routine";
        Frequency frequency1 = Frequency.DAILY;
        habitService.createHabit(name1, description1, frequency1);

        String name2 = "Reading";
        String description2 = "Read 10 pages";
        Frequency frequency2 = Frequency.DAILY;
        habitService.createHabit(name2, description2, frequency2);

        LocalDate creationDate = habitService.getAllHabits().get(0).getCreationDate();

        // Act
        List<Habit> habits = habitService.getHabitsByCreationDate(creationDate);

        // Assert
        assertThat(habits).hasSize(2);
    }

    @Test
    @DisplayName("Отметка привычки как выполненной должна отметить привычку как выполненную")
    void markHabitAsCompleted_shouldMarkHabitAsCompleted() {
        // Arrange
        String name = "Exercise";
        String description = "Morning exercise routine";
        Frequency frequency = Frequency.DAILY;
        habitService.createHabit(name, description, frequency);

        Habit habit = habitService.getAllHabits().get(0);

        // Act
        habitService.markHabitAsCompleted(habit);

        // Assert
        assertThat(habit.getExecutionHistory().get(0).isCompleted()).isTrue();
    }

    @Test
    @DisplayName("Получение текущей серии выполнения привычки должно вернуть текущую серию")
    void getCurrentStreak_withCompletedHabit_shouldReturnCurrentStreak() {
        // Arrange
        String name = "Exercise";
        String description = "Morning exercise routine";
        Frequency frequency = Frequency.DAILY;
        habitService.createHabit(name, description, frequency);

        Habit habit = habitService.getAllHabits().get(0);
        habitService.markHabitAsCompleted(habit);

        // Act
        int streak = habitService.getCurrentStreak(habit);

        // Assert
        assertThat(streak).isEqualTo(1);
    }

    @Test
    @DisplayName("Получение статистики по привычке за указанный период должно вернуть статистику")
    void getStatisticsForHabit_withValidPeriod_shouldReturnStatistics() {
        // Arrange
        String name = "Exercise";
        String description = "Morning exercise routine";
        Frequency frequency = Frequency.DAILY;
        habitService.createHabit(name, description, frequency);

        Habit habit = habitService.getAllHabits().get(0);
        habitService.markHabitAsCompleted(habit);

        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(1);

        // Act
        long completedCount = habitService.getStatisticsForHabit(habit, startDate, endDate);

        // Assert
        assertThat(completedCount).isEqualTo(1);
    }

    @Test
    @DisplayName("Получение процента успешного выполнения привычки за указанный период должно вернуть процент успеха")
    void getSuccessRate_withValidPeriod_shouldReturnSuccessRate() {
        // Arrange
        String name = "Exercise";
        String description = "Morning exercise routine";
        Frequency frequency = Frequency.DAILY;
        habitService.createHabit(name, description, frequency);

        Habit habit = habitService.getAllHabits().get(0);
        habitService.markHabitAsCompleted(habit);

        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(1);

        // Act
        double successRate = habitService.getSuccessRate(habit, startDate, endDate);

        // Assert
        assertThat(successRate).isEqualTo(100.0);
    }
}
