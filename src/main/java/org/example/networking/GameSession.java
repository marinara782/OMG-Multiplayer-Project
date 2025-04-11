package org.example.networking;

import java.util.ArrayList;
import java.util.List;

public class GameSession {

    // Unique session ID for each game session
    private final int sessionid;

    // Game-specific fields (can be used to track different games)
    private int GameID;
    private String gameType;

    // List of clients/players in the session
    private List<Client> players;

    // Static counter to generate unique session IDs
    private static int counter_id = 0;

    // Constructor to initialize a new game session
    public GameSession() {
        this.gameType = gameType;  // NOTE: this assignment does nothing (likely a placeholder or bug)
        this.players = new ArrayList<>();  // initialize empty player list
        this.sessionid = ++counter_id;     // assign a unique session ID
    }

    // Attempts to add a player to the session
    public boolean addPlayer(Client player){
        if (players.contains(player)) {
            System.out.println("Player already in the session!");
            return false;
        } else {
            players.add(player);
            System.out.println("Player added to the session.");
            return true;
        }
    }

    // Simulates broadcasting game state to all players
    public void broadcastGameState(){
        System.out.println("Broadcasting game state to " + players.size() + " players.");
    }

    // Simulates waiting for the opponent's move (with artificial delay)
    public void waitForOpponentMove(Object o) {
        System.out.print("Waiting for opponent's move...");
        try {
            Thread.sleep(2000);  // simulate delay
        } catch (InterruptedException e) {
            System.out.println("Connection interrupted!");
        }
    }

    // Handles a move from a player and returns the result message
    public String processMove(Client player, String moveData) {
        if (players.contains(player)){
            System.out.println("Processing move: " + moveData);
            return ("Move processed: " + moveData);
        } else {
            return "Player is not part of this session!";
        }
    }

    // Stub to simulate that every session is multiplayer
    public boolean isMultiplayer() {
        return true; // stubbed to always simulate multiplayer for now
    }

    // Sends a chat message in the session (stubbed as console output)
    public void sendChat(String message){
        System.out.println("Sending chat: " + message);
    }

    // Completes/ends the session (stubbed as console output)
    public void completeSession(){
        System.out.println("Complete game session...");
    }

    // Getter for session ID
    public int getSessionid() {
        return sessionid;
    }
}