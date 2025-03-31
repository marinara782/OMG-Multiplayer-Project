package org.example.authentication;

import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Login extends UserDatabaseStub{
    public Login(Stage stage) {
    }

    public void show() {
    }
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

        update_password(username, currentPassword, newPassword);
        System.out.println("password changed sucessfully");
        return true;
    }

}
