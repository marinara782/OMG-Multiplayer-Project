package org.example.authentication;

public class User {
    String username;
    String password;
    String email;
    String phone;

    public User(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }
}
