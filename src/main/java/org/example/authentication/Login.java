package org.example.authentication;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.Stage;


public class Login extends UserDatabaseStub {

    /**
     * help users to recover their username if they forget it. Sends a 6-digit code to the email and checks if the code is correct.
     * @param email email of th user
     * @return the username of the user if the code is correct, otherwise return a message
     * @throws FileNotFoundException when the file is not found
     */
    public String forgot_username(String email) throws FileNotFoundException {

        // accessing database
        List<User> users = registered_users_list();

        String code_sent = "none";
        String username = "";

        boolean email_sent_flag = false;

        // for loop that checks if the email entered is the same and sends a 6-digit code to the email
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)){
                code_sent = send_email(email);
                username = user.getUsername();
                email_sent_flag = true;
            }
        }

        // Method call stub to simulate a user receiving an email and entering it into the program
        String user_input = get_2FA_input(email_sent_flag, code_sent);

        if (user_input.equals(code_sent)) {
            return username;
        }
        else {
            return "There was an error during the retrieval of your username, try again.";
        }
    }

    /**
     * * This method is used to verify the username and password of a user then checks if the account is verified by checking the password
     * @param username the username of the user
     * @param email the email of the user
     * @param newPassword the new password of the user
     * @return true if the password is changed successfully, false when not
     * @throws FileNotFoundException if the file is not found
     */
    public boolean forgot_password(String username, String email, String newPassword) throws FileNotFoundException{
        //verify that the username exists
        if(!verify_username(username)){
            System.out.println("username does not exist");
            return false;
        }
        //verify that the email matches the username
        if(!verify_email(username, email)){
            System.out.println(("Email does not match username"));
            return false;
        }
        //get current password to change
        String currentPassword = getCurrentPassword(username);

        //update password
        update_password(username, currentPassword, newPassword);
        System.out.println("password changed successfully");
        return true;
    }

    /**
     * * This method is used to verify the username and password of a user then checks if the account is verified by checking the password
     * @param username username of the user
     * @param password password of the user
     * @return true if verified, false if not
     */
    public boolean login_account(String username, String password) {

        try {
            // if-else block that checks if account is verified, use the true/false to determine next action in GUI
            if (verify_account(username, password)) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean loggedIn = true;

    /**
     * logout method
     */
    public static void logout(){
        if (loggedIn){
            System.out.println("Logging out..");
            loggedIn = false;
            returnToLoginScreen();
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    // return to log in screen after logging out
    public static void returnToLoginScreen(){
        System.out.println("Return to the login screen...");
        // GUI must implement a method to load the login UI
    }

    /**
     * * This method is used to create a new account for the user. It checks if the username already exists in the database and if not, it creates a new account.
     * @param new_username the  new username of the user
     * @param password password of the user
     * @param email email of the user
     * @param phone phone number of the user
     * @return true when a new account is created, false when not
     * @throws FileNotFoundException when the file is not found
     */
    public static  boolean createAccount(String new_username, String password, String email, String phone) throws FileNotFoundException {

        System.out.println("\nAttempting to create account for: " + new_username);

        UserDatabaseStub databaseStub = new UserDatabaseStub();

        List<User> users = new ArrayList<>(); // Initialize empty list as fallback

        // authenticate users

        try {
            users = databaseStub.registered_users_list();
        } catch (FileNotFoundException e) {
            System.out.println("Creating new user database file...");
            // Continue with empty list if file doesn't exist
            users = new ArrayList<>();
        }

        if(databaseStub.verify_username(new_username)){
            System.out.println("User already exists. ");
            return false;
        }

        User new_user = new User(new_username, password, email, phone);
        users.add(new_user);
        databaseStub.write_users_to_file(users);

        System.out.println("✅ Account successfully created for: " + new_username);

        return true;
    }

    /**
     * checks if password is correct and matches the given username
     * @param username username of the user
     * @param oldPassword old password of the user
     * @param newPassword new password of the user
     * @param confirmPassword confirmation password of the user, retyped new password
     * @return true if new password implemented, false if not
     * @throws FileNotFoundException when the file is not found
     */
    public boolean change_password(String username, String oldPassword, String newPassword, String confirmPassword) throws FileNotFoundException{
        //verify old password
        if(!verify_password(username, oldPassword)){
            System.out.println("Password is incorrect");
            return false;
        }

        //newPassword has length >=8
        boolean isLongEnough = newPassword.length() >= 8;
        //new password has at least one letter
        boolean hasLetter = newPassword.matches(".*[a-zA-Z].*");
        //new password has at least 1 digit
        boolean hasNumber= newPassword.matches(".*\\d.*");
        if(!(isLongEnough && hasLetter && hasNumber)){
            System.out.println("newPassword does not match requirements: it must be at east 8 characters long and contain at least 1 letter and 1 number ");
            return false;
        }

        //verify that new password matches confirmation input
        if(!newPassword.equals(confirmPassword)){
            System.out.println("New password and confirmation do not match");
            return false;
        }
        //change password
        update_password(username, oldPassword, newPassword);
        System.out.println("Password changed successfully");
        return true;
    }

    /**
     * verifies that the password is correct and matches with the given username, then change the email
     * @param username username of the user
     * @param password password of the user
     * @param oldEmail the old password of the user
     * @param newEmail the new password of the user
     * @return true when email is changed, false when not
     * @throws FileNotFoundException when the file is not found
     */
    public boolean change_email(String username, String password, String oldEmail, String newEmail) throws FileNotFoundException{
        //verify that the password is correct and matches username
        if (!verify_password(username, password)){
            System.out.println("Incorrect password. Email update failed");
            return false;
        }
        //change email
        update_email(username, newEmail, password);
        return true;
    }

    public void show() {
    }
}

