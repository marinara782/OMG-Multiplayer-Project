package org.example.networking;

public class GameSession {
    public void waitForOpponentMove(Object o)
    {
        System.out.print("Waiting for opponent's move...");
    }

    public void processMove(String move)
    {
        System.out.println("Processing move: " + move);
    }

    public void sendChat(String message)
    {
        System.out.println("Sending chat: " + message);
    }

    public void completeSession()
    {
        System.out.println("Complete game session...");
    }
}
