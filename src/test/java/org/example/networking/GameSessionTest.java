package org.example.networking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameSessionTest {
    private GameSession testSession;
    private Client testClient;

    @BeforeEach
    void setUp() {
        Server clientServer = new Server(); // server instantiated but not directly used here
        testClient = new Client();          // create a test client
        testSession = new GameSession();    // create a new session for each test
    }

    // Test that a player can be added successfully and cannot be added twice
    @Test
    void playerAddTest() {
        assertTrue(testSession.addPlayer(testClient), "Add Player Successfully");           // first add should succeed
        assertFalse(testSession.addPlayer(testClient), "Not Add the Same Player");          // second add should fail
    }

    // Test that broadcasting game state doesn't throw any exceptions
    @Test
    void broadcastTest() {
        testSession.addPlayer(testClient); // add a player to simulate real session

        try {
            testSession.broadcastGameState(); // simulate broadcast
        } catch (Exception e) {
            fail("Broadcast game fail, error message: " + e.getMessage());
        }
    }

    // Test waiting for an opponent move (simulated delay)
    @Test
    void opponentmoveTest() {
        try {
            testSession.waitForOpponentMove(new Object()); // simulate opponent wait
        } catch (Exception e) {
            fail("Waiting for opponent move failed, error message: " + e.getMessage());
        }
    }

    // Test sending a chat message doesn't throw an exception
    @Test
    void chatTest() {
        try {
            testSession.sendChat("Example Test"); // simulate sending a chat
        } catch (Exception e) {
            fail("Failed chat, error message: " + e.getMessage());
        }
    }

    // Test that game session can be completed without error
    @Test
    void gameCompleteTest() {
        try {
            testSession.completeSession(); // simulate completing session
        } catch (Exception e) {
            fail("Failed to complete session, error message: " + e.getMessage());
        }
    }

    // Test move processing:
    // - Should fail if player is not in session
    // - Should succeed once player is added
    @Test
    void moveprocessTest() {
        assertEquals(testSession.processMove(testClient, "b6"), "Player is not part of this session!"); // before add

        testSession.addPlayer(testClient);
        assertEquals(testSession.processMove(testClient, "b6"), "Move processed: b6"); // after add
    }

    // Test that two sessions have unique session IDs
    @Test
    void idTest() {
        GameSession testSession_2 = new GameSession();
        assertNotEquals(testSession_2.getSessionid(), testSession.getSessionid()); // IDs should differ
    }
}