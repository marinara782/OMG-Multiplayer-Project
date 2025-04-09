package org.example.networking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {
    private Client testClient;

    @BeforeEach
    void setup()
    {
        testClient = new Client();
        Server serverTest = new Server();
        serverTest.start(1234);
    }

    @Test
    void gameConnectDisconnectTest() {
        assertTrue(testClient.connect("localhost", 1234), "Connect on First Attempt");
        assertFalse(testClient.connect("localhost", 1234), "Should not Connect Again");

        testClient.disconnect();
    }

    @Test
    void loginSuccesstest() {
        try {
            testClient.login("Username", "testpass");
        }
        catch (Exception L)
        {
            fail("Login failed, exception message: " + L.getMessage());
        }
    }

    @Test
    void loginfailtest() {
        try {
            testClient.login("wrongUsername", "wrongtestpass");
        }
        catch (Exception l)
        {
            fail("Login failed, exception message: " + l.getMessage());
        }
    }

    @Test
    void createjoinGameTest() {
        testClient.createGame("connect4");
        GameSession testSession = Server.createGameSession("testSession");

        assertNotNull(testSession);

        try
        {
            testClient.joinGame(testSession);
        } catch (Exception j) {
            fail("Failed Create/Join Test, error message: " + j.getMessage());
        }
    }

    @Test
    void movechatTests() {
        GameSession movechatSessionTest = Server.createGameSession("chess");
        movechatSessionTest.addPlayer(testClient);

        try {
            testClient.sendGameMove(movechatSessionTest, "e4");
        } catch (Exception m) {
            fail("Failed move, error message: " + m.getMessage());
        }

        try {
            testClient.sendChat(movechatSessionTest, "test chat");
        } catch (Exception c) {
            fail("Failed chat, error message: " + c.getMessage());
        }

    }

    @Test
    void completeGameTest()
    {
        GameSession completeSession = Server.createGameSession("checkers");

        try {
            testClient.completeGame(completeSession);
        }
        catch (Exception c)
        {
            fail("Failed complete, error message: " + c.getMessage());
        }
    }

    @Test
    void communicateTest()
    {
        try {
            testClient.CommunicateWithServer();
        } catch (Exception e) {
            fail("Failed Communication, error message: " + e.getMessage());
        }
    }





}
