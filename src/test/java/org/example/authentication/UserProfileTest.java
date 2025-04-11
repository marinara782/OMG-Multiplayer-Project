package org.example.authentication;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UserProfileTest {

    private final String testFilePath = "temp.txt";
    private UserProfile userProfile;

    private Login login;

    @BeforeEach
    void setup() throws IOException {
        List<String> lines = List.of("alice, password124, alice@gmail.com, 111-222-3333", "benny, test_password, benny@gmail.com, 222-111-3333");
        Files.write(Paths.get(testFilePath), lines);
        login = new Login();
        assertTrue(login.login_account("alice", "password124"));
        userProfile = new UserProfile();
    }

    @AfterEach
    void tearDown() throws IOException{
        Files.deleteIfExists(Paths.get(testFilePath));
    }
    @Test
    public void testSetPhoneNumber_Success() throws IOException {


        assertTrue(userProfile.set_phone_number("alice","123-456-7890", "password124", "password124"));


    }

    @Test

    public void testSetPhoneNumber_IncorrectCurrentPassword() throws IOException{
        assertFalse(userProfile.set_phone_number("alice","123-456-7890", "password123", "password124"));

    }

    @Test

    public void testSetPhoneNumber_PasswordsDoNotMatch() throws IOException{
        assertFalse(userProfile.set_phone_number("alice","123-4567-890", "password124", "password123"));

    }

    @Test

    public void  testSetPhoneNumber_InvalidPhoneNumber() throws IOException {
        assertFalse(userProfile.set_phone_number("alice","1234", "password124", "password124"));
        assertFalse(userProfile.set_phone_number("alice","1234567890", "password124", "password124"));

    }

    @Test

    public void testSetPhoneNumber_FileNotFoundException() throws IOException {
        // Test when database file is missing
        Files.delete(Paths.get(testFilePath));
        assertThrows(RuntimeException.class, () -> {
        assertFalse(userProfile.set_phone_number("alice","123-456-7890", "password124", "password124"));


    });
    }
    @Test
    public void testChangePhoneNumber_Success() {
        // Test successful phone number change
        assertTrue(userProfile.change_phone_number("alice","123-456-7890", "password124", "password124"));
    }

    @Test
    public void testChangePhoneNumber_IncorrectCurrentPassword() {
        // Test with wrong current password
        assertFalse(userProfile.change_phone_number("alice","123-456-7890", "wrongpassword", "wrongpassword"));
    }

    @Test
    public void testChangePhoneNumber_PasswordsDoNotMatch() {
        // Test when password and confirmation don't match
        assertFalse(userProfile.change_phone_number("alice","123-456-7890", "password124", "differentpassword"));
    }

    @Test
    public void testChangePhoneNumber_InvalidPhoneNumber() {
        // Test with invalid phone number format
        assertFalse(userProfile.change_phone_number("alice","123", "password124", "password124")); // Too short
        assertFalse(userProfile.change_phone_number("alice","invalid", "password124", "password124")); // Non-numeric
    }

    @Test
    public void testChangePhoneNumber_FileNotFoundException() throws IOException {
        // Test when database file is missing
        Files.delete(Paths.get(testFilePath));
        assertThrows(RuntimeException.class, () -> {
            assertFalse(userProfile.set_phone_number("alice","123-456-7890", "password124", "password124"));


        });
    }
    @Test
    public void test_change_username_Success() throws IOException {

        String old_username = "alice";
        String new_username = "alice_was_taken";

        assertEquals(old_username, "alice");

        userProfile.change_username(old_username, new_username, "password124", "password124");

        assertEquals(new_username, "alice_was_taken");
    }

    @Test
    public void test_change_username_first_password_incorrect() throws IOException  {

        String old_username = "alice";
        String new_username = "alice_was_taken";

        assertEquals(old_username, "alice");

        userProfile.change_username(old_username, new_username, "incorrect_password", "password124");

        assertNotEquals("alice", new_username);
    }

    @Test
    public void test_change_username_second_password_incorrect() throws IOException  {

        String old_username = "alice";
        String new_username = "alice_was_taken";

        assertEquals(old_username, "alice");

        userProfile.change_username(old_username, new_username, "password124", "incorrect_password");

        assertNotEquals("alice", new_username);
    }

    @Test
    public void test_change_username_both_passwords_incorrect() throws IOException  {

        String old_username = "alice";
        String new_username = "alice_was_taken";

        assertEquals(old_username, "alice");

        userProfile.change_username(old_username, new_username, "incorrect_password", "incorrect_password");

        assertNotEquals("alice", new_username);
    }

    @Test
    public void test_change_username_username_taken() throws IOException  {

        String old_username = "alice";
        String new_username = "benny";

        assertEquals(old_username, "alice");

        userProfile.change_username(old_username, new_username, "password124", "password124");

        assertNotEquals("alice", new_username);
    }

    @Test
    public void test_enable_2_factor_success_email() throws IOException {

        String method_output;

        method_output = userProfile.enable_2_factor("alice", "password124", "password124", 1);

        assertEquals("Email successfully verified, two-factor authentication has been enabled.", method_output);
    }

    @Test
    public void test_enable_2_factor_success_phone() throws IOException {

        String method_output;

        method_output = userProfile.enable_2_factor("alice", "password124", "password124", 2);

        assertEquals("Phone Number successfully verified, two-factor authentication has been enabled.", method_output);
    }

    @Test
    public void test_enable_2_factor_first_password_incorrect_email() throws IOException {

        String method_output;

        method_output = userProfile.enable_2_factor("alice", "incorrect_password", "password124", 1);

        assertEquals("Passwords do not match or an incorrect password has been entered, please try again.", method_output);
    }

    @Test
    public void test_enable_2_factor_first_password_incorrect_phone() throws IOException {

        String method_output;

        method_output = userProfile.enable_2_factor("alice", "incorrect_password", "password124", 2);

        assertEquals("Passwords do not match or an incorrect password has been entered, please try again.", method_output);
    }

    @Test
    public void test_enable_2_factor_second_password_incorrect_email() throws IOException {

        String method_output;

        method_output = userProfile.enable_2_factor("alice", "password124", "incorrect_password", 1);

        assertEquals("Passwords do not match or an incorrect password has been entered, please try again.", method_output);
    }

    @Test
    public void test_enable_2_factor_second_password_incorrect_phone() throws IOException {

        String method_output;

        method_output = userProfile.enable_2_factor("alice", "password124", "incorrect_password", 2);

        assertEquals("Passwords do not match or an incorrect password has been entered, please try again.", method_output);
    }

    @Test
    public void test_enable_2_factor_both_passwords_incorrect_email() throws IOException {

        String method_output;

        method_output = userProfile.enable_2_factor("alice", "incorrect_password", "incorrect_password", 1);

        assertEquals("Passwords do not match or an incorrect password has been entered, please try again.", method_output);
    }

    @Test
    public void test_enable_2_factor_both_passwords_incorrect_phone() throws IOException {

        String method_output;

        method_output = userProfile.enable_2_factor("alice", "incorrect_password", "incorrect_password", 2);

        assertEquals("Passwords do not match or an incorrect password has been entered, please try again.", method_output);
    }

    @Test
    public void test_change_current_status_success() throws IOException {
        User alice = new User("alice", "password124", "alice@gmail.com", "111-222-3333");

        String result_of_change;

        assertEquals(StatusOptions.OFFLINE, alice.getCurrentStatus());

        result_of_change = userProfile.change_current_status("alice", StatusOptions.ONLINE);

        assertEquals("Your status has been changed to " + StatusOptions.ONLINE, result_of_change);
    }

    @Test
    public void test_change_current_status_already_that_status() throws IOException {
        User alice = new User("alice", "password124", "alice@gmail.com", "111-222-3333");

        String result_of_change;

        assertEquals(StatusOptions.OFFLINE, alice.getCurrentStatus());

        result_of_change = userProfile.change_current_status("alice", StatusOptions.OFFLINE);

        assertEquals("Your current status is already set to " + StatusOptions.OFFLINE, result_of_change);
    }
}
