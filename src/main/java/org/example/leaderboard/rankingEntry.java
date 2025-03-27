package org.example.leaderboard;

public class rankingEntry {
    private String username;
    // private int score;
    private int wins;
    private int losses;

    // constructor for ranking entry
    public rankingEntry(String username, int wins, int losses){
        this.username = username;
        this.wins = wins;
        this.losses = losses;

    }


    // username getter
    public String getUsername(){
        return username;
    }
    
    // score getter
/*    public int  getScore(){
        return score;
    }*/

    public int getWins(){
        return wins;
    }

    public int getLosses(){
        return losses;
    }
}