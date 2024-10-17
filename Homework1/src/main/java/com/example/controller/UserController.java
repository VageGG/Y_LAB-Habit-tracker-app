 package com.example.controller;

 import com.example.service.HabitService;
import com.example.service.NotificationService;
import com.example.service.UserService;
import com.example.enums.Frequency;
import com.example.model.Habit;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

 /**
  * Controller for user interaction through console.
  */
 public class UserController {
     private final UserService userService;

     private final NotificationService notificationService;

     private final HabitService habitService;

     private final Scanner scanner = new Scanner(System.in);

     public UserController() {
         userService = new UserService();
         notificationService = new NotificationService();
         habitService = new HabitService();
     }

     public UserController(UserService userService, NotificationService notificationService, HabitService habitService) {
         this.userService = userService;
         this.notificationService = notificationService;
         this.habitService = habitService;
     }

     /**
      * Starts the user interface for registration and login.
      */
     public void start() {
         while (true) {
             System.out.println("1. Регистрация");
             System.out.println("2. Вход");
             System.out.println("3. Выход");
             System.out.print("Выберите опцию: ");
             int option = getInput();

             switch (option) {
                 case 1 -> registerUser();
                 case 2 -> loginUser();
                 case 3 -> {
                     System.out.println("Выход из программы.");
                     return;
                 }
                 default -> System.out.println("Неверный выбор, попробуйте снова.");
             }
         }
     }

     public void registerUser() {
         System.out.print("Введите имя: ");
         String name = scanner.nextLine();
         System.out.print("Введите email: ");
         String email = scanner.nextLine();
         System.out.print("Введите пароль: ");
         String password = scanner.nextLine();
         if (userService.register(name, email, password)) {
             System.out.println("Регистрация успешна!");
         } else {
             System.out.println("Пользователь с таким email уже зарегистрирован.");
         }
     }

     public void loginUser() {
         System.out.print("Введите email: ");
         String email = scanner.nextLine();
         System.out.print("Введите пароль: ");
         String password = scanner.nextLine();
         if (userService.login(email, password)) {
             System.out.println("Вход успешен!");
             manageHabits();
         } else {
             System.out.println("Неверный email или пароль.");
         }
     }

     public void manageHabits() {
         while (true) {
             System.out.println("1. Создать привычку");
             System.out.println("2. Редактировать привычку");
             System.out.println("3. Удалить привычку");
             System.out.println("4. Просмотреть привычки");
             System.out.println("5. Отметить выполнение привычки");
             System.out.println("6. Получить статистику по привычке");
             System.out.println("7. Получить отчет по прогрессу");
             System.out.println("8. Настроить напоминания");
             System.out.println("9. Назад");
             int option = getInput();

             switch (option) {
                 case 1 -> createHabit();
                 case 2 -> editHabit();
                 case 3 -> deleteHabit();
                 case 4 -> viewHabits();
                 case 5 -> markHabitExecution();
                 case 6 -> getHabitStatistics();
                 case 7 -> getHabitProgressReport();
                 case 8 -> setHabitReminders();
                 case 9 -> {
                     return;
                 }
                 default -> System.out.println("Неверный выбор, попробуйте снова.");
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

     private void setHabitReminders() {
         System.out.print("Введите ваш email: ");
         String email = scanner.nextLine();
         System.out.print("Введите название привычки для напоминания: ");
         String habitName = scanner.nextLine();

         Habit habit = findHabitByName(habitName);
         if (habit == null) {
             System.out.println("Привычка не найдена.");
             return;
         }

         notificationService.sendHabitReminder(email, habitName);
         System.out.println("Напоминание отправлено!");
     }

     private Habit findHabitByName(String name) {
         return habitService.getAllHabits().stream()
                 .filter(habit -> habit.getName().equals(name))
                 .findFirst()
                 .orElse(null);
     }

     private void getHabitProgressReport() {
         System.out.print("Введите название привычки для получения отчета: ");
         String name = scanner.nextLine();
         Habit habit = findHabitByName(name);
         if (habit == null) {
             System.out.println("Привычка не найдена.");
             return;
         }

         System.out.print("Введите начальную дату (yyyy-MM-dd): ");
         LocalDate startDate = LocalDate.parse(scanner.nextLine());
         System.out.print("Введите конечную дату (yyyy-MM-dd): ");
         LocalDate endDate = LocalDate.parse(scanner.nextLine());

         int streak = habitService.getCurrentStreak(habit);
         double successRate = habitService.getSuccessRate(habit, startDate, endDate);

         System.out.println("Отчет по прогрессу привычки '" + habit.getName() + "':");
         System.out.println("Текущая серия выполнения: " + streak);
         System.out.printf("Процент успешного выполнения с %s по %s: %.2f%%\n", startDate, endDate, successRate);
     }

     private void markHabitExecution() {
         System.out.print("Введите название привычки для отметки выполнения: ");
         String name = scanner.nextLine();
         Habit habit = findHabitByName(name);
         if (habit == null) {
             System.out.println("Привычка не найдена.");
             return;
         }

         habitService.markHabitAsCompleted(habit);
         System.out.println("Привычка отмечена как выполненная!");
     }

     private void getHabitStatistics() {
         System.out.print("Введите название привычки для получения статистики: ");
         String name = scanner.nextLine();
         Habit habit = findHabitByName(name);
         if (habit == null) {
             System.out.println("Привычка не найдена.");
             return;
         }

         System.out.print("Введите начальную дату (yyyy-MM-dd): ");
         LocalDate startDate = LocalDate.parse(scanner.nextLine());
         System.out.print("Введите конечную дату (yyyy-MM-dd): ");
         LocalDate endDate = LocalDate.parse(scanner.nextLine());

         long completedCount = habitService.getStatisticsForHabit(habit, startDate, endDate);
         System.out.println("Количество выполнений привычки за указанный период: " + completedCount);
     }

     public void createHabit() {
         System.out.print("Введите название привычки: ");
         String name = scanner.nextLine();
         if (name.isEmpty()) {
             System.out.println("Название привычки не может быть пустым.");
             return;
         }

         System.out.print("Введите описание привычки: ");
         String description = scanner.nextLine();
         System.out.print("Введите частоту (1 - Ежедневно, 2 - Еженедельно): ");
         int frequencyChoice = getInput();
         Frequency frequency = frequencyChoice == 1 ? Frequency.DAILY : Frequency.WEEKLY;

         habitService.createHabit(name, description, frequency);
         System.out.println("Привычка создана!");
     }

     private void editHabit() {
         System.out.print("Введите название привычки для редактирования: ");
         String name = scanner.nextLine();
         Habit habit = findHabitByName(name);
         if (habit == null) {
             System.out.println("Привычка не найдена.");
             return;
         }

         System.out.print("Введите новое название (оставьте пустым для пропуска): ");
         String newName = scanner.nextLine();
         System.out.print("Введите новое описание (оставьте пустым для пропуска): ");
         String newDescription = scanner.nextLine();

         habitService.updateHabit(habit, newName.isEmpty() ? habit.getName() : newName, newDescription.isEmpty() ? habit.getDescription() : newDescription);
         System.out.println("Привычка обновлена!");
     }

     public void deleteHabit() {
         System.out.print("Введите название привычки для удаления: ");
         String name = scanner.nextLine();
         Habit habit = findHabitByName(name);
         if (habit == null) {
             System.out.println("Привычка не найдена.");
             return;
         }

         habitService.deleteHabit(habit);
         System.out.println("Привычка удалена!");
     }

     public void viewHabits() {
         List<Habit> habits = habitService.getAllHabits();
         if (habits.isEmpty()) {
             System.out.println("У вас нет привычек.");
         } else {
             System.out.println("Ваши привычки:");
             for (Habit habit : habits) {
                 System.out.println("- " + habit.getName() + ": " + habit.getDescription());
             }
         }
     }
 }
