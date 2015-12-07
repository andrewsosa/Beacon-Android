package com.andrewsosa.beacon;

/**
 * Created by andrewsosa on 10/21/15.
 */
public class User {

    private String email;
    private String username;


    public User() {}

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

}