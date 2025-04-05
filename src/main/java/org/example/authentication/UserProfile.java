package org.example.authentication;

import java.io.FileNotFoundException;

public class UserProfile extends UserDatabaseStub {
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


    public boolean change_phone_number(String new_phone_number, String password, String confirmPassword) {
        try {
            // 1.Verify password
            if (!verify_password(getUsername(), password)) {
                System.out.println("Error: Current password is incorrect");
                return false;
            }

            // 2.Verify confirmation password
            if (!verify_password(getCurrentPassword(getUsername()), confirmPassword)) {
                System.out.println("Error: Confirmation password is incorrect");
                return false;
            }

            // 3.Verify passwords
            if (!password.equals(confirmPassword)) {
                System.out.println("Error: Passwords do not match");
                return false;
            }

            if (!verify_phone_number(getUsername(), new_phone_number)) {
                System.out.println("Error: Invalid phone number format");
                return false;
            }

            // 5. Update phone number in database
            linked_phone_number(getUsername(), new_phone_number);
            System.out.println("Success: Phone number updated for " + getUsername());
            return true;

        } catch (FileNotFoundException e) {
            System.err.println("Error: Could not access user database");
            return false;
        }





    }

    public boolean set_phone_number(String phone_number, String password, String confirm_password) {
        try {
            String username = getUsername(); // Get current logged-in username

            // 1. Verify current password
            if (!verify_password(username, password)) {
                System.out.println("Error: Current password is incorrect");
                return false;
            }

            // 2. Verify confirmation password
            if (!verify_password(username, confirm_password)) {
                System.out.println("Error: Confirmation password is incorrect");
                return false;
            }

            // 3. Verify passwords match
            if (!password.equals(confirm_password)) {
                System.out.println("Error: Passwords do not match");
                return false;
            }
            // 4. Verify phone number
            if(!verify_phone_number(username, phone_number)){
                System.out.println("Error: Account not found!");
                return false;
            }


            // 5. Update phone number in database
            linked_phone_number(username, phone_number);
            System.out.println("Success: Phone number updated for " + username);
            return true;

        } catch (FileNotFoundException e) {
            System.err.println("Error: Could not access user database");
            return false;
        }
    }




    }

