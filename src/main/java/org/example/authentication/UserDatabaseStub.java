package org.example.authentication;

import java.io.*;
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
            if(user.getUsername().equals(username) && user.getPhone().equals(phone)){
                return true;
            }
        }
        return false;
    }

    public void write_users_to_file(List<User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("temp.txt"))) {
            for (User user : users) {
                writer.println(user.getUsername() + ", " + user.getPassword() + ", " + user.getEmail() + ", " + user.getPhone());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update_username(String oldUsername, String newUsername) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if (user.getUsername().equals(oldUsername)) {
                user.setUsername(newUsername);
            }
        }
        write_users_to_file(users);
    }

    public void update_password(String username, String oldPassword, String newPassword) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
            }
        }
        write_users_to_file(users);
    }

    public void linked_phone_number(String username, String newPhone) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.setPhone(newPhone);
            }
        }
        write_users_to_file(users);
    }
    public String getCurrentPassword(String username) throws FileNotFoundException{
        List<User> users = registered_users_list();
        for (User user : users) {
            if (user.getUsername().equals(username)){
                return user.getPassword();
            }
        }
        return null;
    }
    public void update_email(String username, String newEmail, String password) throws  FileNotFoundException{
        List<User> users = registered_users_list();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                user.setEmail(newEmail);
            }
        }
    }

    public String send_email(String email) throws FileNotFoundException {

        // accessing database
        List<User> users = registered_users_list();

        // for loop - checks database email with email entered, gives random 6-digit code if email matches
        for (User user : users) {
            if (user.getEmail().equals(email)) {

                Random random = new Random();

                int random_number = random.nextInt(1000000);

                return String.format("%06d", random_number);
            }
        }
        return "Email not found";
    }

    public String send_text(String phone_number) throws FileNotFoundException {

        // accessing database
        List<User> users = registered_users_list();

        // for loop - checks database phone # with phone # entered, gives random 6-digit code if phone # matches
        for (User user : users) {
            if (user.getPhone().equals(phone_number)) {

                Random random = new Random();

                int random_number = random.nextInt(1000000);

                return String.format("%06d", random_number);
            }
        }
        return "Phone number not found";
    }

    public boolean is_valid_phone_number_format(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        // Matches xxx-xxx-xxxx where x is digit
        return phoneNumber.matches("^\\d{3}-\\d{3}-\\d{4}$");

    }
}
