package org.example.matchmaking;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Matchmaker {
    /*
    private List<Player> players; // List of all players in matchmaking pool
    private List<Player> topPlayers; // Leaderboard containing top-ranked players

    public Matchmaker() {
        this.players = new ArrayList<>();
        this.topPlayers = new ArrayList<>();
    }

    // Simulates finding a match for a player based on skill level.
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

    // Updates the leaderboard with the top 10 players based on skill level
    public void updateLeaderboard() {
        // Sort players by skill level in descending order
        players.sort((p1,p2) -> Integer.compare(p2.getSkillLevel(), p1.getSkillLevel()));

        // Update the top players list (only keep the top 10)
        topPlayers = new ArrayList<>(players.subList(0, Math.min(10, players.size())));
    }

    // Retrieves the top players on the leaderboard
    public List<Player> getTopPlayers() {
        return new ArrayList<>(topPlayers); // Return a copy to prevent modification
    }

    // Simulates matchmaking by pairing all players in the queue
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
     */
}
