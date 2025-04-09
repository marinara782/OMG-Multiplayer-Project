package org.example.authentication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class UserDatabaseStub {


    /**
     *  reads user data from a file and returns a list of user objects to simulate a database
     * @return List<User> - list of users
     * @throws FileNotFoundException when the file is not found
     */
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

    /**
     * check if user exists in the database by checking the parameters
     * @param username username of the user
     * @param password password of the user
     * @param email email of the user
     * @param phone phone number of the user
     * @return true if the user exists, false if the user does not exist
     * @throws FileNotFoundException when the file is not found
     */
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

    /**
     * checks username and the password of the user to see if it is in the database
     * @param username username of the user
     * @param password password of the user
     * @return true if the user exists, false if the user does not exist
     * @throws FileNotFoundException when the file is not found
     */
    public boolean verify_account(String username, String password) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) { // checks if the user exists in the database by matching the username and password parameters
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    /**
     * check is username is already used by another user in the database
     * @param username username of the user
     * @return true if the username is already used by someone else, false if the username is still unused and available
     * @throws FileNotFoundException when the file is not found
     */
    public boolean verify_username(String username) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) { // checks if the username exists in the database
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    /**
     * verifies the password of the user in the database
     * @param username username of the user
     * @param password password of the user
     * @return true when password is correct, false when password is incorrect
     * @throws FileNotFoundException when the file is not found
     */
    public boolean verify_password(String username, String password) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    /**
     * verify the email of the given user with the username
     * @param username username of the user
     * @param email email of the user
     * @return true if the email matches the username, false when the email doesn't match
     * @throws FileNotFoundException
     */
    public boolean verify_email(String username, String email) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if(user.getUsername().equals(username) && user.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    /**
     * verifies the phone number of the user with the username
     * @param username username of the user
     * @param phone phone number of the user
     * @return true if the phone number matches the given username, false when phone number and username don't match
     * @throws FileNotFoundException when the file is not found
     */
    public boolean verify_phone_number(String username, String phone) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if(user.getUsername().equals(username) && user.getPhone().equals(phone)){
                return true;
            }
        }
        return false;
    }

    /**
     * adds a new user to the database by writing the new user into the file
     * @param users user object to be added to the database
     */
    public void write_users_to_file(List<User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("temp.txt"))) {
            for (User user : users) {
                writer.println(user.getUsername() + ", " + user.getPassword() + ", " + user.getEmail() + ", " + user.getPhone());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * changes the username of the user
     * @param oldUsername old username of the user
     * @param newUsername new username of the user
     * @throws FileNotFoundException when the file is not found
     */
    public void update_username(String oldUsername, String newUsername) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if (user.getUsername().equals(oldUsername)) {
                user.setUsername(newUsername);
            }
        }
        write_users_to_file(users);
    }

    /**
     * change the password of the user
     * @param username username of the user
     * @param oldPassword old password of the user
     * @param newPassword new password of the user
     * @throws FileNotFoundException when the file is not found
     */
    public void update_password(String username, String oldPassword, String newPassword) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
            }
        }
        write_users_to_file(users);
    }

    /**
     * updates the phone number of the user or links when user has no phone number
     * @param username username of the user
     * @param newPhone new phone number of the user replacing the old phone number
     * @throws FileNotFoundException when the file is not found
     */
    public void linked_phone_number(String username, String newPhone) throws FileNotFoundException {
        List<User> users = registered_users_list();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.setPhone(newPhone);
            }
        }
        write_users_to_file(users);
    }
    
    /**
     * getting the current password of the user
     * @param username 
     * @return String password of the user
     * @throws FileNotFoundException when the file is not found
     */
    public String getCurrentPassword(String username) throws FileNotFoundException{
        List<User> users = registered_users_list();
        for (User user : users) {
            if (user.getUsername().equals(username)){
                return user.getPassword();
            }
        }
        return null;
    }

    /**
     * udpates the email of the user
     * @param username
     * @param newEmail
     * @param password
     * @throws FileNotFoundException
     */
    public void update_email(String username, String newEmail, String password) throws  FileNotFoundException{
        List<User> users = registered_users_list();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                user.setEmail(newEmail);
            }
        }
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
        return "Email not found";
    }

    /**
     * sends a verification code to the user's phone number
     * @param phone_number phone number of the user
     * @return String - random 6-digit verification code
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
        return "Phone number not found";
    }
}
