package org.example.networking;

import java.util.ArrayList;
import java.util.List;

//imports for .json file
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;




public class Server {
    //variable instantiation
    private int port;   //server is going to be expecting network connections to come in through this port
    private static boolean isRunning;
    private static List<GameSession> activeSessions;

    //.json file variables
    private final File playerFile = new File("players.json");
    private List<Player> players = new ArrayList<>();


    //GETTERS
    //port getter
    public int getPort(){
        return port;
    }


    //getter for list of active sessions
    public static List<GameSession> getActiveSessions(){
        return new ArrayList<>(activeSessions);
    }

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
        //Jackson ObjectMapper to handle conversion between Java objects and JSON
        ObjectMapper mapper = new ObjectMapper();
        if (playerFile.exists()){
            try{
                players = mapper.readValue(playerFile, new TypeReference<List<Player>>(){});
                System.out.println("Players loaded from file.");
            }catch (IOException e){
                //e.getMessage() returns a human-readable description of what caused the exception
                System.err.println("Error loading players: "+ e.getMessage());
            }
        }
    }

    private void savePlayers(){
        //Jackson ObjectMapper to handle conversion between Java objects and JSON
        ObjectMapper mapper = new ObjectMapper();
        try{
            mapper.writerWithDefaultPrettyPrinter().writeValue(playerFile, players);
            System.out.println("Players saved to file");
        }catch (IOException e) {
            //e.getMessage() returns a human-readable description of what caused the exception
            System.err.println("Error saving players: "+e.getMessage());
        }

    }

    //Checks if server is running, creates a new game session, adds the session to the list of active sessions and returns the session
    public static GameSession createGameSession(String gameType){
        if (!isRunning){
            System.out.println("Cannot create a game session because server is not running.");
            return null;
        }
        GameSession newGameSession = new GameSession(gameType);
        activeSessions.add(newGameSession);
        System.out.println("New "+gameType+" game session created!");
        return newGameSession;
    }

    public List<GameSession> getActiveSessions() {
        return new ArrayList<>(activeSessions);
    }

    public static String processRequest(String request, String username, String password) {
        if (request.equals("LOGIN"))
        {
            if (username.equals("Username") && password.equals("pass")){
                return "Successful Login";
            }
            else
            {
                return "Invalid Credentials";
            }
        }
        else
        {
            return "Unknown Request";
        }
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
