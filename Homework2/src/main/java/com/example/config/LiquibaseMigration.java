package com.example.config;

import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.database.jvm.JdbcConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class LiquibaseMigration {

    public static void runMigration() {
        String changeLogFile = AppProperties.getPropertyLiquibase();

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
    }
}
