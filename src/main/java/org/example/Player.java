package org.example;

public class Player {
    private String username;
    private int checkerWins;
    private int checkerLosses;
    private int tictactoeWins;
    private int tictactoeLosses;
    private int connect4Wins;
    private int conncet4Losses;

    public Player(String username) {
        this.username = username;
        this.checkerWins = 0;
        this.checkerLosses = 0;
        this.tictactoeWins = 0;
        this.tictactoeLosses = 0;
        this.connect4Wins = 0;
        this.conncet4Losses = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCheckerWins() {
        return checkerWins;
    }

    public int getCheckerLosses() {
        return checkerLosses;
    }

    public int getTictactoeWins() {
        return tictactoeWins;
    }

    public int getTictactoeLosses() {
        return tictactoeLosses;
    }

    public int getConnect4Wins() {
        return connect4Wins;
    }

    public int getConncet4Losses() {
        return conncet4Losses;
    }

    public void updateCheckerWins() {
        this.checkerWins += 1;
    }

    public void updateCheckerLosses() {
        this.checkerLosses += 1;
    }

    public void updateTicTacToeWins() {
        this.tictactoeWins += 1;
    }

    public void updateTicTacToeLosses() {
        this.tictactoeLosses += 1;
    }

    public void updateConnect4Wins() {
        this.connect4Wins += 1;
    }

    public void updateConnect4Losses() {
        this.conncet4Losses += 1;
    }
}
