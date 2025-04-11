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
     *
     * @param old_username    old username of the user
     * @param new_username    new username of the user
     * @param first_password  current password of the user
     * @param second_password a confirmation password that will be entered to verify the password
     * @return the new username as a string when successful, error message when unsuccessful
     */
    public String change_username(String old_username, String new_username, String first_password, String second_password) throws FileNotFoundException {

        // creating boolean variable that acts as a flag to see if the new username is available
        boolean new_username_available;

        // creating boolean variable that acts as a flag to see if the first password enters matches the password of the account
        boolean password_matches;

        // creating boolean variable that acts as a flag to see if both passwords entered are the same
        boolean passwords_are_equal = first_password.equals(second_password);

        // equates new_username_available to a verify_username call
        new_username_available = verify_username(new_username);

        // equates password_matches to a verify_password call
        password_matches = verify_password(old_username, first_password);


        // if statement that only occurs if the username is available and the passwords match the current account
        if (!new_username_available && password_matches && passwords_are_equal) {
            update_username(old_username, new_username);
            return new_username;
        }
        return "there was an error trying to change your username, please try again.";
    }

    /**
     * changes the phone number of the user
     *
     * @param new_phone_number new phone number of the user
     * @param password         password of the user
     * @param confirmPassword  confirmation password of the user by retyping it
     * @return true when the phone number has changed, false when not
     */
    public boolean change_phone_number(String username,String new_phone_number, String password, String confirmPassword) {
        try {
            // 1.Verify password
            if (!verify_password(username, password)) {
                System.out.println("Error: Current password is incorrect");
                return false;
            }

            // 2.Verify passwords
            if (!password.equals(confirmPassword)) {
                System.out.println("Error: Passwords do not match");
                return false;
            }

            if (!is_valid_phone_number_format(new_phone_number)) {
                System.out.println("Error: Invalid phone number format");
                return false;
            }

            // 5. Update phone number in database
            linked_phone_number(username, new_phone_number);
            System.out.println("Success: Phone number updated for " + username);
            return true;

        } catch (FileNotFoundException e) {
            System.err.println("Error: Could not access user database");
            return false;
        }
    }

    /**
     * set phone number for user
     *
     * @param phone_number     phone number of the user
     * @param password         password of the user
     * @param confirm_password confirmation password of the user by retyping it
     * @return true when a phone number has been set, false when not
     * @throws FileNotFoundException when the file is not found
     */
    public boolean set_phone_number(String username, String phone_number, String password, String confirm_password) {
        try {


            // 1. Verify current password
            if (!verify_password(username, password)) {
                System.out.println("Error: Current password is incorrect");
                return false;
            }



            // 2. Verify passwords match
            if (!password.equals(confirm_password)) {
                System.out.println("Error: Passwords do not match");
                return false;
            }
            // 3. Verify phone number
            if(!is_valid_phone_number_format(phone_number)){
                System.out.println("phone number format is incorrect!");
                return false;
            }


            // 4. Update phone number in database
            linked_phone_number(username, phone_number);
            System.out.println("Success: Phone number updated for " + username);
            return true;

        } catch (FileNotFoundException e) {
            System.err.println("Error: Could not access user database");
            return false;
        }
    }

    /**
     * enables two-factor authentication for the user depending on what option they use to enable it
     *
     * @param username the registered username of a user
     * @param first_password current password of the user
     * @param second_password a 2nd verification password to confirm their current password
     * @param choice          decides what verification method is chosen, 1 for email, 2 for phone number
     * @return a string that the 2FA has been enabled for their chosen method, feedback messages as errors when unsuccessful
     * @throws FileNotFoundException when file for user data is not found
     */

    public String enable_2_factor(String username, String first_password, String second_password, int choice) throws FileNotFoundException {

        // initializing two string variables that will be used to retrieve the phone number or email of the user
        String phone_number = "";
        String email = "";

        // creating boolean variable that acts as a flag to see if the first password enters matches the password of the account
        boolean password_matches;

        // creating boolean variable that acts as a flag to see if both passwords entered are the same
        boolean passwords_are_equal = first_password.equals(second_password);

        // two boolean flags are initialized to emulate that an email/text with a code has been sent to the user
        boolean code_sent_to_phone = false;
        boolean code_sent_to_email = false;

        // accessing database
        List<User> users = registered_users_list();

        // accessing the assigned phone number and email for 2FA, assigns flags as true to emulate that a text/email has been sent
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                phone_number = user.getPhone();
                email = user.getEmail();
                code_sent_to_email = true;
                code_sent_to_phone = true;
            }
        }

        // equates password_matches to a verify_password call
        password_matches = verify_password(username, first_password);

        // if statement that
        if (password_matches && passwords_are_equal) {

            // initializing two string variables that become the 6 code sent via email/text
            String code_sent_phone = "";
            String code_sent_email = "";

            // switch statement that goes over the various options the user can use for 2FA
            switch (choice) {
                // case to handle verification via email
                case 1:

                    // equating the code_sent_email to a 6 digit via a send_email call
                    code_sent_email = send_email(email);
                    break;

                // case to handle verification via phone #
                case 2:

                    // equating the code_sent_phone to a 6 digit via a send_text call
                    code_sent_phone = send_text(phone_number);
                    break;
            }

            // initializing a string user input that simulates the user_input when they receive a code depending on the verification choice
            String user_input = "";

            // if statement that equates user_input to whatever verification choice was chosen
            if (choice == 1) {
               user_input = get_2FA_input(code_sent_to_email, code_sent_email);
            }
            else if (choice == 2) {
               user_input = get_2FA_input(code_sent_to_phone, code_sent_phone);
            }

            // if statement that gives return statements on whether the verification method was successful or not, gives feedback messages
            if (user_input.equals(code_sent_phone)) {
                return "Phone Number successfully verified, two-factor authentication has been enabled.";
            } else if (user_input.equals(code_sent_email)) {
                return "Email successfully verified, two-factor authentication has been enabled.";
            } else {
                return "The code entered is incorrect, please try again.";
            }
        } else {
            return "Passwords do not match or an incorrect password has been entered, please try again.";
        }
    }

    /**
     * changes the current status of a user to a new status chosen by the user
     *
     * @param username the current username of a user
     * @param new_status the new status that will be associated with the user (offline, online etc.)
     * @return the new status within a feedback message if successful, error message if unsuccessful
     * @throws FileNotFoundException if database file is not found
     */
    public String change_current_status(String username, StatusOptions new_status) throws FileNotFoundException {
        // accessing database
        List<User> users = registered_users_list();

        // for loop - iterates through database and checks if username matches, updates status if new status is chosen
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getCurrentStatus() != new_status) {
                user.setCurrentStatus(new_status);
                // return statement that gives new status
                return "Your status has been changed to " + new_status.toString();
            } else if (user.getUsername().equals(username) && user.getCurrentStatus() == new_status) {
                // return statement that informs user that is already their status
                return "Your current status is already set to " + new_status.toString();
            }
        }
        // return statement giving an error message
        return "Error trying to change status";
    }
}
