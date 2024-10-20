package com.example.service;

import com.example.repository.NotificationSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class NotificationServiceTest {

    private NotificationSender notificationSender;
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        notificationSender = mock(NotificationSender.class);
        notificationService = new NotificationService(notificationSender);
    }

    @Test
    @DisplayName("Отправка напоминания о привычке")
    void testSendHabitReminder() {
        String userEmail = "user@example.com";
        String habitName = "Привычка";

        notificationService.sendHabitReminder(userEmail, habitName);

        String expectedMessage = "Напоминание: Не забудьте выполнить привычку '" + habitName + "'!";
        verify(notificationSender, times(1)).send(userEmail, expectedMessage);
    }

    @Test
    @DisplayName("Отправка напоминания с пустым именем привычки")
    void testSendHabitReminderWithEmptyHabitName() {
        String userEmail = "user@example.com";
        String habitName = "";

        notificationService.sendHabitReminder(userEmail, habitName);

        String expectedMessage = "Напоминание: Не забудьте выполнить привычку ''!";
        verify(notificationSender, times(1)).send(userEmail, expectedMessage);
    }

    @Test
    @DisplayName("Отправка напоминания с пустым email")
    void testSendHabitReminderWithEmptyEmail() {
        String userEmail = "";
        String habitName = "Привычка";

        notificationService.sendHabitReminder(userEmail, habitName);

        String expectedMessage = "Напоминание: Не забудьте выполнить привычку '" + habitName + "'!";
        verify(notificationSender, times(1)).send(userEmail, expectedMessage);
    }
}
