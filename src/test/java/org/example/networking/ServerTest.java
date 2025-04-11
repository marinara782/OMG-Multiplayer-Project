package org.example.networking;

import org.example.Player;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
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
        testServer.start(80); // simulate server start
        testServer.clearActiveSessions(); // reset sessions for clean test state
    }

    @AfterEach
    void takeDown(){
        //delete file
        File playerFile = new File("players.json");
        if (playerFile.exists()){
            playerFile.delete(); // clean up player data file after tests
        }
    }

    // Test that server starts and port is set correctly
    @Test
    void testStart(){
        testServer.stop();  // ensure stopped before starting again
        testServer.start(80);  // start server
        assertTrue(testServer.isRunning(), "Server should be running after start()");
        assertEquals(80, testServer.getPort(), "Port number should be set correctly.");
    }

    // Test that sessions are created and stored properly when server is running
    @Test
    void testCreateGameSessionWhileServerRunning(){
        testServer.createGameSession("test");
        GameSession session = testServer.createGameSession("checkers");
        assertNotNull(session); // session should be returned
        assertTrue(testServer.getActiveSessions().contains(session), "Session should be found.");
        assertEquals(2, testServer.getActiveSessions().size(), "There should be two active sessions.");
    }

    // Test that sessions are not created when the server is stopped
    @Test
    void testCreateGameSessionWhileServerStopped(){
        testServer.stop();  // stop server before creating session
        testServer.createGameSession("test"); // not added to list if server is off
        GameSession session = testServer.createGameSession("checkers");
        assertNull(session); // session should not be created when server is stopped
    }

    // Test saving and loading player data through JSON serialization
    @Test
    void testSaveAndLoadPlayers() throws IOException {
        List<Player> ogPlayers = testServer.getPlayerList(); // initial list
        Player testPlayer = new Player("Eloisa"); // create player
        testPlayer.setCheckerWins(3);
        testPlayer.setCheckerLosses(1);
        testServer.addPlayer(testPlayer); // add to internal list

        testServer.savePlayers(); // save to JSON file
        testServer.savePlayers(); // repeat save to ensure stability

        ogPlayers.clear(); // simulate clearing memory / fresh server state

        testServer.loadPlayers(); // load from file
        List<Player> loadedPlayers = testServer.getPlayerList();

        //assert results
        assertEquals(1, loadedPlayers.size(), "There should be one player loaded");
        Player loaded = loadedPlayers.get(0);
        assertEquals("Eloisa", loaded.getUsername());
        assertEquals(3, loaded.getCheckerWins());
        assertEquals(1, loaded.getCheckerLosses());

        //clean up. remove file
        File file = new File("players.json");
        if (file.exists()){
            file.delete();
        }
    }

    // Test bug report processing and confirmation message
    @Test
    void testProcessBugReport() {
        bugReport report = new bugReport("Game Logic", "Pieces disappear randomly.");
        String result = Server.processBugReport(report);
        assertEquals("Bug report received. Thank you for your feedback!", result, "Server should confirm bug report receipt.");
    }

    // Test correct login credentials
    @Test
    void testValidLogin() {
        String result = Server.processRequest("LOGIN", "Username", "pass");
        assertEquals("Successful Login", result);
    }

    // Test incorrect login credentials
    @Test
    void testInvalidLogin() {
        String result = Server.processRequest("LOGIN", "wrongUser", "wrongPass");
        assertEquals("Invalid Credentials", result);
    }

    // Test unrecognized request type in login method
    @Test
    void testUnknownRequestType() {
        String result = Server.processRequest("REGISTER", "Username", "pass");
        assertEquals("Unknown Request", result);
    }

    //PROCESS REQUEST TESTS

    // Test response to "CGS" (create game session) command
    @Test
    void testCreateGameSessionRequest() {
        assertEquals("New Game Created", Server.processRequest("CGS"));
    }

    // Test response to "JOIN" command
    @Test
    void testJoinRequest() {
        assertEquals("Joined Game", Server.processRequest("JOIN"));
    }

    // Test response to "MOVE" command
    @Test
    void testMoveRequest() {
        assertEquals("Move Processed", Server.processRequest("MOVE"));
    }

    // Test response to "CHAT" command
    @Test
    void testChatRequest() {
        assertEquals("Message Sent", Server.processRequest("CHAT"));
    }

    // Test response to "DISCONNECT" command
    @Test
    void testDisconnectRequest() {
        assertEquals("Disconnected", Server.processRequest("DISCONNECT"));
    }

    // Test response to "COMPLETE" command
    @Test
    void testCompleteRequest() {
        assertEquals("Game Completed", Server.processRequest("COMPLETE"));
    }

    // Test unrecognized command in second processRequest()
    @Test
    void testUnknownRequest() {
        assertEquals("Unknown Request", Server.processRequest("FOO"));
    }
}