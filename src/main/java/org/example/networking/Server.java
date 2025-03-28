package org.example.networking;

public class Server {
    public String processRequest(String request) {
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
