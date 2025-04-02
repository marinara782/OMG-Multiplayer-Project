package org.example.authentication;

import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.regex.Pattern;

public class Login extends UserDatabaseStub {

    public boolean forgot_password(String username, String email, String newPassword) throws FileNotFoundException{
        //verify that the username exists
        if(!verify_username(username)){
            System.out.println("useername does not exist");
            return false;
        }
        //verify that the email matchs the username
        if(!verify_email(username, email)){
            System.out.println(("Email does not match username"));
            return false;
        }
        //get current password to change
        String currentPassword = getCurrentPassword(username);

        //update password
        update_password(username, currentPassword, newPassword);
        System.out.println("password changed sucessfully");
        return true;
    }
}

    public boolean login_account(String username, String password) {

        // Username & Password String input by user, will be compared with database
        String username_entered;
        String password_entered;

        // if-else block that checks if account is verified, use the true/false to determine next action in GUI
        try {
            if (verify_account(username, password)) {
                return true;
            }
            else {
                return false;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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

    }

