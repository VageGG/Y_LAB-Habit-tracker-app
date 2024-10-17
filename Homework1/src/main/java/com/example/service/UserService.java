 package com.example.service;

 import com.example.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

 /**
  * Service for managing user registration and authentication.
  */
 public class UserService {
     private final Map<String, User> users = new HashMap<>();

     /**
      * Registers a new user with the specified name, email, and password.
      *
      * @param name     the name of the new user
      * @param email    the email of the new user
      * @param password the password of the new user
      * @return true if registration is successful, false if the email is already in use or validation fails
      */
     public boolean register(String name, String email, String password) {
         if (!isEmailValid(email)) {
             System.out.println("Ошибка: Некорректный email.");
             return false;
         }

         if (!isPasswordValid(password)) {
             System.out.println("Ошибка: Некорректный пароль.");
             return false;
         }

         if (users.containsKey(email)) {
             System.out.println("Ошибка: Пользователь с таким email уже зарегистрирован.");
             return false;
         }

         users.put(email, new User(name, email, password));
          return true;
     }

     /**
      * Logs in a user with the specified email and password.
      *
      * @param email    the email of the user
      * @param password the password of the user
      * @return true if login is successful, false otherwise
      */
     public boolean login(String email, String password) {
         User user = users.get(email);
         return user != null && user.getPassword().equals(password);
     }

     /**
      * Updates user profile.
      *
      * @param email       the user's email
      * @param name        the new name
      * @param newPassword the new password
      */
     public void updateUser(String email, String name, String newPassword) {
         User user = users.get(email);
         if (user != null) {
             if (newPassword != null && !newPassword.isEmpty()) {
                 if (!isPasswordValid(newPassword)) {
                     System.out.println("Ошибка: Некорректный пароль.");
                     return;
                 }
                 user.setPassword(newPassword);
             }

             user.setName(name);
         } else {
             System.out.println("Пользователь не найден.");
         }
     }

     /**
      * Deletes a user by email.
      *
      * @param email the email of the user to delete
      * @return true if deletion was successful, false otherwise
      */
     public boolean deleteUser(String email) {
        return users.remove(email) != null;
    }

     /**
      * Retrieves a user by their email.
      *
      * @param email the user's email
      * @return the User object, or null if not found
      */
     public User getUser(String email) {
         return users.get(email);
     }

     /**
      * Blocks a user by their email.
      *
      * @param email the user's email
      * @return true if the user was blocked, false otherwise
      */
     public boolean blockUser(String email) {
         User user = users.get(email);
         if (user != null) {
             user.setBlocked(true);
             return true;
         }
         return false;
     }

     /**
      * Retrieves the list of all users.
      *
      * @return list of users
      */
     public List<User> getAllUsers() {
         return users.values().stream().collect(Collectors.toList());
     }

     /**
      * Validates the email format.
      *
      * @param email the email to validate
      * @return true if email is valid, false otherwise
      */
     private boolean isEmailValid(String email) {
         String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                 + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
         Pattern pattern = Pattern.compile(regex);
         Matcher matcher = pattern.matcher(email);
         return matcher.matches();
     }

     /**
      * Validates the password strength.
      *
      * @param password the password to validate
      * @return true if password is valid, false otherwise
      */
     private boolean isPasswordValid(String password) {
         if (password.length() < 6) {
             System.out.println("Ошибка: Пароль должен быть не менее 6 символов.");
             return false;
         }

         Pattern numberPattern = Pattern.compile("[0-9]");
         Pattern uppercasePattern = Pattern.compile("[A-Z]");
         Pattern lowercasePattern = Pattern.compile("[a-z]");
         Pattern specialCharacterPattern = Pattern.compile("[$&+,:;=?@#|'<>.-^*()%!]");

         Matcher number = numberPattern.matcher(password);
         Matcher uppercase = uppercasePattern.matcher(password);
         Matcher lowercase = lowercasePattern.matcher(password);
         Matcher specialCharacter = specialCharacterPattern.matcher(password);

         return number.find() && uppercase.find() && lowercase.find() && specialCharacter.find();
     }
 }
