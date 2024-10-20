package com.example;

import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.database.jvm.JdbcConnection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class LiquibaseMigration {
    private static final String DB_PROPERTIES_PATH = "C:\\Users\\Vage\\IdeaProjects\\Y_LAB-Habit-tracker-app\\Homework2\\db.properties";

    public static void runMigration() {
        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream(DB_PROPERTIES_PATH)) {
            props.load(fis);
            String changeLogFile = props.getProperty("liquibase.changeLogFile");

            try (Connection connection = DataBaseConnection.getConnection()) {
                Liquibase liquibase = new Liquibase(changeLogFile,
                        new ClassLoaderResourceAccessor(),
                        new JdbcConnection(connection));
                liquibase.update((String) null);
                System.out.println("Database migration completed!");

            } catch (SQLException | LiquibaseException e) {
                System.out.println("Database migration failed.");
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("Failed to load database properties.");
            e.printStackTrace();
        }
    }
}
