package com.example.repository;

import com.example.model.User;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты для UserRepository")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {

    private PostgreSQLContainer<?> postgresContainer;
    private Connection connection;
    private UserRepository userRepository;

    @BeforeAll
    public void setUp() throws SQLException {
        postgresContainer = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("testdb")
                .withUsername("user")
                .withPassword("password");
        postgresContainer.start();

        String jdbcUrl = postgresContainer.getJdbcUrl();
        String username = postgresContainer.getUsername();
        String password = postgresContainer.getPassword();
        connection = DriverManager.getConnection(jdbcUrl, username, password);

        initializeDatabase();

        userRepository = new UserRepository();
    }

    private void initializeDatabase() throws SQLException {
        String createSchemaQuery = "CREATE SCHEMA IF NOT EXISTS my_schema";
        String createTableQuery = """
            CREATE TABLE IF NOT EXISTS my_schema.users (
                id SERIAL PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL UNIQUE,
                password VARCHAR(255) NOT NULL,
                blocked BOOLEAN NOT NULL
            )
        """;

        String createSequenceQuery = "CREATE SEQUENCE IF NOT EXISTS my_schema.users_id_seq";

        try (var statement = connection.createStatement()) {
            statement.execute(createSchemaQuery);
            statement.execute(createSequenceQuery);
            statement.execute(createTableQuery);
        }
    }

    @AfterAll
    public void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
        if (postgresContainer != null) {
            postgresContainer.stop();
        }
    }

    @BeforeEach
    public void clearDatabase() throws SQLException {
        connection.createStatement().execute("DELETE FROM my_schema.users");
    }

    @Test
    @DisplayName("Должен сохранять пользователя в базе данных")
    public void shouldSaveUser() throws SQLException {
        User user = new User("Иван Иванов", "ivan@example.com", "password123");
        user.setBlocked(false);

        userRepository.save(user);

        User foundUser = userRepository.findByEmail("ivan@example.com");
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo("Иван Иванов");
        assertThat(foundUser.getEmail()).isEqualTo("ivan@example.com");
    }

    @Test
    @DisplayName("Должен находить всех пользователей в базе данных")
    public void shouldFindAllUsers() throws SQLException {
        User user1 = new User("Иван Иванов", "ivan@example.com", "password123");
        user1.setBlocked(false);
        User user2 = new User("Мария Смирнова", "maria@example.com", "password456");
        user2.setBlocked(false);

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(2);
        assertThat(users).extracting(User::getEmail).contains("ivan@example.com", "maria@example.com");
    }

    @Test
    @DisplayName("Должен обновлять данные пользователя в базе данных")
    public void shouldUpdateUser() throws SQLException {
        User user = new User("Иван Иванов", "ivan@example.com", "password123");
        user.setBlocked(false);
        userRepository.save(user);

        user.setName("Иван Петров");
        user.setPassword("Newpassword-123");
        userRepository.update(user);

        User updatedUser = userRepository.findByEmail("ivan@example.com");

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getName()).isEqualTo("Иван Петров");
        assertThat(updatedUser.getPassword()).isEqualTo("Newpassword-123");
    }



    @Test
    @DisplayName("Должен удалять пользователя по email")
    public void shouldDeleteUserByEmail() throws SQLException {
        User user = new User("Иван Иванов", "ivan@example.com", "password123");
        user.setBlocked(false);
        userRepository.save(user);

        userRepository.deleteByEmail("ivan@example.com");
        User deletedUser = userRepository.findByEmail("ivan@example.com");
        assertThat(deletedUser).isNull();
    }
}
