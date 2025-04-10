package org.example.matchmaking;

import java.util.*;
import org.example.Player;


/**
 * Implements a skill-based matchmaking system that matches players based on similar skill levels,
 * with dynamic tolerance adjustment to reduce wait times.
 */
public class SkillBasedMatchmaking {

    private List<QueuedPlayer> players; // List of all players waiting to be matched up
    private double baseTolerance;
    private int maxWaitTimeSeconds;
    private double toleranceIncrement;

    /**
     * Constructs a matchmaking system.
     *
     * @param baseTolerance      Initial skill difference allowed between players.
     * @param maxWaitTimeSeconds Max time before expanding the tolerance (in seconds).
     * @param toleranceIncrement Amount to expand tolerance after wait time.
     */
    public SkillBasedMatchmaking(double baseTolerance, int maxWaitTimeSeconds, double toleranceIncrement) {
        this.players = new ArrayList<>(); //Match pool
        this.baseTolerance = baseTolerance;
        this.maxWaitTimeSeconds = maxWaitTimeSeconds;
        this.toleranceIncrement = toleranceIncrement;
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
     * Adds a new player to the matchmaking pool. We need to grab the time they joined the pool.
     *
     * @param player The player that is to be added
     */
    public void addPlayer(Player player) {

        for (QueuedPlayer qp : players) { //this loop essentially prevents the same player from queuing with themself
            if (qp.player == player) return; // Player already in queue — skip
        }

        long matchTime = System.currentTimeMillis();
        QueuedPlayer queuedPlayer = new QueuedPlayer(player, matchTime);
        players.add(queuedPlayer);
    }

    /**
     * Attempts to match players in the pool based on skill similarity.
     * Tolerance increases the longer players wait.
     *
     * @return A list of matched player ID pairs.
     */
    public List<String[]> findMatches() {
        List<String[]> matchedPlayers = new ArrayList<>(); //list of players who have been matched

        while (players.size() >= 2) { //while there are at least two people looking for a match
            QueuedPlayer p1 = players.get(0); //grab the first player
            QueuedPlayer p2 = null; //the second player will be found through a loop
            boolean matchFound = false; //this is set to false until a match is found as its name and value suggests

            for (int i = 1; i < players.size(); i++) { //loop through available players
                QueuedPlayer candidate = players.get(i); //grab the player we are at in the loop (i)

                double p1Skill = p1.player.getWinPercentage(); //grab the first players skill level (win percentage)
                double p2Skill = candidate.player.getWinPercentage(); //grab the second players skill level (win percentage)


                int p1SkillInt = (int) (p1Skill * 100); // Convert skill levels to percentages so they are easier to work with
                int p2SkillInt = (int) (p2Skill * 100); // Convert skill levels to percentages so they are easier to work with
                int diff = Math.abs(p1SkillInt - p2SkillInt); //the difference is what will determine if they can play together or not


                if (p1.player == candidate.player) { //if the player is attempting to some how match with themself
                    continue; // skip matching with self
                }

                if (diff <= baseTolerance * 100) { //if the difference is less than or equal to the base tolerance we allow
                    p2 = candidate; //the player we at can match with the current first player
                    matchedPlayers.add(new String[]{p1.player.getUsername(), p2.player.getUsername()}); //add them both to matched players
                    matchFound = true; // a match has been found!
                    break;
                }
            }

            if (matchFound && p2 != null) { //if a match was found ...
                players.remove(p1); //...remove p1 and p2 from the match pool
                players.remove(p2);
            } else {
                // no match was found , so check wait time and increase tolerance
                long timeWaitedP1 = System.currentTimeMillis() - p1.joinTime; //first we check p1s time waited
                if (timeWaitedP1 >= maxWaitTimeSeconds * 1000L) { //if it exceeds maxwaittime
                    baseTolerance += toleranceIncrement; //increase tolerance
                }
                // Move p1 to the back to retry AFTER other players
                players.remove(p1);
                players.add(p1);
            }
        }

        return matchedPlayers;
    }




    /**
     * Gets a list of players who have been waiting too long and may need to be notified or handled specially.
     * @return List of player IDs who are waiting beyond double the max wait time.
     */
    public List<String> getUnmatchedPlayers(){
        List<String> unMatchedPlayers = new ArrayList<>(); //This is our list of unmatched Players that we will return

        for (int i = 0; i < players.size(); i++) { //Iterate through remaining players
            QueuedPlayer currentPlayer = players.get(i); //Grab the current player
            long waitTime = System.currentTimeMillis() - currentPlayer.joinTime; //Grab the current wait time of the current player
            if (waitTime >= (2L * maxWaitTimeSeconds)) { //If the current players been waiting for more than double the max wait time,
                unMatchedPlayers.add(currentPlayer.player.getUsername()); //Add the current player to the unMatchedPlayers list
            }
        }
        return unMatchedPlayers;
    }

}




