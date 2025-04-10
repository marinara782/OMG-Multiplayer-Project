package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Player {
    private String username;
    private int checkerWins;
    private int checkerLosses;
    private int tictactoeWins;
    private int tictactoeLosses;

    private int tictactoeDraws;
    private int connect4Wins;
    private int connect4Losses;

    private int connect4Draws;

    private int totalWins;
    private int totalLosses;
    private int totalDraws;


    private double winPercentage;

    //Jackson requires no-argument constructor (for load/save players in Server.java)
    public Player(){}


    public Player(String username) {
        this.username = username;
        this.checkerWins = 0;
        this.checkerLosses = 0;
        this.tictactoeWins = 0;
        this.tictactoeLosses = 0;
        this.tictactoeDraws = 0;
        this.connect4Wins = 0;
        this.connect4Losses = 0;
        this.connect4Draws = 0;
        this.totalDraws = 0;
    }


    public void updateTotalWins() {
        this.totalWins = this.checkerWins + this.tictactoeWins + this.connect4Wins;
    }


    public void updateTotalLosses() {
        this.totalLosses = this.checkerLosses + this.tictactoeLosses + this.connect4Losses;
    }

    public void updateTotalDraws() {
        this.totalDraws = this.connect4Draws + this.tictactoeDraws;
    }

    public void updateWinPercentage() {
        int wins = getTotalWins();
        int totalGamesPlayed = getTotalWins() + getTotalLosses() + getTotalDraws();
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

    public void setTictactoeDraws(int tictactoeDraws) {
        this.tictactoeDraws = tictactoeDraws;
        updateTotalDraws();
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

    public void setConnect4Draws(int connect4Draws) {
        this.connect4Draws = connect4Draws;
        updateTotalDraws();
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

    public double getCheckersWinPercentage() {
        int wins = getCheckerWins();
        int games = wins + getCheckerLosses();
        return (double) wins/games;
    }

    public int getTictactoeWins() {
        return tictactoeWins;
    }

    public int getTictactoeLosses() {
        return tictactoeLosses;
    }

    public int getTictactoeDraws() {return tictactoeDraws;}

    public double getTicTacToeWinPercentage() {
        int wins = getTictactoeWins();
        int games = wins + getTictactoeLosses();
        return (double) wins/games;
    }
    public int getConnect4Wins() {
        return connect4Wins;
    }

    public int getConnect4Losses() {
        return connect4Losses;
    }

    public int getConnect4Draws() {return connect4Draws;}

    public double getConnect4WinPercentage() {
        int wins = getConnect4Wins();
        int games = wins + getConnect4Losses();
        return (double) wins/games;
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

    public int getTotalDraws() {
        return totalDraws;
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

    public void updateTictactoeDraws() {
        this.tictactoeDraws += 1;
        updateTotalDraws();
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

    public void updateConnect4Draws() {
        this.connect4Draws += 1;
        updateTotalDraws();
    }
}

