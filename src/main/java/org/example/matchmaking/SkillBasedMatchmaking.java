package org.example.matchmaking;

import java.util.*;
import org.example.Player;


/**
 * Implements a skill-based matchmaking system that matches players based on similar skill levels,
 * with dynamic tolerance adjustment to reduce wait times.
 */
public class SkillBasedMatchmaking {

    private List<QueuedPlayer> players; // List of all players waiting to be matched up
    private int baseTolerance;
    private int maxWaitTimeSeconds;
    private int toleranceIncrement;

    /**
     * Constructs a matchmaking system.
     *
     * @param baseTolerance      Initial skill difference allowed between players.
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

        //This is a constructor so we can call it in the addPlayer method
        private QueuedPlayer(Player player, long joinTime) {
            this.player = player;
            this.joinTime = joinTime;
        }
    }


    /**
     * Adds a new player to the matchmaking pool.
     *
     * @param player The player that is to be added
     */
    public void addPlayer(Player player) {
        long matchTime = System.currentTimeMillis();
        QueuedPlayer queuedPlayer = new QueuedPlayer(player, matchTime);
        players.add(queuedPlayer);
    }

    /**
     * Attempts to match players in the pool based on skill similarity.
     * Tolerance increases the longer a player waits.
     *
     * @return A list of matched player ID pairs.
     */
    public List<String[]> findMatches() {
        List<String[]> matchedPlayers = new ArrayList<>(); //This is our list of matched Players that we will return

        while (players.size() >= 2) { //While we have at least 2 people looking for a match
            QueuedPlayer p1 = players.get(0); //Gets the first player in the queue
            QueuedPlayer p2 = null; //Null at first until we find an appropriate partner

            for (int i = 0; i < players.size(); i++) { //For loop to iterate through queue to find an appropriate player to match with (p2)
                if (players.get(i) != p1) { //We can't match the player with themselves
                    double p1Skill = p1.player.getWinPercentage(); //Grab their skill levels
                    double p2Skill = players.get(i).player.getWinPercentage(); //Grab their skill levels

                    if (Math.abs(p1Skill - p2Skill) <= baseTolerance) { //if the skill levels fit within the tolerance limits
                        p2 = players.get(i); //Then p2 is whatever player we are at right now
                        matchedPlayers.add(new String[]{p1.player.toString(), p2.player.toString()});
                        players.remove(p2); //remove them from the match pool
                        players.remove(p1); //remove them from the match pool
                    }

                    else { //If it is not less than the baseTolerance
                        baseTolerance += toleranceIncrement; //Increase the tolerance limit so it's easier to find a match
                    }
                }

            }

        }
        return matchedPlayers;
    }



    /**
     * Gets a list of players who have been waiting too long and may need to be notified or handled specially.
     * @return List of player IDs who are waiting beyond double the max wait time.
     */
    public List<String> getUnmatchedPlayers(){

    }

}




