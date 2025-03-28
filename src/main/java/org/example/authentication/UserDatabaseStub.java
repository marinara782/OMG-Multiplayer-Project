package org.example.authentication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class UserDatabaseStub {


    //return a list of registered users
    public List<User> registered_users_list() throws FileNotFoundException {
        List<User> users = new ArrayList<>();
        File file = new File("temp.txt");

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",\\s*");
                if (parts.length == 4) {
                    users.add(new User(parts[0], parts[1], parts[2], parts[3]));
                }
            }
            return users;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean Authenticate_user(String username, String password, String email, String phone) throws FileNotFoundException {
        List<User> users = registered_users_list();
        boolean userExists = false;
        for (User user : users) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password) && user.getEmail().equals(email) && user.getPhone().equals(phone)) {
                userExists = true;
                break;
            }
        }
        return userExists;
    }

    public boolean verify_account(String username, String password) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }


    public boolean verify_username(String username) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public boolean verify_password(String username, String password) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public boolean verify_email(String username, String email) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if(user.getUsername().equals(username) && user.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    public boolean verify_phone_number(String username, String phone) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public boolean passwords_match(){
        return false;
    }

    public void update_username(String oldUsername, String newUsername) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if(user.getUsername().equals(oldUsername)){
                user.setUsername(newUsername);
            }
        }

    }

    public void update_password(String username, String oldPassword, String newPassword) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if(user.getUsername().equals(username) && user.getPassword().equals(oldPassword)){
                user.setPassword(newPassword);
            }
        }
    }

    public void linked_phone_number(){

    }
    

}
