package org.example.networking;

import org.junit.jupiter.api.*;
import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {
    //instantiate server object
    private Server server;

    //set up-- make a server object
    @BeforeEach
    void setUp(){
        //before each test, make a new server object
        server = new Server();
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
        //enter port number 80 as parameter
        server.start(80);
        //create a game session
        assertTrue(server.isRunning(), "Server should be running after start()");
        assertEquals(80, server.getPort(), "Port number should be set correctly.");
    }

    @Test
    void testCreateGameSessionWhileServerRunning(){
        server.createGameSession("test");
        //check if new game session was returned
        server.start(80);
        GameSession session = server.createGameSession("checkers");
        assertNotNull(session);
        //check if session has been added to list of active sessions

    }

}
