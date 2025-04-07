package org.example;

import java.util.List;

public class Player {
    private String username;
    private int checkerWins;
    private int checkerLosses;
    private int tictactoeWins;
    private int tictactoeLosses;
    private int connect4Wins;
    private int connect4Losses;

    public Player(String username) {
        this.username = username;
        this.checkerWins = 0;
        this.checkerLosses = 0;
        this.tictactoeWins = 0;
        this.tictactoeLosses = 0;
        this.connect4Wins = 0;
        this.connect4Losses = 0;
    }

    // setters for Database Stub

    public void setCheckerWins(int checkerWins) {
        this.checkerWins = checkerWins;
    }



    public void setCheckerLosses(int checkerLosses) {
        this.checkerLosses = checkerLosses;
    }



    public void setTictactoeWins(int tictactoeWins) {
        this.tictactoeWins = tictactoeWins;
    }



    public void setTictactoeLosses(int tictactoeLosses) {
        this.tictactoeLosses = tictactoeLosses;
    }



    public void setConnect4Wins(int connect4Wins) {
        this.connect4Wins = connect4Wins;
    }



    public void setConnect4Losses(int connect4Losses) {
        this.connect4Losses = connect4Losses;
    }

    // getters

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

    public int getConnect4Losses() {
        return connect4Losses;
    }

    public int getTotalWins() {return checkerWins + connect4Wins + tictactoeWins;}

    public int getTotalLosses() {return checkerLosses + connect4Losses + tictactoeLosses;}

    public void updateCheckerWins() {
        this.checkerWins += 1;
    }

    public void updateCheckerLosses() {
        this.checkerLosses += 1;
    }

    public void updateTicTacToeWins() {
        this.tictactoeWins += 1;
    }

    public void updateTictactoeLosses() {
        this.tictactoeLosses += 1;
    }

    public void updateConnect4Wins() {
        this.connect4Wins += 1;
    }

    public void updateConnect4Losses() {
        this.connect4Losses += 1;
    }
}
