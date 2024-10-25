package com.example;

import com.example.config.AppFactory;
import com.example.config.DataBaseConnection;
import com.example.config.InputHandler;
import com.example.config.LiquibaseMigration;
import com.example.controller.UserController;
import com.example.controller.AdminController;
import com.example.service.HabitService;
import com.example.service.NotificationService;
import com.example.service.UserService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Main class to run the Habit Tracker application.
 */
public class HabitTrackerApp {

    private final UserService userService;
    private final HabitService habitService;
    private final NotificationService notificationService;
    private final AdminController adminController;
    private final UserController userController;
    private final InputHandler inputHandler;

    public HabitTrackerApp() throws SQLException, IOException {
        Connection connection = DataBaseConnection.getConnection();
        LiquibaseMigration.runMigration();

        this.userService = AppFactory.createUserService();
        this.habitService = AppFactory.createHabitService();
        this.notificationService = AppFactory.createNotificationService();
        this.adminController = AppFactory.createAdminController(userService, habitService);
        this.userController = AppFactory.createUserController(userService, notificationService, habitService);
        this.inputHandler = AppFactory.createInputHandler();
    }

    public static void main(String[] args) {
        try {
            HabitTrackerApp app = new HabitTrackerApp();
            app.start();
        } catch (SQLException | IOException e) {
            System.err.println("Ошибка инициализации приложения: " + e.getMessage());
        }
    }

    private void start() {
        while (true) {
            System.out.println("""
                1. Вход как администратор
                2. Вход как пользователь
                3. Выход
                Выберите опцию:""");
            int option = inputHandler.getIntInput();

            switch (option) {
                case 1 -> adminMenu();
                case 2 -> userController.start();
                case 3 -> {
                    System.out.println("Выход из приложения.");
                    return;
                }
                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("""
                1. Показать пользователей
                2. Показать привычки
                3. Заблокировать пользователя
                4. Удалить пользователя
                5. Назад
                Выберите опцию:""");
            int option = inputHandler.getIntInput();

            switch (option) {
                case 1 -> adminController.displayUsers();
                case 2 -> adminController.displayHabits();
                case 3 -> {
                    System.out.print("Введите email пользователя для блокировки: ");
                    String blockEmail = inputHandler.getStringInput();
                    adminController.blockUser(blockEmail);
                }
                case 4 -> {
                    System.out.print("Введите email пользователя для удаления: ");
                    String deleteEmail = inputHandler.getStringInput();
                    adminController.deleteUser(deleteEmail);
                }
                case 5 -> { return; }
                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }
}

