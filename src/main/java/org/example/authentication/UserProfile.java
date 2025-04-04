package org.example.authentication;

import java.io.FileNotFoundException;

public class UserProfile extends UserDatabaseStub {
public class UserProfile {
    public UserProfile(String username, String password, String email, String phone) {
    }
    public UserProfile() {

    }


    public String getUsername() {
        return "";
    }

    public void change_username(String old_username, String new_username, String first_password, String second_password) {

        // creating boolean variable that acts as a flag to see if the new username is available
        boolean new_username_available;

        // creating boolean variable that acts as a flag to see if the first password enters matches the password of the account
        boolean password_matches;

        // creating boolean variable that acts as a flag to see if both passwords entered are the same
        boolean passwords_are_equal = first_password.equals(second_password);

        // try catch block for exception handling
        try {
            // equates new_username_available to a verify_username call
            new_username_available = verify_username(new_username);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // try catch block for exception handling
        try {
            // equates password_matches to a verify_password call
            password_matches = verify_password(old_username, first_password);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // if statement that only occurs if the username is available and the passwords match the current account
        if (!new_username_available && password_matches && passwords_are_equal) {

            // try catch block for exception handling
            try {
                update_username(old_username, new_username);
            }
            catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
