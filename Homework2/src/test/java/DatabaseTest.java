import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseTest {
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16.3")
            .withDatabaseName("habit_db")
            .withUsername("postgres")
            .withPassword("131313");

    static {
        postgresContainer.start();
    }

    @Test
    @DisplayName("Проверка подключения к базе данных")
    public void testDatabaseConnection() {
        assertThat(postgresContainer.isRunning()).isTrue();

        try (Connection connection = DriverManager.getConnection(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword())) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            e.printStackTrace();
            assertThat(false).isTrue();
        }
    }

    @Test
    @DisplayName("Создание и вставка данных в таблицу")
    public void testCreateAndInsertData() {
        try (Connection connection = DriverManager.getConnection(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword());
             Statement statement = connection.createStatement()) {

            statement.execute("CREATE TABLE test_habit (id SERIAL PRIMARY KEY, name VARCHAR(255))");

            statement.execute("INSERT INTO test_habit (name) VALUES ('Test Habit')");

            var resultSet = statement.executeQuery("SELECT COUNT(*) FROM test_habit");
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                assertThat(count).isEqualTo(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            assertThat(false).isTrue();
        }
    }
}
