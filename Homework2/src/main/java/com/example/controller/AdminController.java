package com.example.controller;

import com.example.model.Habit;
import com.example.model.User;
import com.example.service.HabitService;
import com.example.service.UserService;

import java.util.List;

/**
 * Controller for performing administrative operations.
 */
public class AdminController {
    private final UserService userService;
    private final HabitService habitService;

    /**
     * Constructs an AdminController with the specified UserService and HabitService.
     *
     * @param userService  the service for managing users
     * @param habitService the service for managing habits
     */
    public AdminController(UserService userService, HabitService habitService) {
        this.userService = userService;
        this.habitService = habitService;
    }

    /**
     * Displays the list of all users.
     */
    public void displayUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("Нет зарегистрированных пользователей.");
        } else {
            System.out.println("Список пользователей:");
            users.forEach(user -> System.out.println("- " + user.getEmail()));
        }
    }

    /**
     * Displays the list of all habits.
     */
    public void displayHabits() {
        List<Habit> habits = habitService.getAllHabits();
        if (habits.isEmpty()) {
            System.out.println("Нет зарегистрированных привычек.");
        } else {
            System.out.println("Список привычек:");
            habits.forEach(habit -> System.out.println("- " + habit.getName()));
        }
    }

    /**
     * Blocks a user by their email.
     *
     * @param email the user's email
     */
    public void blockUser(String email) {
        if (userService.blockUser(email)) {
            printSuccessMessage("Пользователь " + email + " был заблокирован.");
        } else {
            printErrorMessage("Пользователь не найден или уже заблокирован.");
        }
    }

    /**
     * Deletes a user by their email.
     *
     * @param email the user's email
     */
    public void deleteUser(String email) {
        if (userService.deleteUser(email)) {
            printSuccessMessage("Пользователь " + email + " был удален.");
        } else {
            printErrorMessage("Пользователь не найден.");
        }
    }

    /**
     * Displays a success message.
     *
     * @param message the success message
     */
    private void printSuccessMessage(String message) {
        System.out.println("Успех: " + message);
    }

    /**
     * Displays an error message.
     *
     * @param message the error message
     */
    private void printErrorMessage(String message) {
        System.out.println("Ошибка: " + message);
    }
}
