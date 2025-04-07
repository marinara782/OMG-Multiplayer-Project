package org.example.matchmaking;

import java.util.*;
import org.example.Player;


/**
 * Implements a skill-based matchmaking system that matches players based on similar skill levels,
 * with dynamic tolerance adjustment to reduce wait times.
 */
public class SkillBasedMatchmaking {

    private List<Player> players; // List of all players in matchmaking pool

    /**
     * Constructs a matchmaking system.
     * @param baseTolerance Initial skill difference allowed between players.
     * @param maxWaitTimeSeconds Max time before expanding the tolerance (in seconds).
     * @param toleranceIncrement Amount to expand tolerance after wait time.
     */
    public SkillBasedMatchmaking(int baseTolerance, int maxWaitTimeSeconds, int toleranceIncrement) {
        this.players = new ArrayList<>(players); //List of players


    }

    /**
     * Adds a new player to the matchmaking pool.
     * @param id Unique player identifier
     * @param skillLevel Player's skill level.
     */
    public void addPlayer(String id, double skillLevel) {
        Player player = new Player(id);
        skillLevel = player.getWinPercentage();
        players.add(player);
    }

    /**
     * Attempts to match players in the pool based on skill similarity.
     * Tolerance increases the longer a player waits.
     * @return A list of matched player ID pairs.
     */
    public List<String[]> findMatches() {

    }

    /**
     * Gets a list of players who have been waiting too long and may need to be notified or handled specially.
     * @return List of player IDs who are waiting beyond double the max wait time.
     */
    //public List<String> getUnmatchedPlayers(){

    //}
}





