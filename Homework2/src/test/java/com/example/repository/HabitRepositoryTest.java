package com.example.repository;

import com.example.config.DataBaseConnection;
import com.example.enums.Frequency;
import com.example.model.Habit;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты для HabitRepository")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HabitRepositoryTest {

    private PostgreSQLContainer<?> postgresContainer;
    private Connection connection;
    private HabitRepository habitRepository;

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

        habitRepository = new HabitRepository();
    }

    private void initializeDatabase() throws SQLException {
        String createSchemaQuery = "CREATE SCHEMA IF NOT EXISTS my_schema";
        String createTableQuery = """
        CREATE TABLE IF NOT EXISTS my_schema.habit (
            id SERIAL PRIMARY KEY,
            name VARCHAR(255) NOT NULL,
            description TEXT,
            frequency VARCHAR(50) NOT NULL,
            creation_date DATE NOT NULL,
            user_id INTEGER NOT NULL
        )
        """;

        try (var statement = connection.createStatement()) {
            statement.execute(createSchemaQuery);
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
        connection.createStatement().execute("DELETE FROM my_schema.habit");
    }

    @Test
    @DisplayName("Должен сохранять привычку в базе данных")
    public void shouldSaveHabit() {
        Habit habit = new Habit();
        habit.setName("Чтение книги");
        habit.setDescription("Читать одну главу в день");
        habit.setFrequency(Frequency.DAILY);
        habit.setCreationDate(LocalDate.now());
        habit.setUserId(1);

        habitRepository.save(habit);

        List<Habit> habits = habitRepository.findAll();
        assertThat(habits).hasSize(1);
        assertThat(habits.get(0).getName()).isEqualTo("Чтение книги");
    }

    @Test
    @DisplayName("Должен находить все привычки в базе данных")
    public void shouldFindAllHabits() {
        Habit habit1 = new Habit();
        habit1.setName("Чтение книги");
        habit1.setDescription("Читать одну главу в день");
        habit1.setFrequency(Frequency.DAILY);
        habit1.setCreationDate(LocalDate.now());
        habit1.setUserId(1);

        Habit habit2 = new Habit();
        habit2.setName("Упражнения");
        habit2.setDescription("Заниматься спортом каждый день");
        habit2.setFrequency(Frequency.DAILY);
        habit2.setCreationDate(LocalDate.now());
        habit2.setUserId(1);

        habitRepository.save(habit1);
        habitRepository.save(habit2);

        List<Habit> habits = habitRepository.findAll();
        assertThat(habits).hasSize(2);
        assertThat(habits).extracting(Habit::getName).contains("Чтение книги", "Упражнения");
    }

    @Test
    @DisplayName("Должен удалять привычку из базы данных")
    public void shouldDeleteHabit() {
        Habit habit = new Habit();
        habit.setName("Чтение книги");
        habit.setDescription("Читать одну главу в день");
        habit.setFrequency(Frequency.DAILY);
        habit.setCreationDate(LocalDate.now());
        habit.setUserId(1);

        habitRepository.save(habit);
        List<Habit> habitsBeforeDelete = habitRepository.findAll();
        assertThat(habitsBeforeDelete).hasSize(1);

        habitRepository.delete(habit.getId());
        List<Habit> habitsAfterDelete = habitRepository.findAll();
        assertThat(habitsAfterDelete).isEmpty();
    }
}
