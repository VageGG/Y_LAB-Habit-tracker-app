package org.example.service;

import org.example.model.User;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void registerUser_withValidEmailAndPassword_shouldSucceed() {
        // Arrange
        String name = "John";
        String email = "john@example.com";
        String password = "Password-13";

        // Act
        boolean result = userService.register(name, email, password);

        // Assert
        assertThat(result).isTrue();
        assertThat(userService.getUser(email)).isNotNull();
    }

    @Test
    void registerUser_withInvalidEmail_shouldFail() {
        // Arrange
        String name = "John";
        String email = "invalid-email";
        String password = "Password123!";

        // Act
        boolean result = userService.register(name, email, password);

        // Assert
        assertThat(result).isFalse();
        assertThat(userService.getUser(email)).isNull();
    }

    @Test
    void registerUser_withInvalidPassword_shouldFail() {
        // Arrange
        String name = "John";
        String email = "john@example.com";
        String password = "short";

        // Act
        boolean result = userService.register(name, email, password);

        // Assert
        assertThat(result).isFalse();
        assertThat(userService.getUser(email)).isNull();
    }

    @Test
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
    void updateUser_withInvalidPassword_shouldFail() {
        // Arrange
        String email = "john@example.com";
        String name = "John";
        String password = "Password123!";
        userService.register(name, email, password);

        // Act
        userService.updateUser(email, "John Updated", "short");

        // Assert
        User updatedUser = userService.getUser(email);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getName()).isEqualTo("John");
        assertThat(updatedUser.getPassword()).isEqualTo(password);
    }

    @Test
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
        assertThat(userService.getUser(email)).isNull();
    }
}
