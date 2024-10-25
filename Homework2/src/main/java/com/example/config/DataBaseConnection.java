package com.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {

            String url = AppProperties.getDatabaseUrl();
            String user = AppProperties.getDatabaseUsername();
            String password = AppProperties.getDatabasePassword();

            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }
}
