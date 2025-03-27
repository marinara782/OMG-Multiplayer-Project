package org.example.leaderboard;

import java.util.ArrayList;
import java.util.List;

import org.example.Player;


/**
 * Stub class for the database interface
 */
public class LeaderboardDatabaseStub  {
    private List<Player> players;

    public LeaderboardDatabaseStub(){
        players = new ArrayList<>();
        initializePlayers();
    }

    public void initializePlayers(){
    // add Alice    
        Player alice = new Player("Alice");
        alice.setCheckerWins(2);
        alice.setCheckerLosses(5);
        alice.setConnect4Wins(5);
        alice.setConnect4Losses(2);
        alice.setTictactoeWins(15);
        alice.setTictactoeLosses(12);
        players.add(alice);

        // add Bob
        Player bob = new Player("Bob");
        bob.setCheckerWins(3);
        bob.setCheckerLosses(4);
        bob.setConnect4Wins(4);
        bob.setConnect4Losses(3);
        bob.setTictactoeWins(10);
        bob.setTictactoeLosses(15);
        players.add(bob);

        // add Charlie
        Player charlie = new Player("Charlie");
        charlie.setCheckerWins(7);
        charlie.setCheckerLosses(10);
        charlie.setConnect4Wins(16);
        charlie.setConnect4Losses(10);
        charlie.setTictactoeWins(13);
        charlie.setTictactoeLosses(7);
        players.add(charlie);
    }
    



    
}
