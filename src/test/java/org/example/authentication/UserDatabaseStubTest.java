package org.example.authentication;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserDatabaseStubTest {

    private final String testFilePath = "temp.txt";
    private UserDatabaseStub dataBase;

    @BeforeEach
    void setUp() throws IOException {
        dataBase = new UserDatabaseStub();
        List<String> lines = Arrays.asList(
                "alice, pass123, alice@gmail.com, 111-222-3333",
                "bob, qwerty, bob@gmail.com, 444-555-6666"
        );
        Files.write(Paths.get(testFilePath), lines);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(testFilePath));
    }

    @Test
    public void testWriteUsersToFile() throws IOException {
        String filePath = "temp.txt";
        List<User> users = new ArrayList<>();
        users.add(new User("testuser1", "pass1", "test1@gmail.com", "123-4567-8901"));
        users.add(new User("testuser2", "pass2", "test2@gmail.com", "098-7654-3210"));

        UserDatabaseStub tempData = new UserDatabaseStub();
        tempData.write_users_to_file(users);

        List<String> lines = Files.readAllLines(Paths.get(filePath));
        assertEquals(2, lines.size());
        assertEquals("testuser1,pass1,test1@gmail.com,123-4567-8901", lines.get(0).trim());
        assertEquals("testuser2,pass2,test2@gmail.com,098-7654-3210", lines.get(1).trim());
    }

    @Test
    void testRegisteredUsersList() throws FileNotFoundException {
        List<User> users = dataBase.registered_users_list();
        assertEquals(2, users.size());
        assertEquals("alice", users.get(0).getUsername());
        assertEquals("bob", users.get(1).getUsername());
    }

    @Test
    void testAuthenticateUser_Success() throws FileNotFoundException {
        assertTrue(dataBase.Authenticate_user("alice", "pass123", "alice@gmail.com", "111-222-3333"));
    }

    @Test
    void testAuthenticateUser_Failure() throws FileNotFoundException {
        assertFalse(dataBase.Authenticate_user("alice", "wrongpass", "alice@gmail.com", "111-222-3333"));
    }

    @Test
    void testVerifyAccount() throws FileNotFoundException {
        assertTrue(dataBase.verify_account("alice", "pass123"));
        assertFalse(dataBase.verify_account("alice", "wrong"));
    }

    @Test
    void testVerifyUsername() throws FileNotFoundException {
        assertTrue(dataBase.verify_username("bob"));
        assertFalse(dataBase.verify_username("charlie"));
    }

    @Test
    void testVerifyPassword() throws FileNotFoundException {
        assertTrue(dataBase.verify_password("alice", "pass123"));
        assertFalse(dataBase.verify_password("alice", "pass456"));
    }

    @Test
    void testVerifyEmail() throws FileNotFoundException {
        assertTrue(dataBase.verify_email("bob", "bob@gmail.com"));
        assertFalse(dataBase.verify_email("bob", "wrong@gmail.com"));
    }

    @Test
    void testVerifyPhoneNumber() throws FileNotFoundException {
        assertTrue(dataBase.verify_phone_number("bob", "444-555-6666"));
    }

    @Test
    void testUpdateUsername() throws FileNotFoundException {
        dataBase.update_username("alice", "aliceNew");
        assertTrue(dataBase.verify_username("aliceNew"));
        assertFalse(dataBase.verify_username("alice"));
    }

    @Test
    void testUpdatePassword() throws FileNotFoundException {
        dataBase.update_password("bob", "qwerty", "newpass");
        assertTrue(dataBase.verify_password("bob", "newpass"));
    }

    @Test
    void testUpdateEmail() throws FileNotFoundException {
        dataBase.update_email("bob", "bob_new@gmail.com", "qwerty");
        assertEquals("bob_new@gmail.com", dataBase.getCurrentEmail("bob"));
    }

    @Test
    void testLinkedPhoneNumber() throws FileNotFoundException {
        dataBase.linked_phone_number("alice", "999-888-7777");
        List<User> users = dataBase.registered_users_list();
        for (User user : users) {
            if (user.getUsername().equals("alice")) {
                assertEquals("999-888-7777", user.getPhone());
            }
        }
    }

    // gets username from dataBase compares if its in expected dataBase
    @Test
    void testGetCurrentUsername() throws FileNotFoundException {
        assertEquals("alice", dataBase.getUsername("alice"));
        assertNull(dataBase.getUsername("unknown"));
    }

    @Test
    void testGetCurrentPassword() throws FileNotFoundException {
        assertEquals("pass123", dataBase.getCurrentPassword("alice"));
        assertNull(dataBase.getCurrentPassword("unknown"));
    }

    // gets email from dataBase compares if its in expected dataBase
    @Test
    void testGetCurrentEmail() throws FileNotFoundException {
        assertEquals("bob@gmail.com", dataBase.getCurrentEmail("bob"));
        assertNull(dataBase.getCurrentPassword("unknown"));
    }

    // gets phone from dataBase compares if its in expected dataBase
    @Test
    void testGetCurrentPhone() throws FileNotFoundException {
        assertEquals("111-222-3333", dataBase.getCurrentPhone("alice"));
        assertNull(dataBase.getCurrentPhone("unknown"));
    }

    @Test
    public void testSendEmailWithValidEmail() throws FileNotFoundException {
        String code = dataBase.send_email("bob@gmail.com");
        assertTrue(code.matches("\\d{6}")); // 6-digit code
    }

    @Test
    public void testSendEmailWithInvalidEmail() throws FileNotFoundException {
        String result = dataBase.send_email("notfound@gmail.com");
        assertEquals("Email not found", result);
    }

    @Test
    public void testSendTextWithValidPhoneNumber() throws FileNotFoundException {
        String code = dataBase.send_text("111-222-3333");
        assertTrue(code.matches("\\d{6}")); // 6-digit code
    }

    @Test
    public void testSendTextWithInvalidPhoneNumber() throws FileNotFoundException {

        String result = dataBase.send_text("0000000000");
        assertEquals("Phone number not found", result);
    }
}
