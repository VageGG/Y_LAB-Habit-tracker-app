package com.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NotificationServiceTest {
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        notificationService = new NotificationService();
    }

    @Test
    @DisplayName("Отправка напоминания о привычке должна успешно отправить уведомление")
    void sendHabitReminder_shouldSendReminder() {
        // Arrange
        String userEmail = "test@example.com";
        String habitName = "Exercise";

        // Act
        notificationService.sendHabitReminder(userEmail, habitName);
    }
}
