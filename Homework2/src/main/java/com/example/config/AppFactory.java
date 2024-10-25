package com.example.config;

import com.example.controller.AdminController;
import com.example.controller.UserController;
import com.example.repository.NotificationSender;
import com.example.service.HabitService;
import com.example.service.NotificationService;
import com.example.service.UserService;

import java.sql.Connection;

public class AppFactory {
    public static UserService createUserService() {
        return new UserService();
    }

    public static HabitService createHabitService() {
        return new HabitService();
    }

    public static NotificationService createNotificationService() {
        return new NotificationService(new NotificationSender() {
            @Override
            public void send(String email, String message) {
                System.out.println("Email: " + email + ", Сообщение: " + message);
            }
        });
    }

    public static AdminController createAdminController(UserService userService, HabitService habitService) {
        return new AdminController(userService, habitService);
    }

    public static UserController createUserController(UserService userService, NotificationService notificationService, HabitService habitService) {
        return new UserController(userService, notificationService, habitService);
    }

    public static InputHandler createInputHandler() {
        return new InputHandler();
    }
}

