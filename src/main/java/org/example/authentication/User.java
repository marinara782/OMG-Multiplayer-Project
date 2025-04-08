package org.example.authentication;

public class User {
    String username;
    String password;
    String email;
    String phone;
    StatusOptions current_status;

    public User(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.current_status = StatusOptions.OFFLINE;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public StatusOptions getCurrentStatus() {
        return current_status;
    }
    public void setCurrentStatus(StatusOptions current_status) {
        this.current_status = current_status;
    }
}
