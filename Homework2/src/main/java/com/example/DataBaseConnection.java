package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {
    private static Connection connection;

    public static Connection getConnection() throws IOException, SQLException {
        if (connection == null || connection.isClosed()) {
            Properties props = new Properties();
            try (FileInputStream fis = new FileInputStream("C:\\Users\\Vage\\IdeaProjects\\Y_LAB-Habit-tracker-app\\Homework2\\db.properties")) {
                props.load(fis);
            }

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }
}
