package org.example.authentication;

import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.regex.Pattern;

public class Login extends UserDatabaseStub {

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

    public static void logout(){
        if (loggedIn){
            System.out.println("Logging out..");
            loggedIn = false;
            returnToLoginScreen();
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    private static void returnToLoginScreen(){
        System.out.println("Return to the login screen...");
        // GUI must implement a method to load the login UI
    }

    public static boolean createAccount(String new_username, String password, String email, String phone) {

            System.out.println("\nAttempting to create account for: " + new_username);

            UserDatabaseStub databaseStub = new UserDatabaseStub();
        // authenticate users
        try {
            if(databaseStub.Authenticate_user(new_username, password, email, phone)){
                System.out.println("User already exists.");
                return false;}

        } catch (FileNotFoundException e) {
            throw new RuntimeException("User database file not found.");
        }
        System.out.println("✅ Account successfully created for: " + new_username);
        return true;
    }

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

    public Login(Stage stage) {
    }

    public void show() {
    }

}

