package com.example.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    @DisplayName("Регистрация пользователя с валидным email и паролем должна пройти успешно")
    void registerUser_withValidEmailAndPassword_shouldSucceed() {
        // Arrange
        String name = "John";
        String email = "john@example.com";
        String password = "Password-13";

        // Act
        boolean result = userService.register(name, email, password);

        // Assert
        assertThat(result).isTrue();
        Assertions.assertThat(userService.getUser(email)).isNotNull();
    }

    @Test
    @DisplayName("Регистрация пользователя с некорректным email должна завершиться неудачей")
    void registerUser_withInvalidEmail_shouldFail() {
        // Arrange
        String name = "John";
        String email = "invalid-email";
        String password = "Password123!";

        // Act
        boolean result = userService.register(name, email, password);

        // Assert
        assertThat(result).isFalse();
        Assertions.assertThat(userService.getUser(email)).isNull();
    }

    @Test
    @DisplayName("Регистрация пользователя с некорректным паролем должна завершиться неудачей")
    void registerUser_withInvalidPassword_shouldFail() {
        // Arrange
        String name = "John";
        String email = "john@example.com";
        String password = "short";

        // Act
        boolean result = userService.register(name, email, password);

        // Assert
        assertThat(result).isFalse();
        Assertions.assertThat(userService.getUser(email)).isNull();
    }

    @Test
    @DisplayName("Вход с корректными учетными данными должен пройти успешно")
    void login_withValidCredentials_shouldSucceed() {
        // Arrange
        String name = "John";
        String email = "john@example.com";
        String password = "Password123!";
        userService.register(name, email, password);

        // Act
        boolean result = userService.login(email, password);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Вход с некорректным паролем должен завершиться неудачей")
    void login_withInvalidPassword_shouldFail() {
        // Arrange
        String name = "John";
        String email = "john@example.com";
        String password = "Password123!";
        userService.register(name, email, password);

        // Act
        boolean result = userService.login(email, "WrongPassword");

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Удаление существующего пользователя должно пройти успешно")
    void deleteUser_withExistingUser_shouldSucceed() {
        // Arrange
        String name = "John";
        String email = "john@example.com";
        String password = "Password123!";
        userService.register(name, email, password);

        // Act
        boolean result = userService.deleteUser(email);

        // Assert
        assertThat(result).isTrue();
        Assertions.assertThat(userService.getUser(email)).isNull();
    }
}
