package com.example.config;

public class SQLQueries {
    public static final String SAVE_HABIT = "INSERT INTO my_schema.habit (name, description, frequency, creation_date, user_id) VALUES (?, ?, ?, ?, ?)";
    public static final String FIND_ALL_HABITS = "SELECT * FROM my_schema.habit";
    public static final String DELETE_HABIT = "DELETE FROM my_schema.habit WHERE id = ?";

    public static final String SAVE_USER = "INSERT INTO my_schema.users (id, name, email, password, blocked) VALUES (nextval('my_schema.users_id_seq'), ?, ?, ?, ?)";
    public static final String FIND_USER_BY_EMAIL = "SELECT * FROM my_schema.users WHERE email = ?";
    public static final String FIND_ALL_USERS = "SELECT * FROM my_schema.users";
    public static final String UPDATE_USER = "UPDATE my_schema.users SET name = ?, password = ?, blocked = ? WHERE email = ?";
    public static final String DELETE_USER_BY_EMAIL = "DELETE FROM my_schema.users WHERE email = ?";
}

