package org.example.networking;

public class Server {
    private int port;
    private boolean isRunning;





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
}
