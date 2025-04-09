package org.example.networking;

import org.junit.jupiter.api.*;
import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {
    //instantiate server object
    private Server testServer;

    //set up-- make a server object
    @BeforeEach
    void setUp(){
        //before each test, make a new server object
        testServer = new Server();
        //enter port number 80 as parameter
        testServer.start(80);
        testServer.clearActiveSessions();
    }

    @AfterEach
    void takeDown(){
        //delete file
        File playerFile = new File("players.json");
        if (playerFile.exists()){
            playerFile.delete();
        }
    }

    @Test
    void testStart(){
        testServer.stop();
        //enter port number 80 as parameter
        testServer.start(80);
        //test
        System.out.println("From test: isRunning = " + testServer.isRunning());
        //create a game session
        assertTrue(testServer.isRunning(), "Server should be running after start()");
        assertEquals(80, testServer.getPort(), "Port number should be set correctly.");
    }

    @Test
    void testCreateGameSessionWhileServerRunning(){
        testServer.createGameSession("test");
        //check if new game session was returned
        GameSession session = testServer.createGameSession("checkers");
        assertNotNull(session);
        //check if session has been added to list of active sessions
        assertTrue(testServer.getActiveSessions().contains(session), "Session should be found.");
        assertEquals(2, testServer.getActiveSessions().size(), "There should be two active sessions.");
    }

    @Test
    void testCreateGameSessionWhileServerStopped(){
        testServer.createGameSession("test");   //not stored but still added to list
        GameSession session = testServer.createGameSession("checkers");
        assertNull(session);

    }




}
