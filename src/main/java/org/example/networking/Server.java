package org.example.networking;

//IMPORTS
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

//imports for .json file
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.example.Player;

import java.io.File;
import java.io.IOException;




public class Server {
    //variable instantiation
    private int port;   //server is going to be expecting network connections to come in through this port
    private static boolean isRunning;   //boolean for turning the simulated server on and off
    private static List<GameSession> activeSessions;    //list of active game sessions

    //.json file variables
    private File playerFile = new File("players.json"); //json file for players and their attributes
    private List<Player> players = new ArrayList<>();   //list of players


    //GETTERS
    //port getter
    public int getPort(){
        return port;
    }

    //getter for isRunning boolean
    public boolean isRunning() {
        return isRunning;
    }

    //getter for list of active sessions
    AbstractList<GameSession> getActiveSessions(){
        return new ArrayList<>(activeSessions);
    }

    //getter for player list
    public List<Player> getPlayerList(){
        return new ArrayList<>(players);
    }

    //clear active sessions
    public void clearActiveSessions(){
        activeSessions.clear();
    }

    //HELPER METHOD
    public void addPlayer(Player player){
        players.add(player);
    }



    public Server(){
        this.activeSessions = new ArrayList<>();
        this.isRunning = false;
        this.playerFile = playerFile;
    }

    //method for starting the server
    public void start(int port){
        //if server is not already running, start it.
        if (!isRunning){
            this.port = port;   //server would expect connection to come in through this port if it were real
            this.isRunning = true;  //turn on simulated server
            System.out.println("Server has started on port "+port);
        }else{
            //if server is already running, print this message:
            System.out.println("Server is already running!");
        }
    }

    //method for stopping the server
    public void stop(){
        isRunning = false;
    }

    //method for loading player list
    void loadPlayers(){
        //if the server is running, load the list of players
        if (isRunning == true){
            //Jackson ObjectMapper to handle conversion between java objects and json
            ObjectMapper mapper = new ObjectMapper();
            //if the file exists, load the players from the file
            if (playerFile.exists()){
                try{
                    players = mapper.readValue(playerFile, new TypeReference<List<Player>>(){});    //load players from file
                    System.out.println("Players loaded from file.");
                }catch (IOException e){
                    //e.getMessage() returns a human-readable description of what caused the exception
                    System.err.println("Error loading players: "+ e.getMessage());
                }
            }
        }
        //if server is not running, print this error message:
        else{
            System.out.println("Server disconnected. Please connect and try again.");
        }

    }

    //method for saving players in the json file
    public void savePlayers(){
        //if the server is running, save the player in the file
        if (isRunning){
            //Jackson ObjectMapper to handle conversion between Java objects and JSON
            ObjectMapper mapper = new ObjectMapper();
            try{
                mapper.writerWithDefaultPrettyPrinter().writeValue(playerFile, players);    //write players to file
                System.out.println("Players saved to file");
            }catch (IOException e) {
                //e.getMessage() returns a human-readable description of what caused the exception
                System.err.println("Error saving players: "+e.getMessage());
            }
        }
        //if server is not running, print this error message:
        else{
            System.out.println("Server disconnected. Please connect and try again.");
        }


    }

    //Checks if server is running, creates a new game session, adds the session to the list of active sessions and returns the session
    public static GameSession createGameSession(String gameType){
        //if the server is not running, print error message and return null
        if (!isRunning){
            System.out.println("Cannot create a game session because server is not running.");
            return null;
        }
        GameSession newGameSession = new GameSession(); //create new game session
        activeSessions.add(newGameSession); //add the new session to the list of active game sessions
        System.out.println("New "+gameType+" game session created!");
        return newGameSession;
    }

    //method for processing bug report
    public static String processBugReport(bugReport report) {
        //if the server is running, process the bug report
        if (isRunning){
            //let the user know their report has been processed
            System.out.println("Received bug report:");
            System.out.println("- Type: " + report.getType());
            System.out.println("- Comment: " + report.getComment());

            return "Bug report received. Thank you for your feedback!";
        }
        //if the server is not running, print this error message:
        else{
            return "Server disconnected. Please connect and try again.";
        }

    }



    //method for processing login request
    public static String processRequest(String request, String username, String password) {
        if (request.equals("LOGIN"))
        {
            //let the user know their login was successful if their username and passwords are valid
            if (username.equals("Username") && password.equals("pass")){
                return "Successful Login";
            }
            //if credentials invalid, let the user know
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





    //method for several server responses to different requests
    public static String processRequest(String request) {
        switch (request) {
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
}

