package org.example.authentication;

import javafx.stage.Stage;

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


}
