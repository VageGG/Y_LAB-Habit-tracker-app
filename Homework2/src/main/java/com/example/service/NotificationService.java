package com.example.service;

import com.example.repository.NotificationSender;

/**
 * Service for sending notifications to users.
 */
public class NotificationService {

    private final NotificationSender notificationSender;

    public NotificationService(NotificationSender notificationSender) {
        this.notificationSender = notificationSender;
    }

    /**
     * Sends a reminder notification for a habit.
     *
     * @param userEmail the user's email
     * @param habitName the name of the habit
     */
    public void sendHabitReminder(String userEmail, String habitName) {
        String message = "Напоминание: Не забудьте выполнить привычку '" + habitName + "'!";
        notificationSender.send(userEmail, message);
    }
}
