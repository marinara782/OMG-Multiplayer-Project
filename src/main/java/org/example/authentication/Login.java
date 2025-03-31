package org.example.authentication;

import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Login extends UserDatabaseStub{

    public Login(Stage stage) {
    }

    public void show() {
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
    }
}
