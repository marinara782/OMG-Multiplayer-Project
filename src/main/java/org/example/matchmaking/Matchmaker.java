package org.example.matchmaking;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import org.example.Player;

public class Matchmaker {

    private List<Player> players; // List of all players in matchmaking pool
    private List<Player> topPlayers; // Leaderboard containing top-ranked players

    public Matchmaker() {
        this.players = new ArrayList<>();
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
            if (!opponent.equals(player)) { // Ensures that we don't match a player with themselves
                int skillDifference = Math.abs(player.getSkillLevel() - opponent.getSkillLevel());

                // Choose the opponent with the closest skill level
                if (skillDifference < minSkillDifference) {
                    minSkillDifference = skillDifference;
                    bestMatch = opponent;
                }
            }
        }

        return bestMatch; // Return the best match found, or null if no match is available.
    }

    /**
     * Displays the current matchmaking queue.
     */
    public void showQueue() {

    }

    /**
     * Adds a player to the matchmaking queue.
     * @param player The player to be added to the queue.
     */
    public void joinQueue(Player player) {

    }

    /**
     * Matches players based on their skill level and sends them to the game lobby.
     */
    public void matchPlayers() {

    }

    /**
     * @param winner The player who won the match.
     * @param loser The player who lost the match.
     */

    public void updateLeaderboard() {

    }

    /**
     * Simulates matchmaking by pairing all players in the queue without modifying the list.
     * This is useful for testing without affecting the actual matchmaking queue.
     */
    public void simulateMatchmaking() {
        for (Player player : players) {
            Player opponent = findMatch(player);
            if (opponent != null) {
                System.out.println(player.getName() + " matched with " + opponent.getName());
            } else {
                System.out.println(player.getName() + " is waiting for a match.");
            }
        }
    }

    /**
     * Simulates the outcome of a match between two players.
     * Randomly determines a winner and updates the leaderboard accordingly.
     * @param player1 The first player in the match.
     * @param player2 The second player in the match.
     */
    private void simulateMatchResult(Player player1, Player player2) {

    }

   /**
    * Retrieves the top players on the leaderboard.
    * @return A list of the top players.
    */

    public List<Player> getTopPlayers() {
        return new ArrayList<>(topPlayers); // Return a copy to prevent modification
    }
}
