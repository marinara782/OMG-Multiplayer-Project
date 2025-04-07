package org.example.networking;

import java.util.ArrayList;
import java.util.List;

public class GameSession {

    private final int sessionid;
    private int GameID;
    private String gameType;
    private List<Client> players;
    private static int counter_id = 0;


    public GameSession() {
        this.gameType = gameType;
        this.players = new ArrayList<>();
        this.sessionid = ++counter_id;
    }
    public boolean addPlayer(Client player){
        if (players.contains(player)) {
            System.out.println("Player already in the session!");
            return false;
        }else{
            players.add(player);
            System.out.println("Player added to the session.");
            return true;
        }
    }

    public void broadcastGameState(){
        System.out.println("Broadcasting game state to "+ players.size() +" players.");
    }

    public void waitForOpponentMove(Object o) {
        System.out.print("Waiting for opponent's move...");
        try
        {
            Thread.sleep(2000);
        }
        catch(InterruptedException e)
        {
            System.out.println("Connection interrupted!");
        }
    }

    public String processMove(Client player, String moveData) {
        if (players.contains(player)){
            System.out.println("Processing move: " + moveData);
            return ("Move processed: " +moveData);
        }else{
            return "Player is not part of this session!";
        }

    }

    public void sendChat(String message){
        System.out.println("Sending chat: " + message);
    }

    public void completeSession(){
        System.out.println("Complete game session...");
    }

    public int getSessionid()
    {
        return sessionid;
    }
}
