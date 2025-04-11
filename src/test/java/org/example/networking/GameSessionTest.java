package org.example.networking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the GameSession class.
// This class verifies player management, session ID generation, move processing, chat, and other core session functionality.
public class GameSessionTest {
    private GameSession testSession;
    private Client testClient;


    // Setup method to initialize a new GameSession and Client before each test.
    @BeforeEach
    void setUp() {
        Server clientServer = new Server();
        testClient = new Client();
        testSession = new GameSession();
    }


    // Tests adding a player to the session and prevents duplicate adds.
    @Test
    void playerAddTest() {
        // First add should return true
        assertTrue(testSession.addPlayer(testClient), "Add Player Successfully");
        // Second add (duplicate) should return false
        assertFalse(testSession.addPlayer(testClient), "Not Add the Same Player");
    }


    // Tests that broadcasting game state does not throw exceptions.
    @Test
    void broadcastTest() {
        testSession.addPlayer(testClient);

        try {
            testSession.broadcastGameState();
        } catch (Exception e) {
            fail("Broadcast game fail, error message: " + e.getMessage());
        }
    }


    // Tests that the session can wait for opponent move without crashing.
    @Test
    void opponentmoveTest() {
        try {
            testSession.waitForOpponentMove(new Object());

        } catch (Exception e) {
            fail("Waiting for opponent move failed, error message: " + e.getMessage());
        }
    }


    // Tests sending a chat message to players.
    @Test
    void chatTest() {
        try {
            testSession.sendChat("Example Test");
        } catch (Exception e) {
            fail("Failed chat, error message: " + e.getMessage());
        }
    }


    // Tests that completing the game session executes without exception.
    @Test
    void gameCompleteTest() {
        try {
            testSession.completeSession();
        } catch (Exception e) {
            fail("Failed to complete session, error message: " + e.getMessage());
        }
    }

    // Tests move processing based on player presence in the session.
    @Test
    void moveprocessTest() {
        // Before adding the player: should reject the move
        assertEquals(testSession.processMove(testClient, "b6"), "Player is not part of this session!");

        testSession.addPlayer(testClient);
        // After adding the player: should process the move
        assertEquals(testSession.processMove(testClient, "b6"), "Move processed: b6");
    }

    // Tests that each GameSession has a unique session ID.
    @Test
    void idTest() {
        GameSession testSession_2 = new GameSession();
        assertNotEquals(testSession_2.getSessionid(), testSession.getSessionid());
    }
}
