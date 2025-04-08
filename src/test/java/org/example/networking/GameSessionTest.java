package org.example.networking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameSessionTest {
    private GameSession testSession;
    private Client testClient;

    @BeforeEach
    void setUp() {
        testClient = new Client();
        testSession = new GameSession();
    }


    @Test
    void playerAddTest() {
        assertTrue(testSession.addPlayer(testClient), "Add Player Successfully");
        assertFalse(testSession.addPlayer(testClient), "Not Add the Same Player");
    }

    @Test
    void broadcastTest() {
        testSession.addPlayer(testClient);

        try {
            testSession.broadcastGameState();
        } catch (Exception e) {
            fail("Broadcast game fail, error message: " + e.getMessage());
        }
    }

    @Test
    void opponentmoveTest() {
        try {
            testSession.waitForOpponentMove(new Object());

        } catch (Exception e) {
            fail("Waiting for opponent move failed, error message: " + e.getMessage());
        }
    }

    @Test
    void chatTest() {
        try {
            testSession.sendChat("Example Test");
        } catch (Exception e) {
            fail("Failed chat, error message: " + e.getMessage());
        }
    }

    @Test
    void gameCompleteTest() {
        try {
            testSession.completeSession();
        } catch (Exception e) {
            fail("Failed to complete session, error message: " + e.getMessage());
        }
    }

    @Test
    void moveprocessTest() {
        assertEquals(testSession.processMove(testClient, "b6"), "Player is not part of this session!");

        testSession.addPlayer(testClient);
        assertEquals(testSession.processMove(testClient, "b6"), "Move processed: b6");
    }

    @Test
    void idTest() {
        GameSession testSession_2 = new GameSession();
        assertNotEquals(testSession_2.getSessionid(), testSession.getSessionid());
    }
}
