package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a player in the game platform.
 * Stores statistics for multiple games (Checkers, Tic Tac Toe, Connect4)
 * and computes overall win percentage.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {
    // Player username
    private String username;

    // Checkers game statistics
    private int checkerWins;
    private int checkerLosses;
    private int checkersDraws;

    // Tic Tac Toe game statistics
    private int tictactoeWins;
    private int tictactoeLosses;
    private int tictactoeDraws;

    // Connect Four game statistics
    private int connect4Wins;
    private int connect4Losses;
    private int connect4Draws;

    // Totals for all games
    private int totalWins;
    private int totalLosses;
    private int totalDraws;

    // Overall win percentage calculated across games
    private double winPercentage;

    /**
     * No-argument constructor required by Jackson for serialization/deserialization.
     */
    public Player() {
        // Jackson needs a default constructor for loading/saving player data.
    }

    /**
     * Constructs a new Player with the given username.
     * Initializes all game-specific win/draw/loss counts to 0.
     *
     * @param username the player's username
     */
    public Player(String username) {
        this.username = username;
        // Initialize Checkers stats
        this.checkerWins = 0;
        this.checkerLosses = 0;
        this.checkersDraws = 0;

        // Initialize Tic Tac Toe stats
        this.tictactoeWins = 0;
        this.tictactoeLosses = 0;
        this.tictactoeDraws = 0;

        // Initialize Connect Four stats
        this.connect4Wins = 0;
        this.connect4Losses = 0;
        this.connect4Draws = 0;

        // Initialize total draws (other totals will be calculated)
        this.totalDraws = 0;
    }

    /**
     * Updates the total wins count by summing wins across all games.
     */
    public void updateTotalWins() {
        // Sum wins from all game types.
        this.totalWins = this.checkerWins + this.tictactoeWins + this.connect4Wins;
    }

    /**
     * Updates the total losses count by summing losses across all games.
     */
    public void updateTotalLosses() {
        // Sum losses from all game types.
        this.totalLosses = this.checkerLosses + this.tictactoeLosses + this.connect4Losses;
    }

    /**
     * Updates the total draws count by summing draws across all games.
     */
    public void updateTotalDraws() {
        // Sum draws from all game types.
        this.totalDraws = this.connect4Draws + this.tictactoeDraws + this.checkersDraws;
    }

    /**
     * Updates the overall win percentage.
     * Computes win percentage by dividing total wins by total games played.
     */
    public void updateWinPercentage() {
        int wins = getTotalWins();
        int totalGamesPlayed = getTotalWins() + getTotalLosses() + getTotalDraws();
        // Avoid division by zero.
        this.winPercentage = totalGamesPlayed > 0 ? (double) wins / totalGamesPlayed : 0;
    }

    // Getters and Setters

    /**
     * Sets the number of wins for Checkers, then updates totals and win percentage.
     *
     * @param checkerWins the new wins count for Checkers
     */
    public void setCheckerWins(int checkerWins) {
        this.checkerWins = checkerWins;
        updateTotalWins();
        updateWinPercentage();
    }

    /**
     * Sets the number of losses for Checkers, then updates totals and win percentage.
     *
     * @param checkerLosses the new losses count for Checkers
     */
    public void setCheckerLosses(int checkerLosses) {
        this.checkerLosses = checkerLosses;
        updateTotalLosses();
        updateWinPercentage();
    }

    /**
     * Sets the number of draws for Checkers, then updates totals and win percentage.
     *
     * @param checkersDraws the new draws count for Checkers
     */
    public void setCheckersDraws(int checkersDraws) {
        this.checkersDraws = checkersDraws;
        updateTotalDraws();
        updateWinPercentage();
    }

    /**
     * Sets the number of wins for Tic Tac Toe, then updates totals and win percentage.
     *
     * @param tictactoeWins the new wins count for Tic Tac Toe
     */
    public void setTictactoeWins(int tictactoeWins) {
        this.tictactoeWins = tictactoeWins;
        updateTotalWins();
        updateWinPercentage();
    }

    /**
     * Sets the number of losses for Tic Tac Toe, then updates totals and win percentage.
     *
     * @param tictactoeLosses the new losses count for Tic Tac Toe
     */
    public void setTictactoeLosses(int tictactoeLosses) {
        this.tictactoeLosses = tictactoeLosses;
        updateTotalLosses();
        updateWinPercentage();
    }

    /**
     * Sets the number of draws for Tic Tac Toe, then updates totals and win percentage.
     *
     * @param tictactoeDraws the new draws count for Tic Tac Toe
     */
    public void setTictactoeDraws(int tictactoeDraws) {
        this.tictactoeDraws = tictactoeDraws;
        updateTotalDraws();
        updateWinPercentage();
    }

    /**
     * Sets the number of wins for Connect Four, then updates totals and win percentage.
     *
     * @param connect4Wins the new wins count for Connect Four
     */
    public void setConnect4Wins(int connect4Wins) {
        this.connect4Wins = connect4Wins;
        updateTotalWins();
        updateWinPercentage();
    }

    /**
     * Sets the number of losses for Connect Four, then updates totals and win percentage.
     *
     * @param connect4Losses the new losses count for Connect Four
     */
    public void setConnect4Losses(int connect4Losses) {
        this.connect4Losses = connect4Losses;
        updateTotalLosses();
        updateWinPercentage();
    }

    /**
     * Sets the number of draws for Connect Four, then updates totals and win percentage.
     *
     * @param connect4Draws the new draws count for Connect Four
     */
    public void setConnect4Draws(int connect4Draws) {
        this.connect4Draws = connect4Draws;
        updateTotalDraws();
        updateWinPercentage();
    }

    /**
     * Returns the username of the player.
     *
     * @return the player's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the player.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the number of Checkers wins.
     *
     * @return number of wins in Checkers
     */
    public int getCheckerWins() {
        return checkerWins;
    }

    /**
     * Returns the number of Checkers losses.
     *
     * @return number of losses in Checkers
     */
    public int getCheckerLosses() {
        return checkerLosses;
    }

    /**
     * Returns the number of Checkers draws.
     *
     * @return number of draws in Checkers
     */
    public int getCheckersDraws() {
        return checkersDraws;
    }

    /**
     * Calculates and returns the win percentage for Checkers.
     *
     * @return Checkers win percentage
     */
    public double getCheckersWinPercentage() {
        int wins = getCheckerWins();
        int games = wins + getCheckerLosses();
        // Make sure to prevent division by zero.
        return games > 0 ? (double) wins / games : 0;
    }

    /**
     * Returns the number of Tic Tac Toe wins.
     *
     * @return wins in Tic Tac Toe
     */
    public int getTictactoeWins() {
        return tictactoeWins;
    }

    /**
     * Returns the number of Tic Tac Toe losses.
     *
     * @return losses in Tic Tac Toe
     */
    public int getTictactoeLosses() {
        return tictactoeLosses;
    }

    /**
     * Returns the number of Tic Tac Toe draws.
     *
     * @return draws in Tic Tac Toe
     */
    public int getTictactoeDraws() {
        return tictactoeDraws;
    }

    /**
     * Calculates and returns the win percentage for Tic Tac Toe.
     *
     * @return Tic Tac Toe win percentage
     */
    public double getTicTacToeWinPercentage() {
        int wins = getTictactoeWins();
        int games = wins + getTictactoeLosses();
        return games > 0 ? (double) wins / games : 0;
    }

    /**
     * Returns the number of Connect Four wins.
     *
     * @return wins in Connect Four
     */
    public int getConnect4Wins() {
        return connect4Wins;
    }

    /**
     * Returns the number of Connect Four losses.
     *
     * @return losses in Connect Four
     */
    public int getConnect4Losses() {
        return connect4Losses;
    }

    /**
     * Returns the number of Connect Four draws.
     *
     * @return draws in Connect Four
     */
    public int getConnect4Draws() {
        return connect4Draws;
    }

    /**
     * Calculates and returns the win percentage for Connect Four.
     *
     * @return Connect Four win percentage
     */
    public double getConnect4WinPercentage() {
        int wins = getConnect4Wins();
        int games = wins + getConnect4Losses();
        return games > 0 ? (double) wins / games : 0;
    }

    /**
     * Returns the total wins across all games.
     *
     * @return total wins count
     */
    public int getTotalWins() {
        return totalWins;
    }

    /**
     * Returns the total losses across all games.
     *
     * @return total losses count
     */
    public int getTotalLosses() {
        return totalLosses;
    }

    /**
     * Returns the overall win percentage.
     *
     * @return overall win percentage
     */
    public double getWinPercentage() {
        return winPercentage;
    }

    /**
     * Returns the total draws across all games.
     *
     * @return total draws count
     */
    public int getTotalDraws() {
        return totalDraws;
    }


    // Update methods for in-game events

    /**
     * Increments the Checkers win count by one,
     * then updates total wins and win percentage.
     */
    public void updateCheckerWins() {
        this.checkerWins += 1;
        updateTotalWins();
        updateWinPercentage();
    }

    /**
     * Increments the Checkers loss count by one,
     * then updates total losses and win percentage.
     */
    public void updateCheckerLosses() {
        this.checkerLosses += 1;
        updateTotalLosses();
        updateWinPercentage();
    }

    /**
     * Increments the Checkers draw count by one,
     * then updates total draws and win percentage.
     */
    public void updateCheckerDraws() {
        this.checkersDraws += 1;
        updateTotalDraws();
        updateWinPercentage();
    }

    /**
     * Increments the Tic Tac Toe win count by one,
     * then updates total wins and win percentage.
     */
    public void updateTicTacToeWins() {
        this.tictactoeWins += 1;
        updateTotalWins();
        updateWinPercentage();
    }

    /**
     * Increments the Tic Tac Toe loss count by one,
     * then updates total losses and win percentage.
     */
    public void updateTictactoeLosses() {
        this.tictactoeLosses += 1;
        updateTotalLosses();
        updateWinPercentage();
    }

    /**
     * Increments the Tic Tac Toe draw count by one,
     * then updates total draws.
     */
    public void updateTictactoeDraws() {
        this.tictactoeDraws += 1;
        updateTotalDraws();
    }

    /**
     * Increments the Connect Four win count by one,
     * then updates total wins and win percentage.
     */
    public void updateConnect4Wins() {
        this.connect4Wins += 1;
        updateTotalWins();
        updateWinPercentage();
    }

    /**
     * Increments the Connect Four loss count by one,
     * then updates total losses and win percentage.
     */
    public void updateConnect4Losses() {
        this.connect4Losses += 1;
        updateTotalLosses();
        updateWinPercentage();
    }

    /**
     * Increments the Connect Four draw count by one,
     * then updates total draws and win percentage.
     */
    public void updateConnect4Draws() {
        this.connect4Draws += 1;
        updateTotalDraws();
        updateWinPercentage();
    }
}


