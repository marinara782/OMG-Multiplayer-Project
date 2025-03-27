package org.example.leaderboard;

import java.util.ArrayList;
import java.util.List;

import org.example.Player;


/**
 * Stub class for the database interface
 */
public class LeaderboardDatabaseStub  {
    private Leaderboard leaderboard;

    public LeaderboardDatabaseStub(){
        leaderboard = new Leaderboard();
        initializePlayers();
    }

    public void initializePlayers() {

        // add Alice
        Player alice = new Player("Alice");
        alice.setCheckerWins(2);
        alice.setCheckerLosses(5);
        alice.setConnect4Wins(5);
        alice.setConnect4Losses(2);
        alice.setTictactoeWins(15);
        alice.setTictactoeLosses(12);
        leaderboard.addPlayer(alice);

        // add Bob
        Player bob = new Player("Bob");
        bob.setCheckerWins(3);
        bob.setCheckerLosses(4);
        bob.setConnect4Wins(4);
        bob.setConnect4Losses(3);
        bob.setTictactoeWins(10);
        bob.setTictactoeLosses(15);
        leaderboard.addPlayer(bob);

        // add Charlie
        Player charlie = new Player("Charlie");
        charlie.setCheckerWins(7);
        charlie.setCheckerLosses(10);
        charlie.setConnect4Wins(16);
        charlie.setConnect4Losses(10);
        charlie.setTictactoeWins(13);
        charlie.setTictactoeLosses(7);
        leaderboard.addPlayer(charlie);
    }

    /**
     * Get the top N players based on a sorting criterion.
     * @param amount Number of players to retrieve.
     * @param sortingCriteria Sorting criteria (e.g., "checkers wins").
     * @return List of top N players.
     */
    public List<Player> getTopPlayers(int amount, String sortingCriteria) {
        return leaderboard.getTopNPlayers(amount, sortingCriteria);
    }

    /**
     * Prints the top N players based on sorting criteria.
     */
    public void printTopPlayers(int amount, String sortingCriteria) {
        List<Player> topPlayers = getTopPlayers(amount, sortingCriteria);
        System.out.println("\nTop " + amount + " Players by " + sortingCriteria + ":");
        for (Player p : topPlayers) {
            System.out.println(p.getUsername() + " - " + sortingCriteria);
        }
    }

    public static void main(String[] args) {
        LeaderboardDatabaseStub stub = new LeaderboardDatabaseStub();

        // Print top 2 players by Checkers Wins
        stub.printTopPlayers(2, "checkers wins");

        // Print top 2 players by Tic-Tac-Toe Losses
        stub.printTopPlayers(2, "tictactoe losses");
    }
}
