package com.example;

import com.example.controller.UserController;
import com.example.controller.AdminController;
import com.example.repository.NotificationSender;
import com.example.service.HabitService;
import com.example.service.NotificationService;
import com.example.service.UserService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Main class to run the Habit Tracker application.
 */
public class HabitTrackerApp {

    private final UserService userService;
    private final HabitService habitService;
    private final NotificationService notificationService;
    private final AdminController adminController;
    private final UserController userController;
    private final Scanner scanner = new Scanner(System.in);

    public HabitTrackerApp() throws SQLException, IOException {
        Connection connection = DataBaseConnection.getConnection();

        LiquibaseMigration.runMigration();

        this.userService = new UserService(connection);
        this.habitService = new HabitService(connection);
        this.notificationService = new NotificationService(new NotificationSender() {
            @Override
            public void send(String email, String message) {
                System.out.println("Email: " + email + ", Сообщение: " + message);
            }
        });
        this.adminController = new AdminController(userService, habitService);
        this.userController = new UserController(userService, notificationService, habitService);
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
            System.out.println("1. Вход как администратор");
            System.out.println("2. Вход как пользователь");
            System.out.println("3. Выход");
            System.out.print("Выберите опцию: ");
            int option = getInput();

            switch (option) {
                case 1:
                    adminMenu();
                    break;
                case 2:
                    userController.start();
                    break;
                case 3:
                    System.out.println("Выход из приложения.");
                    return;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }

    private int getInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Пожалуйста, введите число: ");
            }
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("1. Показать пользователей");
            System.out.println("2. Показать привычки");
            System.out.println("3. Заблокировать пользователя");
            System.out.println("4. Удалить пользователя");
            System.out.println("5. Назад");
            System.out.print("Выберите опцию: ");
            int option = getInput();

            switch (option) {
                case 1:
                    adminController.displayUsers();
                    break;
                case 2:
                    adminController.displayHabits();
                    break;
                case 3:
                    System.out.print("Введите email пользователя для блокировки: ");
                    String blockEmail = scanner.nextLine();
                    adminController.blockUser(blockEmail);
                    break;
                case 4:
                    System.out.print("Введите email пользователя для удаления: ");
                    String deleteEmail = scanner.nextLine();
                    adminController.deleteUser(deleteEmail);
                    break;
                case 5:
                    return; // Возврат в главное меню
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }
}
