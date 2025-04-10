package org.example.authentication;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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
        login = new Login(null);
    }

    @AfterEach
    void tearDown() throws IOException{
        Files.deleteIfExists(Paths.get(testFilePath));
    }

    //forgot_Password() tests
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
}
