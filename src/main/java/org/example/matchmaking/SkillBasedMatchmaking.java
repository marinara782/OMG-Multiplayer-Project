package org.example.matchmaking;

import java.util.*;
import org.example.Player;


/**
 * Implements a skill-based matchmaking system that matches players based on similar skill levels,
 * with dynamic tolerance adjustment to reduce wait times.
 */
public class SkillBasedMatchmaking {

    private List<QueuedPlayer> players; // List of all players waiting to be matched up

    /**
     * Constructs a matchmaking system.
     * @param baseTolerance Initial skill difference allowed between players.
     * @param maxWaitTimeSeconds Max time before expanding the tolerance (in seconds).
     * @param toleranceIncrement Amount to expand tolerance after wait time.
     */
    public SkillBasedMatchmaking(int baseTolerance, int maxWaitTimeSeconds, int toleranceIncrement) {
        this.players = new ArrayList<>(players); //Match pool
    }

    /**
     * Allows us to create and access the time a player joins the match pool (joinTime)
     * without modifying the Player.java class unnecessarily (no other classes we have made
     * will utilize it).
     */
    private static class QueuedPlayer {
        private Player player;
        private long joinTime; //this is when the player joined the match pool; will be set to System.currentTimeMillis()
        private QueuedPlayer(Player player, long joinTime) {
            this.player = player;
            this.joinTime = joinTime;
        }
    }


    /**
     * Adds a new player to the matchmaking pool.
     * @param player The player that will is to be added
     */
    public void addPlayer(Player player) {
        long matchTime = System.currentTimeMillis();
        addedPlayer = new QueuedPlayer(player, matchTime);
        players.add(addedPlayer);
    }

    /**
     * Attempts to match players in the pool based on skill similarity.
     * Tolerance increases the longer a player waits.
     * @return A list of matched player ID pairs.
     */
    //public List<String[]> findMatches() {

    }

    /**
     * Gets a list of players who have been waiting too long and may need to be notified or handled specially.
     * @return List of player IDs who are waiting beyond double the max wait time.
     */
    //public List<String> getUnmatchedPlayers(){

    //}






