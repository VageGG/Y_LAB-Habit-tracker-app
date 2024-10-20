package com.example.controller;

import com.example.enums.Frequency;
import com.example.model.Habit;
import com.example.model.User;
import com.example.service.HabitService;
import com.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class AdminControllerTest {
    private UserService userService;
    private HabitService habitService;
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        habitService = Mockito.mock(HabitService.class);
        adminController = new AdminController(userService, habitService);
    }

    @Test
    @DisplayName("Отображение пользователей, когда пользователи существуют")
    void testDisplayUsers_WhenUsersExist() {
        // Arrange
        List<User> users = Arrays.asList(
                new User("John Doe", "john@example.com", "password123"),
                new User("Jane Doe", "jane@example.com", "password456")
        );
        when(userService.getAllUsers()).thenReturn(users);

        // Act & Assert
        assertDoesNotThrow(() -> adminController.displayUsers());

        // Verify
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    @DisplayName("Отображение пользователей, когда нет зарегистрированных пользователей")
    void testDisplayUsers_WhenNoUsersExist() {
        // Arrange
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertDoesNotThrow(() -> adminController.displayUsers());

        // Verify
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    @DisplayName("Отображение привычек, когда привычки существуют")
    void testDisplayHabits_WhenHabitsExist() {
        // Arrange
        List<Habit> habits = Arrays.asList(
                new Habit("Exercise", "Заниматься спортом", Frequency.WEEKLY,1),
                new Habit("Read", "Читать книги", Frequency.DAILY, 2)
        );
        when(habitService.getAllHabits()).thenReturn(habits);

        // Act & Assert
        assertDoesNotThrow(() -> adminController.displayHabits());

        // Verify
        verify(habitService, times(1)).getAllHabits();
    }

    @Test
    @DisplayName("Отображение привычек, когда нет зарегистрированных привычек")
    void testDisplayHabits_WhenNoHabitsExist() {
        // Arrange
        when(habitService.getAllHabits()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertDoesNotThrow(() -> adminController.displayHabits());

        // Verify
        verify(habitService, times(1)).getAllHabits();
    }

    @Test
    @DisplayName("Блокировка пользователя, если блокировка успешна")
    void testBlockUser_Success() {
        // Arrange
        String email = "john@example.com";
        when(userService.blockUser(email)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> adminController.blockUser(email));

        // Verify
        verify(userService, times(1)).blockUser(email);
    }

    @Test
    @DisplayName("Блокировка пользователя, если блокировка не удалась")
    void testBlockUser_Failure() {
        // Arrange
        String email = "unknown@example.com";
        when(userService.blockUser(email)).thenReturn(false);

        // Act
        assertDoesNotThrow(() -> adminController.blockUser(email));

        // Verify
        verify(userService, times(1)).blockUser(email);
    }

    @Test
    @DisplayName("Удаление пользователя, если удаление успешно")
    void testDeleteUser_Success() {
        // Arrange
        String email = "john@example.com";
        when(userService.deleteUser(email)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> adminController.deleteUser(email));

        // Verify
        verify(userService, times(1)).deleteUser(email);
    }

    @Test
    @DisplayName("Удаление пользователя, если удаление не удалось")
    void testDeleteUser_Failure() {
        // Arrange
        String email = "unknown@example.com";
        when(userService.deleteUser(email)).thenReturn(false);

        // Act
        assertDoesNotThrow(() -> adminController.deleteUser(email));

        // Verify
        verify(userService, times(1)).deleteUser(email);
    }
}
