package org.example.networking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {
    private Client testClient;

    @BeforeEach
    void setup() {
        testClient = new Client();         // initialize new client
        Server serverTest = new Server();  // initialize and start test server
        serverTest.start(1234);            // start server on port 1234
    }

    // Test that a client can connect, and cannot connect again if already connected
    @Test
    void gameConnectDisconnectTest() {
        assertTrue(testClient.connect("localhost", 1234), "Connect on First Attempt");
        assertFalse(testClient.connect("localhost", 1234), "Should not Connect Again");

        testClient.disconnect();  // disconnect after test
    }

    // Test successful login attempt
    @Test
    void loginSuccesstest() {
        try {
            testClient.login("Username", "testpass");  // correct format, should log response
        } catch (Exception L) {
            fail("Login failed, exception message: " + L.getMessage());
        }
    }

    // Test failed login attempt with wrong credentials
    @Test
    void loginfailtest() {
        try {
            testClient.login("wrongUsername", "wrongtestpass");
        } catch (Exception l) {
            fail("Login failed, exception message: " + l.getMessage());
        }
    }

    // Test game creation and joining functionality
    @Test
    void createjoinGameTest() {
        testClient.createGame("connect4"); // client creates a game

        GameSession testSession = Server.createGameSession("testSession"); // another session is created manually
        assertNotNull(testSession); // make sure it's valid

        try {
            testClient.joinGame(testSession); // attempt to join
        } catch (Exception j) {
            fail("Failed Create/Join Test, error message: " + j.getMessage());
        }
    }

    // Test sending a game move and chat message
    @Test
    void movechatTests() {
        GameSession movechatSessionTest = Server.createGameSession("chess");
        movechatSessionTest.addPlayer(testClient);  // simulate adding client to the session

        try {
            testClient.sendGameMove(movechatSessionTest, "e4");  // simulate move
        } catch (Exception m) {
            fail("Failed move, error message: " + m.getMessage());
        }

        try {
            testClient.sendChat(movechatSessionTest, "test chat");  // simulate chat
        } catch (Exception c) {
            fail("Failed chat, error message: " + c.getMessage());
        }
    }

    // Test completing a game session
    @Test
    void completeGameTest() {
        GameSession completeSession = Server.createGameSession("checkers");

        try {
            testClient.completeGame(completeSession); // simulate ending session
        } catch (Exception c) {
            fail("Failed complete, error message: " + c.getMessage());
        }
    }

    // Test the method that sends a full sequence of mock server requests
    @Test
    void communicateTest() {
        try {
            testClient.CommunicateWithServer(); // mock commands to server
        } catch (Exception e) {
            fail("Failed Communication, error message: " + e.getMessage());
        }
    }

    // Ensure bug reports can be sent without exceptions
    @Test
    void testSendBugReportDoesNotThrow() {
        Client client = new Client();
        assertDoesNotThrow(() ->
                client.sendBugReport("UI Bug", "Buttons are misaligned on login screen.")
        );
    }
}
