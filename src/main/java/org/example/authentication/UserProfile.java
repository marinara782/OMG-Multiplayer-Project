package org.example.authentication;

public class UserProfile {
    private String username;
    private String status;
    private String joinDate;

    // Constructor
    public UserProfile(String username, String status, String joinDate) {
        this.username = username;
        this.status = status;
        this.joinDate = joinDate;
    }

    public UserProfile() {

    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return status;
    }

    public String getJoinDate() {
        return joinDate;
    }
}