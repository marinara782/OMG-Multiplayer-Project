package org.example.authentication;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    private final String testFilePath = "temp.txt";
    private Login login;

    @BeforeEach
    void setup() throws IOException{
        List<String> lines = List.of("alice, pass123, alice@gmail.com, 111-222-3333","bob, qwerty, bob@gmail.com, 444-555-6666");
        Files.write(Paths.get(testFilePath), lines);
        login = new Login();
    }

    @AfterEach
    void tearDown() throws IOException{
        Files.deleteIfExists(Paths.get(testFilePath));
    }

    //forgot_Password() tests

    // successful password change test
    @Test
    void testForgotPassword_Success() throws IOException {
        assertTrue(login.forgot_password("alice", "alice@gmail.com", "newPass123"));
    }

    // test for forgetting password with wrong username
    @Test
    void testForgotPassword_InvalidUsername() throws IOException{
        assertFalse(login.forgot_password("charlie, ", "alice@gmail.com", "newPass123"));
    }

    // forgot password with wrong email
    @Test
    void testForgotPassword_EmailMismatched() throws IOException{
        assertFalse(login.forgot_password("alice", "wrong@gmail.com", "newPass123"));
    }

    //changePassword() testcases

    // successful change password test case
    @Test
    void testChangePassword_Success() throws IOException{
        assertTrue(login.change_password("alice", "pass123", "newPass456", "newPass456"));
    }

    // change password with wrong old password
    @Test
    void testChangePassword_InvalidOldPassword() throws IOException{
        assertFalse(login.change_password("alice", "wrongOld", "newPass456", "newPass456"));
    }

    // change password but too short
    @Test
    void testChangePassword_TooShort() throws IOException{
        assertFalse(login.change_password("alice", "pass123", "short1", "short1"));
    }

    // change password but too long
    @Test
    void testChangePassword_MissingLetterOrNumber() throws IOException{
        //does not contain numbers
        assertFalse(login.change_password("alice", "pass123", "abcdefgh", "abcdefgh"));
        //does not contain letters
        assertFalse(login.change_password("alice", "pass123", "12345678", "12345678"));
    }

    // change password but new password and confirm password are mistmatched
    @Test
    void testChangePassword_ConfirmationMismatch() throws IOException{
        assertFalse(login.change_password("alice", "pass123", "newpass456", "wrongPassConfirm"));
    }

    //change_Email() testcases

    // test for changing email with correct credentials
    @Test
    void testChangeEmail_Success() throws IOException{
        assertTrue(login.change_email("bob", "qwerty", "bob@gmail.com", "newBob@gmail.com"));
    }

    // test for changing email with wrong username
    @Test
    void testChangeEmail_WrongPassword() throws IOException{
        assertFalse(login.change_email("bob", "wrongpass", "bob@gmail.com", "newBob@gmail.com"));
    }

    // tests for login_account()

    // Test for checking if a registered user can log in with correct information
    @Test
    void test_login_account_success() {
        assertTrue(login.login_account("alice", "pass123"));
    }

    // attempting to log in with an incorrect password
    @Test
    void test_login_account_incorrect_password() {
        assertFalse(login.login_account("alice", "wrongPass123"));
    }

    // attempting to log in with an incorrect username
    @Test
    void test_login_account_incorrect_username() {
        assertFalse(login.login_account("not_alice", "pass123"));
    }

    // attempting to log in while not a registered user
    @Test
    void test_login_account_not_registered_user() {
        assertFalse(login.login_account("benjamin", "not_a_password123"));
    }

    // tests for forgot_username()

    // test for successfully recovering a forgotten username
    @Test
    void forgot_username_success() throws IOException {
        assertEquals("alice", login.forgot_username("alice@gmail.com"));
    }

    // test for unsuccessfully recovering a forgotten username
    @Test
    void forgot_username_failure() throws IOException {
        assertEquals("There was an error during the retrieval of your username, try again.", login.forgot_username("not_alice@gmail.com"));
    }

    // tests for returnToLoginScreen()

    // test for successfully returning to the login screen
    @Test
    void test_return_to_login_screen_success() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        System.setOut(printStream);

        Login.returnToLoginScreen();

        assertTrue(outputStream.toString().contains("Return to the login screen..."));
    }

    // tests for logout()

    // test for successfully logging out of the platform
    @Test
    void test_logout_success() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PrintStream printStream = new PrintStream(outputStream);

        System.setOut(printStream);

        Login.logout();

        assertTrue(outputStream.toString().contains("Logging out.."));
    }

    // tests for create_account()

    // test for successfully creating an account
    @Test
    void testCreateAccount_Success() throws IOException {
        // Test account creation
        assertTrue(Login.createAccount("charlie", "char123", "charlie@gmail.com", "555-123-4567"));

        // Verify the account was added to the file
        List<String> lines = Files.readAllLines(Paths.get(testFilePath));
        assertEquals(3, lines.size(), "File should have 3 accounts now");
        assertTrue(lines.stream().anyMatch(line -> line.startsWith("charlie,char123")),
                "New account should be in file");
    }

    // test for successfully creating an account when the username is already taken
    @Test
    void testCreateAccount_UsernameTaken() throws IOException {
        // Try to create account with existing username
        assertFalse(Login.createAccount("alice", "newpass", "newalice@test.com", "555-111-2222"));

        // Verify original account still exists and no duplicate was created
        List<String> lines = Files.readAllLines(Paths.get(testFilePath));
        assertEquals(2, lines.size()); // Should still only have alice and bob
    }

    // test for successfully creating an account and putting it into a new database file
    @Test
    void testCreateAccount_NewDatabaseFile() throws IOException {
        // Delete existing file to test creation of new database


        // Create first account
        assertTrue(Login.createAccount("firstUser", "firstPass", "first@test.com", "000-000-0000"));


    }

    // test for creating an account when there is incomplete information given
    @Test
    void testCreateAccount_EmptyFields() {
        // Test with empty username
        assertThrows(IllegalArgumentException.class, () -> {
            Login.createAccount("", "validPass", "test@test.com", "1234567890");
        });

        // Test with empty password
        assertThrows(IllegalArgumentException.class, () -> {
            Login.createAccount("testUser", "", "test@test.com", "1234567890");
        });
    }

    // test for creating an account when the email format given is invalid
    @Test
    void testCreateAccount_InvalidEmailFormat() throws IOException {

        assertFalse(Login.createAccount("testUser", "testPass", "invalid-email", "555-123-4567"));
    }

    // test for creating an account when the phone format given is invalid
    @Test
    void testCreateAccount_InvalidPhoneFormat() throws IOException {
        // Should still succeed since phone format isn't validated in the method
        assertFalse(Login.createAccount("testUser", "testPass", "test@test.com", "invalid-phone"));
    }
}
