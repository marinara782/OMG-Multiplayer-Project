package org.example.authentication;

import javafx.stage.Stage;

import java.util.HashSet;
import java.util.regex.Pattern;


public class Login {

    public Login(Stage stage) {
    }

    public void show() {
    }

    public boolean login_account(String username, String password) {

        // Username & Password String input by user, will be compared with database
        String username_entered;
        String password_entered;

        // if-else block that checks if account is verified, use the true/false to determine next action in GUI
        if (verify_account(username, password)) {
            return true;
        }
        else {
            return false;
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





    public static boolean createAccount(String newUsername, String password, String email) {
        String new_username = null;
            System.out.println("\nAttempting to create account for: " + new_username);

            // Check if username is already taken
        if (!verifyUsernameNotTaken(new_username)) {
                System.out.println("Error: Username '" + new_username + "' is already taken.");
                return false;
            }

            // Check if email is already registered
        if (!verifyEmailNotTaken(email)) {
                System.out.println("Error: Email '" + email + "' is already registered.");
                return false;
            }

            // authenticate users


            // If all checks pass, create account

            System.out.println("✅ Account successfully created for: " + newUsername);
            return true;
        }

    private static boolean verifyUsernameNotTaken(String username){
        return true;
    }
    private static boolean verifyEmailNotTaken(String email){
        return true;
    }






    }




