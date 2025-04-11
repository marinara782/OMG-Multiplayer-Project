package org.example.authentication;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class UserDatabaseStub {


    // return a list of registered users from temp.txt, returns List<User>
    public List<User> registered_users_list() throws FileNotFoundException {
        List<User> users = new ArrayList<>();
        File file = new File("temp.txt");

        // Check if the file exists, if not create it
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

    // checks if given parameters match with a user in database, returns bool
    public boolean Authenticate_user(String username, String password, String email, String phone) throws FileNotFoundException {
        List<User> users = registered_users_list();
        boolean userExists = false;
        for (User user : users) { // checks if the user exists in the database by matching the parameters
            if(user.getUsername().equals(username) && user.getPassword().equals(password) && user.getEmail().equals(email) && user.getPhone().equals(phone)) {
                userExists = true;
                break;
            }
        }
        return userExists;
    }

    // checks if username and password exist in database and returns bool
    public boolean verify_account(String username, String password) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) { // checks if the user exists in the database by matching the username and password parameters
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    // checks if user is in database, returns true if exist
    public boolean verify_username(String username) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) { // checks if the username exists in the database
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    // checks if password matches password in database, returns true if so
    public boolean verify_password(String username, String password) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    // checks if given email matches email in database, returns true if so
    public boolean verify_email(String username, String email) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if(user.getUsername().equals(username) && user.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    // checks if given phone matches phone in database, returns true if so
    public boolean verify_phone_number(String username, String phone) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if(user.getUsername().equals(username) && user.getPhone().equals(phone)){
                return true;
            }
        }
        return false;
    }

    // writes given user list to database, overwrites anything already in, returns void
    public void write_users_to_file(List<User> users) {
        File file = new File("temp.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (User user : users) {
                writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getEmail() + "," + user.getPhone());
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // updates username in data base, returns void
    public void update_username(String oldUsername, String newUsername) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if (user.getUsername().equals(oldUsername)) {
                user.setUsername(newUsername);
            }
        }
        write_users_to_file(users);
    }

    // updates password in database, returns void
    public void update_password(String username, String oldPassword, String newPassword) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
            }
        }
        write_users_to_file(users);
    }

    // updates phone number in database, returns void
    public void linked_phone_number(String username, String newPhone) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.setPhone(newPhone);
            }
        }
        write_users_to_file(users);
    }

    // returns username if username is in database
    public String getUsername(String username) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user.getUsername();
            }
        }
        return null;
    }

    // returns password of username
    public String getCurrentPassword(String username) throws FileNotFoundException{
        List<User> users = registered_users_list();
        for (User user : users) {
            if (user.getUsername().equals(username)){
                return user.getPassword();
            }
        }
        return null;
    }

    // returns email of username
    public String getCurrentEmail(String username) throws FileNotFoundException{
        List<User> users = registered_users_list();
        for (User user : users) {
            if (user.getUsername().equals(username)){
                return user.getEmail();
            }
        }
        return null;
    }

    // returns phone number of username
    public String getCurrentPhone(String username) throws FileNotFoundException{
        List<User> users = registered_users_list();
        for (User user : users) {
            if (user.getUsername().equals(username)){
                return user.getPhone();
            }
        }
        return null;
    }

    // updates email of a user if they request to change it and writes the new email into the file
    public void update_email(String username, String newEmail, String password) throws  FileNotFoundException{
        List<User> users = registered_users_list();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                user.setEmail(newEmail);
            }
        }
        write_users_to_file(users);
    }

    /**
     * sends a verification code to the user's email address
     * @param email
     * @return String - random 6-digit verification code
     * @throws FileNotFoundException when the file is not found
     */
    public String send_email(String email) throws FileNotFoundException {

        // accessing database
        List<User> users = registered_users_list();

        // for loop - checks database email with email entered, gives random 6-digit code if email matches
        for (User user : users) {
            if (user.getEmail().equals(email)) {

                Random random = new Random(); // random object

                int random_number = random.nextInt(1000000); // generates a random number between 0 and 999999

                return String.format("%06d", random_number);
            }
        }
        // return message if a code could not be sent
        return "Email not found";
    }

    /**
     * sends a verification code to the user's phone number
     * @param phone_number phone number of the user
     * @return String - random 6-digit verification code via text
     * @throws FileNotFoundException
     */
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
        // return message if a code could not be sent
        return "Phone number not found";
    }

    // helper function for verification purposes, sends code to emulate a text/email being sent successfully
    public String get_2FA_input(boolean flag, String code) throws FileNotFoundException {
        // checks if flag eas true, returns a code otherwise gives an error message
        if (flag) {
            return code;
        }
        else {
            return "code not received";
        }
    }

    // helper function for the format of a phone number, returns the phone number in required format
    public boolean is_valid_phone_number_format(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        // Matches xxx-xxx-xxxx where x is digit
        return phoneNumber.matches("^\\d{3}-\\d{3}-\\d{4}$");
    }
}
