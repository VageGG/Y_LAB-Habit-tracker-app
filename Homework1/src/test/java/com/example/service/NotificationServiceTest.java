 package com.example.service;

 import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

 public class NotificationServiceTest {
     private NotificationService notificationService;

     @BeforeEach
     void setUp() {
        notificationService = new NotificationService();
    }

     @Test
     void sendHabitReminder_shouldSendReminder() {
         // Arrange
         String userEmail = "test@example.com";
         String habitName = "Exercise";

         // Act
         notificationService.sendHabitReminder(userEmail, habitName);
     }
 }

