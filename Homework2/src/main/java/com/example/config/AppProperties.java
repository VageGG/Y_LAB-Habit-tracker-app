package com.example.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppProperties {
    private static final String DB_PROPERTIES_PATH = "C:\\Users\\Vage\\IdeaProjects\\Y_LAB-Habit-tracker-app\\Homework2\\db.properties";
    private static Properties properties = new Properties();

    static {
        try (FileInputStream inputStream = new FileInputStream(DB_PROPERTIES_PATH)) {
            properties.load(inputStream);
        } catch (IOException e) {
            System.err.println("Ошибка загрузки файла свойств: " + e.getMessage());
        }
    }

    public static String getDatabaseUrl() {
        return properties.getProperty("db.url");
    }

    public static String getDatabaseUsername() {
        return properties.getProperty("db.user");
    }

    public static String getDatabasePassword() {
        return properties.getProperty("db.password");
    }

    public static String getPropertyLiquibase() {
        return properties.getProperty("liquibase.changeLogFile");
    }
}

