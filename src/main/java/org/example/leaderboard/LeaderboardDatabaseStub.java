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

        // add Dave
        Player dave = new Player("Dave");
        charlie.setCheckerWins(35);
        charlie.setCheckerLosses(12);
        charlie.setConnect4Wins(39);
        charlie.setConnect4Losses(45);
        charlie.setTictactoeWins(11);
        charlie.setTictactoeLosses(19);
        leaderboard.addPlayer(dave);

        // add Erick
        Player erick = new Player("Erick");
        charlie.setCheckerWins(43);
        charlie.setCheckerLosses(56);
        charlie.setConnect4Wins(86);
        charlie.setConnect4Losses(14);
        charlie.setTictactoeWins(56);
        charlie.setTictactoeLosses(98);
        leaderboard.addPlayer(erick);

        // add Fred
        Player fred = new Player("Fred");
        charlie.setCheckerWins(42);
        charlie.setCheckerLosses(9);
        charlie.setConnect4Wins(12);
        charlie.setConnect4Losses(2);
        charlie.setTictactoeWins(98);
        charlie.setTictactoeLosses(45);
        leaderboard.addPlayer(fred);

        // add Gary
        Player gary = new Player("Gary");
        charlie.setCheckerWins(45);
        charlie.setCheckerLosses(22);
        charlie.setConnect4Wins(33);
        charlie.setConnect4Losses(66);
        charlie.setTictactoeWins(55);
        charlie.setTictactoeLosses(44);
        leaderboard.addPlayer(gary);

        // add Haley
        Player haley = new Player("Haley");
        charlie.setCheckerWins(3);
        charlie.setCheckerLosses(16);
        charlie.setConnect4Wins(4);
        charlie.setConnect4Losses(10);
        charlie.setTictactoeWins(5);
        charlie.setTictactoeLosses(24);
        leaderboard.addPlayer(haley);

        // add Isiah
        Player isiah = new Player("Isiah");
        charlie.setCheckerWins(34);
        charlie.setCheckerLosses(12);
        charlie.setConnect4Wins(65);
        charlie.setConnect4Losses(34);
        charlie.setTictactoeWins(90);
        charlie.setTictactoeLosses(83);
        leaderboard.addPlayer(isiah);

        // add Jack
        Player jack = new Player("Jack");
        charlie.setCheckerWins(53);
        charlie.setCheckerLosses(23);
        charlie.setConnect4Wins(23);
        charlie.setConnect4Losses(53);
        charlie.setTictactoeWins(13);
        charlie.setTictactoeLosses(98);
        leaderboard.addPlayer(jack);

        // add Kylie
        Player kylie = new Player("Kylie");
        charlie.setCheckerWins(2);
        charlie.setCheckerLosses(12);
        charlie.setConnect4Wins(42);
        charlie.setConnect4Losses(1);
        charlie.setTictactoeWins(41);
        charlie.setTictactoeLosses(24);
        leaderboard.addPlayer(kylie);

        // add Lebron
        Player lebron = new Player("Lebron");
        charlie.setCheckerWins(84);
        charlie.setCheckerLosses(1);
        charlie.setConnect4Wins(23);
        charlie.setConnect4Losses(12);
        charlie.setTictactoeWins(24);
        charlie.setTictactoeLosses(17);
        leaderboard.addPlayer(lebron);

        // add Mathew
        Player mathew = new Player("Mathew");
        charlie.setCheckerWins(43);
        charlie.setCheckerLosses(12);
        charlie.setConnect4Wins(23);
        charlie.setConnect4Losses(45);
        charlie.setTictactoeWins(37);
        charlie.setTictactoeLosses(85);
        leaderboard.addPlayer(mathew);

        // add Nick
        Player nick = new Player("Nick");
        charlie.setCheckerWins(12);
        charlie.setCheckerLosses(23);
        charlie.setConnect4Wins(75);
        charlie.setConnect4Losses(24);
        charlie.setTictactoeWins(74);
        charlie.setTictactoeLosses(25);
        leaderboard.addPlayer(nick);

        // add Obi
        Player obi = new Player("Obi");
        charlie.setCheckerWins(56);
        charlie.setCheckerLosses(34);
        charlie.setConnect4Wins(36);
        charlie.setConnect4Losses(53);
        charlie.setTictactoeWins(37);
        charlie.setTictactoeLosses(23);
        leaderboard.addPlayer(obi);
    }

    /**
     * Prints the top N players based on sorting criteria.
     */
    public void printTopPlayers(int amount, String sortingCriteria) {
        List<Player> topPlayers = leaderboard.getTopNPlayers(amount, sortingCriteria);
        System.out.println("\nTop " + amount + " Players by " + sortingCriteria + ":");
        for (Player p : topPlayers) {
            System.out.println(p.getUsername() + " - " + sortingCriteria);
        }
    }

    public static void main(String[] args) {
        LeaderboardDatabaseStub stub = new LeaderboardDatabaseStub();

        // Print top 4 players by Checkers Wins
        stub.printTopPlayers(4, "checkers wins");

        // Print top 6 players by Tic-Tac-Toe Losses
        stub.printTopPlayers(6, "tictactoe losses");

        // Print top 7 players by Connect4 Wins
        stub.printTopPlayers(7, "connect4 wins");

        // Print top 10 players by Checkers Losses
        stub.printTopPlayers(10, "checkers losses");

        // Print top 3 players by Tic-Tac-Toe wins
        stub.printTopPlayers(3, "tictactoe wins");

        // Print top 5 players by connect4 Losses
        stub.printTopPlayers(5, "connect4 losses");
    }
}
