package org.example.matchmaking;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import org.example.Player;

public class Matchmaker {
    private List<Player> players; // List of all players in matchmaking pool
    private List<Player> topPlayers; // Leaderboard containing top-ranked players

    public Matchmaker(List<Player> players) {
        this.players = new ArrayList<>(players);
        this.topPlayers = new ArrayList<>();
    }

    /**
     * Finds the best possible match for a player based on skill level.
     * @param player The player looking for a match.
     * @return The best-matched opponent, or null if no suitable match is found.
     */
    public Player findMatch(Player player) {
        Player bestMatch = null;
        int minSkillDifference = Integer.MAX_VALUE;

        for (Player opponent : players) {
            if (!opponent.equals(player)) {
                int skillDifference = Math.abs(player.getCheckerWins() - opponent.getCheckerWins());
                if (skillDifference < minSkillDifference) {
                    minSkillDifference = skillDifference;
                    bestMatch = opponent;
                }
            }
        }
        return bestMatch;
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
     */
    public void matchPlayers() {
        List<Player> playersToMatch = new ArrayList<>(players);
        while (playersToMatch.size() >= 2) {
            Player player1 = playersToMatch.remove(0);
            Player player2 = findMatch(player1);
            if (player2 != null) {
                playersToMatch.remove(player2);
                System.out.println(player1.getUsername() + " matched with " + player2.getUsername());
                simulateMatchResult(player1, player2);
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
    public void updateLeaderboard(Player winner, Player loser) {
        if (!topPlayers.contains(winner)) {
            topPlayers.add(winner);
        }
        topPlayers.sort((p1, p2) -> Integer.compare(p2.getCheckerWins(), p1.getCheckerWins()));
        if (topPlayers.size() > 5) {
            topPlayers.remove(topPlayers.size() - 1);
        }
    }

    /**
     * Simulates the outcome of a match between two players.
     * Randomly determines a winner and updates their stats.
     * @param player1 The first player in the match.
     * @param player2 The second player in the match.
     */
    private void simulateMatchResult(Player player1, Player player2) {
        Player winner = ThreadLocalRandom.current().nextBoolean() ? player1 : player2;
        Player loser = (winner.equals(player1)) ? player2 : player1;

        winner.updateCheckerWins();
        loser.updateCheckerLosses();

        updateLeaderboard(winner, loser);
        System.out.println(winner.getUsername() + " won against " + loser.getUsername());
    }

    /**
     * Retrieves the top players on the leaderboard.
     * @return A list of the top players.
     */
    public List<Player> getTopPlayers() {
        return new ArrayList<>(topPlayers);
    }
}