package org.example.authentication;

import java.io.FileNotFoundException;
import java.util.List;

public class UserProfile extends UserDatabaseStub {
    // get username of the user
    public String getUsername() {
        return "";
    }

    /**
     * change username for the user
     * @param old_username old username of the user
     * @param new_username new username of the user
     * @param first_password old password of the user
     * @param second_password new password of the user
     */
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

    /**
     * changes the phone number of the user
     * @param new_phone_number new phone number of the user
     * @param password password of the user
     * @param confirmPassword confirmation password of the user by retyping it
     * @return true when the phone number has changed, false when not
     */
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

    /**
     * set phone number for user
     * @param phone_number phone number of the user
     * @param password password of the user
     * @param confirm_password confirmation password of the user by retyping it
     * @return true when a phone number has been set, false when not
     * @throws FileNotFoundException when the file is not found
     */
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

    /**
     * set email for user
     * @param username
     * @param first_password
     * @param second_password
     * @param choice 1 for email, 2 for phone number
     * @throws FileNotFoundException
     */
    public void enable_2_factor(String username, String first_password, String second_password, int choice) throws FileNotFoundException {

        String phone_number = "none";
        String email = "none";

        // creating boolean variable that acts as a flag to see if the first password enters matches the password of the account
        boolean password_matches;

        // creating boolean variable that acts as a flag to see if both passwords entered are the same
        boolean passwords_are_equal = first_password.equals(second_password);

        // accessing database
        List<User> users = registered_users_list();

        // accessing the assigned phone number and email for 2FA
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                phone_number = user.getPhone();
                email = user.getEmail();
            }
        }

        // equates password_matches to a verify_password call
        password_matches = verify_password(username, first_password);

        // equates password_matches to a verify_password call
        if (password_matches && passwords_are_equal) {

            String code_sent_phone = "none";
            String code_sent_email = "none";

            // switch statement that goes over the various options the user can use for 2FA
            switch (choice) {
                // case to handle verification via email
                case 1:

                    // calling send_email method which is a 6-digit code
                    code_sent_email = send_email(email);

                    // informing the user a code was sent
                    System.out.println("A code has been sent to " + email);
                    break;

                // case to handle verification via phone #
                case 2:

                    // calling send_text method which is a 6-digit code
                    code_sent_phone = send_text(phone_number);

                    // informing the user a code was sent
                    System.out.println("A code has been sent to " + phone_number);
                    break;
            }

            // Need GUI to have a text-field here that gets user input
            String user_input = "no number";
            if (user_input.equals(code_sent_phone)) {
                System.out.println("Email successfully verified, two-factor authentication has been enabled.");
            }
            else if(user_input.equals(code_sent_email)){
                System.out.println("Phone number successfully verified, two-factor authentication has been enabled.");
            }
            else {
                System.out.println("The code entered is incorrect, please try again.");
            }
        }
        else {
            System.out.println("Passwords do not match or an incorrect password has been entered, please try again.");
        }
    }
}


