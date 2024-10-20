package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service for managing user registration and authentication.
 */
public class UserService {
    private final UserRepository userRepository;

    /**
     * Constructs a UserService with the specified database connection.
     *
     * @param connection the database connection
     */
    public UserService(Connection connection) {
        this.userRepository = new UserRepository(connection);
    }

    /**
     * Registers a new user with the specified name, email, and password.
     *
     * @param name     the name of the new user
     * @param email    the email of the new user
     * @param password the password of the new user
     * @return true if registration is successful, false otherwise
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

        try {
            if (userRepository.findByEmail(email) != null) {
                System.out.println("Ошибка: Пользователь с таким email уже зарегистрирован.");
                return false;
            }

            User user = new User(name, email, password);
            userRepository.save(user);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Logs in a user with the specified email and password.
     *
     * @param email    the email of the user
     * @param password the password of the user
     * @return true if login is successful, false otherwise
     */
    public boolean login(String email, String password) {
        try {
            User user = userRepository.findByEmail(email);
            return user != null && user.getPassword().equals(password);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates user profile with the specified email, name, and new password.
     *
     * @param email       the user's email
     * @param name        the new name
     * @param newPassword the new password
     */
    public void updateUser(String email, String name, String newPassword) {
        try {
            User user = userRepository.findByEmail(email);
            if (user != null) {
                user.setName(name);
                if (newPassword != null && !newPassword.isEmpty()) {
                    user.setPassword(newPassword);
                }
                userRepository.update(user);
            } else {
                System.out.println("Ошибка: Пользователь с таким email не найден.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a user by email.
     *
     * @param email the email of the user to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteUser(String email) {
        try {
            userRepository.deleteByEmail(email);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user to retrieve
     * @return the User object, or null if not found
     */
    public User getUser(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Blocks a user by their email.
     *
     * @param email the email of the user to block
     * @return true if the user was blocked, false otherwise
     */
    public boolean blockUser(String email) {
        try {
            User user = userRepository.findByEmail(email);
            if (user != null) {
                user.setBlocked(true);
                userRepository.update(user);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves a list of all users.
     *
     * @return a list of all users
     */
    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
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
