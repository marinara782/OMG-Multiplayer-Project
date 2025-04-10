package org.example.matchmaking;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import org.example.Player;

// Try and look over the related problem later.
public class Matchmaker {

    /**
     * Enum for supported games in the matchmaking system.
     */
    public enum GameType {
        CHECKERS,
        TICTACTOE,
        CONNECT4
    }

    private final List<Player> players; // List of all players in matchmaking pool
    private final List<Player> topPlayers; // Leaderboard containing top-ranked players

    public Matchmaker() {
        this.players = new ArrayList<>();
        this.topPlayers = new ArrayList<>();
    }


    /**
     * Adds a player to the matchmaking pool.
     * This method allows players to the matchmaking queue.
     * @param player The player to be added to the matchmaking pool.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }



    /**
     * Finds the best possible match for a player based on skill level.
     * @param player The player looking for a match.
     * @return The best-matched opponent, or null if no suitable match is found.
     */
    public Player findMatch(Player player, GameType gameType) {
        Player bestMatch = null;
        double minDifference = Double.MAX_VALUE;

        for (Player opponent : players) {
            if (!opponent.equals(player)) {
                // Get win percentages for both players and compare
                double playerStat = getWinStat(player, gameType);
                double opponentStat = getWinStat(opponent, gameType);
                double difference = Math.abs(playerStat - opponentStat);


                if (difference < minDifference) {
                    minDifference = difference;
                    bestMatch = opponent;
                }
            }
        }
        return bestMatch;
    }

    /**
     * Helper method to retrieve the correct win percentage for a player based on the game type.
     */
    private double getWinStat(Player player, GameType gameType) {
        switch (gameType) {
            case CHECKERS:
                return player.getCheckersWinPercentage();
            case TICTACTOE:
                return player.getTicTacToeWinPercentage();
            case CONNECT4:
                return player.getConnect4WinPercentage();
            default:
                return 0;
        }
    }

    /**
     * Displays the current matchmaking queue.
     */
    public void showQueue() {
        if (players.isEmpty()) {
            System.out.println("The matchmaking queue is empty.");
        } else {
            System.out.println("Current matchmaking queue:");
            for (Player player : players) {
                System.out.println(player.getUsername() + " (Checkers Wins: " + player.getCheckerWins() + ")");
            }
        }
    }

    /**
     * Matches players based on their checkers skill level and sends them to the game lobby.
     *
     * @param gameType The game type to match players for.
     */
    public void matchPlayers(GameType gameType) {
        List<Player> playersToMatch = new ArrayList<>(players);
        while (playersToMatch.size() >= 2) {
            Player player1 = playersToMatch.remove(0);
            Player player2 = findMatch(player1, gameType);

            if (player2 != null) {
                playersToMatch.remove(player2);
                System.out.println(player1.getUsername() + " matched with " + player2.getUsername());
                simulateMatchResult(player1, player2, gameType);
            } else {
                System.out.println(player1.getUsername() + " is waiting for a match.");
            }
        }
    }

    /**
     * Updates the leaderboard with the new winner.
     * @param winner The player who won the match.
     * @param loser The player who lost the match.
     */
    public void updateLeaderboard(Player winner, Player loser, GameType gameType) {
        // Update only the relevant game-specific stats
        switch (gameType) {
            case CHECKERS:
                winner.updateCheckerWins();
                loser.updateCheckerLosses();
                break;
            case TICTACTOE:
                winner.updateTicTacToeWins();
                loser.updateTictactoeLosses();
                break;
            case CONNECT4:
                winner.updateConnect4Wins();
                loser.updateConnect4Losses();
                break;
        }

        // Add players to the leaderboard
        topPlayers.add(winner);
        topPlayers.add(loser);

        // Sort players by number of wins in descending order
        topPlayers.sort((p1, p2) -> Integer.compare(p2.getTotalWins(), p1.getTotalWins()));

        // You may want to remove duplicates or ensure unique players in the leaderboard
        Set<Player> uniqueTopPlayers = new LinkedHashSet<>(topPlayers);  // Set removes duplicates
        topPlayers.clear();
        topPlayers.addAll(uniqueTopPlayers);
    }

    /**
     * Simulates the outcome of a match between two players.
     * Randomly determines a winner and updates their stats.
     * @param player1 The first player in the match.
     * @param player2 The second player in the match.
     * @param gameType The game type being player.
     */
    private void simulateMatchResult(Player player1, Player player2, GameType gameType) {
        Player winner = ThreadLocalRandom.current().nextBoolean() ? player1 : player2;
        Player loser = (winner.equals(player1)) ? player2 : player1;

        // Update only the relevant game stats
        switch (gameType) {
            case CHECKERS:
                winner.updateCheckerWins();
                loser.updateCheckerLosses();
                break;
            case TICTACTOE:
                winner.updateTicTacToeWins();
                loser.updateTictactoeLosses();
                break;
            case CONNECT4:
                winner.updateConnect4Wins();
                loser.updateConnect4Losses();
                break;
        }

        updateLeaderboard(winner, loser, gameType);
        System.out.println(winner.getUsername() + " won against " + loser.getUsername());
    }

    /**
     * Retrieves the players currently in the matchmaking queue.
     * @return A list of players in the matchmaking queue.
     */
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }


    /**
     * Retrieves the top players on the leaderboard.
     * @return A list of the top players.
     */
    public List<Player> getTopPlayers() {

        return new ArrayList<>(topPlayers);
    }
}