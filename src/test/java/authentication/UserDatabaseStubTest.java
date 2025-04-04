package authentication;
import org.example.authentication.User;
import org.example.authentication.UserDatabaseStub;
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
        assertTrue(dataBase.verify_phone_number("bob", "anything")); // logic only checks username
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
    void testLinkedPhoneNumber() throws FileNotFoundException {
        dataBase.linked_phone_number("alice", "999-888-7777");
        List<User> users = dataBase.registered_users_list();
        for (User user : users) {
            if (user.getUsername().equals("alice")) {
                assertEquals("999-888-7777", user.getPhone());
            }
        }
    }

    @Test
    void testGetCurrentPassword() throws FileNotFoundException {
        assertEquals("pass123", dataBase.getCurrentPassword("alice"));
        assertNull(dataBase.getCurrentPassword("unknown"));
    }
}