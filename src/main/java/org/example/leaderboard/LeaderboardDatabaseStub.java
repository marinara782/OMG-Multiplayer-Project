package org.example.leaderboard;

import java.nio.file.attribute.AclEntry;
import java.util.ArrayList;
import java.util.List;
import org.example.Player;

import javax.swing.text.StyledEditorKit;

/**
 * Stub class for the database interface
 */
public class LeaderboardDatabaseStub  {
    private List<Player> players;

    public LeaderboardDatabaseStub(){
        players = new ArrayList<>();
    }

    // initialize Alice
    Player alice = new Player("Alice");
    alice.setCheckerWins(2);
    alice.setCheckerLosses(5);
    alice.setConnect4Wins(5);
    alice.setConnect4losses(2);
    alice.setTicTacToeWins(15);
    alice.setTicTacToeLosses(12);
    
    



    
}
/**
 * private List<Player> players;
 * 
 * public LeaderbordeDatabaseStub(){
 *    players = new ArrayList<>();
 * 
 * // initialize the players
 * Player alice = new Playyer("ALice");
 * alice.setChckersWins(2);
 * alice.setCheckersLosses(1);
 * alice.setConnect4Wins(5);
 * alice.setConnect4Losses(3);
 * alice.setTicTacToeWins(20);
 * alice.setTicTacToeLosses(10);
 * players.add(alice);
 */