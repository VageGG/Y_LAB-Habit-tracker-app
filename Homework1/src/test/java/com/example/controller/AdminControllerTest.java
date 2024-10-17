 package com.example.controller;

 import com.example.enums.Frequency;
import com.example.model.Habit;
import com.example.model.User;
import com.example.service.HabitService;
import com.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

 public class AdminControllerTest {

     private AdminController adminController;

     private UserService userService;

     private HabitService habitService;

     @BeforeEach
     public void setUp() {
         userService = Mockito.mock(UserService.class);
         habitService = Mockito.mock(HabitService.class);
         adminController = new AdminController();
     }

     @Test
     public void testDisplayUsers_EmptyList() {
         when(userService.getAllUsers()).thenReturn(new ArrayList<>());
         adminController.displayUsers();
     }

     @Test
     public void testDisplayUsers_NonEmptyList() {
         List<User> users = List.of(new User("user1","user1@example.com", "Password-13"), new User("user2","user2@example.com", "Password-11"));
         when(userService.getAllUsers()).thenReturn(users);
         adminController.displayUsers();
     }

     @Test
     public void testBlockUser_UserExists() {
         String email = "user1@example.com";
         when(userService.blockUser(email)).thenReturn(true);
         adminController.blockUser(email);
     }

     @Test
     public void testBlockUser_UserDoesNotExist() {
         String email = "user1@example.com";
         when(userService.blockUser(email)).thenReturn(false);
         adminController.blockUser(email);
     }

     @Test
     public void testDeleteUser_UserExists() {
         String email = "user1@example.com";
         when(userService.deleteUser(email)).thenReturn(true);
         adminController.deleteUser(email);
     }

     @Test
     public void testDeleteUser_UserDoesNotExist() {
         String email = "user1@example.com";
         when(userService.deleteUser(email)).thenReturn(false);
         adminController.deleteUser(email);
     }

     @Test
     public void testDisplayHabits_EmptyList() {
         when(habitService.getAllHabits()).thenReturn(new ArrayList<>());
         adminController.displayHabits();
     }

     @Test
     public void testDisplayHabits_NonEmptyList() {
         List<Habit> habits = List.of(new Habit("habit1","habits", Frequency.DAILY), new Habit("Habit2", "habits2", Frequency.WEEKLY));
         when(habitService.getAllHabits()).thenReturn(habits);
         adminController.displayHabits();
     }
 }

