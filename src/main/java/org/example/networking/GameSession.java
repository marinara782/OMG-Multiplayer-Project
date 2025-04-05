package org.example.networking;

public class GameSession {

    private int sessionid;
    private String gameType;
    private List<Client> players;

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
        System.out.println("Broadcasting game state to "+players.size()+" players.");
        //broadcast state to the client of each player in players list
        for (Client player: players){

        }
    }
    public void waitForOpponentMove(Object o) {
        System.out.print("Waiting for opponent's move...");
        try{
            Thread.sleep(2000);
        }catch(InterruptedException e){
            System.out.println("Connection interrupted!");
    }
    }

    public String processMove(Client player, String moveData) {
        if (players.contains(player)){
            System.out.println("Processing move: " + moveData);
            return ("Move processed: " +moveData);
        }else{
            return ("Player is not part of this session!");
        }

    }

    public void sendChat(String message){
        System.out.println("Sending chat: " + message);
    }

    public void completeSession(){
        System.out.println("Complete game session...");
    }
}
