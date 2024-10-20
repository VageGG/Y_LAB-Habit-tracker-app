package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Represents a user with an email, password, and name.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private boolean blocked;

    /**
     * Constructs a User with the specified name, email, and password.
     *
     * @param name     the name of the user
     * @param email    the email of the user
     * @param password the password of the user (should be hashed)
     */
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        setPassword(password);
        this.blocked = false;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
