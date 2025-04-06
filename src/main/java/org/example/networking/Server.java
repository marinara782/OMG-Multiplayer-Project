package org.example.networking;

import java.util.ArrayList;
import java.util.List;

//imports for .json file
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;



public class Server {
    //server is going to be expecting network connections to come in through this port
    private int port;
    private boolean isRunning;
    private List<GameSession> activeSessions;
    private String playerID;
    private final File playerFile = new File("players.json");
    private List<Player> players = new ArrayList<>();

    public Server(){
        this.activeSessions = new ArrayList<>();
        this.isRunning = false;
    }

    public void start(int port){
        if (!isRunning){
            this.port = port;
            this.isRunning = true;
            System.out.println("Server has started on port "+port "for player "+ playerID);
        }else{
            System.out.println("Server is already running!");
        }
    }

    private void loadPlayers(){
        ObjectMapper mapper = new ObjectMapper();
        if (playerFile.exists()){
            try{
                players = mapper.readValue(playerFile, new TypeReference<List<Player>>(){});
                System.out.println("Players loaded from file.");
            }catch (IOException e){
                System.err.println("Error loading players: "+ e.getMessage());
            }
        }
    }

    //Checks if server is running, creates a new game session, adds the session to the list of active sessions and returns the session
    public GameSession createGameSession(String gameType){
        if (!isRunning){
            System.out.println("Cannot create a game session because server is not running.");
            return null;
        }
        GameSession newGameSession = new GameSession;
        activeSessions.add(newGameSession);
        System.out.println("New "+gameType+" game session created!");
        return newGameSession;
    }

    public List<GameSession> getActiveSessions() {
        return new ArrayList<>(activeSessions);
    }





    //all code below this may be removed as we make our cases more complex / separate them into individual methods
    public static String processRequest(String request) {
        switch (request) {
            case "LOGIN":
                return "Login Successful";
            case "CGS":
                return "New Game Created";
            case "JOIN":
                return "Joined Game";
            case "MOVE":
                return "Move Processed";
            case "CHAT":
                return "Message Sent";
            case "DISCONNECT":
                return "Disconnected";
            case "COMPLETE":
                return "Game Completed";
            default:
                return "Unknown Request";

        }
    }

    //new server (using firebase)
}
