package org.example.utilities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.example.utilities.ChatManager;

public class ChatManagerTest {

    @Test
    public void testTicTacToeWinResponse() {
        String response = ChatManager.TicTacToeBot.generateResponse("I win!");
        assertNotNull(response);
        assertFalse(response.isEmpty());
    }

    @Test
    public void testTicTacToeMoveResponse() {
        String response = ChatManager.TicTacToeBot.generateResponse("B2");
        assertNotNull(response);
        assertFalse(response.isEmpty());
    }

    @Test
    public void testCheckersJumpResponse() {
        String response = ChatManager.CheckersBot.generateResponse("I’m going to jump you!");
        assertNotNull(response);
        assertFalse(response.isEmpty());
    }

    @Test
    public void testConnectFourColumnDrop() {
        String response = ChatManager.ConnectFourBot.generateResponse("4");
        assertTrue(response.startsWith("Dropped in column 4"));
    }

    @Test
    public void testConnectFourBlock() {
        String response = ChatManager.ConnectFourBot.generateResponse("block");
        assertNotNull(response);
        assertFalse(response.isEmpty());
    }

}

