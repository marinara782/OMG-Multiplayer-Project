package org.example;

public class Player {
    private String username;
    private int checkerWins;
    private int checkerLosses;
    private int tictactoeWins;
    private int tictactoeLosses;
    private int connect4Wins;
    private int connect4Losses;

    private int totalWins;
    private int totalLosses;

    private double winPercentage;

    public Player(String username) {
        this.username = username;
        this.checkerWins = 0;
        this.checkerLosses = 0;
        this.tictactoeWins = 0;
        this.tictactoeLosses = 0;
        this.connect4Wins = 0;
        this.connect4Losses = 0;
    }

    public void updateTotalWins() {
        this.totalWins = this.checkerWins + this.tictactoeWins + this.connect4Wins;
    }

    public void updateTotalLosses() {
        this.totalLosses = this.checkerLosses + this.tictactoeLosses + this.connect4Losses;
    }

    public void updateWinPercentage() {
        int wins = getTotalWins();
        int totalGamesPlayed = getTotalWins() + getTotalLosses();
        this.winPercentage = (double) wins /totalGamesPlayed;
    }

    // setters for Database Stub

    public void setCheckerWins(int checkerWins) {
        this.checkerWins = checkerWins;
        updateTotalWins();
        updateWinPercentage();
    }


    public void setCheckerLosses(int checkerLosses) {
        this.checkerLosses = checkerLosses;
        updateTotalLosses();
        updateWinPercentage();
    }



    public void setTictactoeWins(int tictactoeWins) {
        this.tictactoeWins = tictactoeWins;
        updateTotalWins();
        updateWinPercentage();
    }



    public void setTictactoeLosses(int tictactoeLosses) {
        this.tictactoeLosses = tictactoeLosses;
        updateTotalLosses();
        updateWinPercentage();
    }



    public void setConnect4Wins(int connect4Wins) {
        this.connect4Wins = connect4Wins;
        updateTotalWins();
        updateWinPercentage();
    }



    public void setConnect4Losses(int connect4Losses) {
        this.connect4Losses = connect4Losses;
        updateTotalLosses();
        updateWinPercentage();
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

    public int getTotalWins() {
        return totalWins;
    }

    public int getTotalLosses() {
        return totalLosses;
    }

    public double getWinPercentage() {
        return winPercentage;
    }

    public void updateCheckerWins() {
        this.checkerWins += 1;
        updateTotalWins();
        updateWinPercentage();
    }

    public void updateCheckerLosses() {
        this.checkerLosses += 1;
        updateTotalLosses();
        updateWinPercentage();
    }

    public void updateTicTacToeWins() {
        this.tictactoeWins += 1;
        updateTotalWins();
        updateWinPercentage();
    }

    public void updateTictactoeLosses() {
        this.tictactoeLosses += 1;
        updateTotalLosses();
        updateWinPercentage();
    }

    public void updateConnect4Wins() {
        this.connect4Wins += 1;
        updateTotalWins();
        updateWinPercentage();
    }

    public void updateConnect4Losses() {
        this.connect4Losses += 1;
        updateTotalLosses();
        updateWinPercentage();
    }
}
