package com.example.service;

/**
 * Service for sending notifications to users.
 */
public class NotificationService {

    /**
     * Sends a reminder notification for a habit.
     *
     * @param userEmail the user's email
     * @param habitName the name of the habit
     */
    public void sendHabitReminder(String userEmail, String habitName) {
        System.out.println("Напоминание: Не забудьте выполнить привычку '" + habitName + "'!");
    }
}
