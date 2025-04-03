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
        dave.setCheckerWins(35);
        dave.setCheckerLosses(12);
        dave.setConnect4Wins(39);
        dave.setConnect4Losses(45);
        dave.setTictactoeWins(11);
        dave.setTictactoeLosses(19);
        leaderboard.addPlayer(dave);

        // add Erick
        Player erick = new Player("Erick");
        erick.setCheckerWins(43);
        erick.setCheckerLosses(56);
        erick.setConnect4Wins(86);
        erick.setConnect4Losses(14);
        erick.setTictactoeWins(56);
        erick.setTictactoeLosses(98);
        leaderboard.addPlayer(erick);

        // add Fred
        Player fred = new Player("Fred");
        fred.setCheckerWins(42);
        fred.setCheckerLosses(9);
        fred.setConnect4Wins(12);
        fred.setConnect4Losses(2);
        fred.setTictactoeWins(98);
        fred.setTictactoeLosses(45);
        leaderboard.addPlayer(fred);

        // add Gary
        Player gary = new Player("Gary");
        gary.setCheckerWins(45);
        gary.setCheckerLosses(22);
        gary.setConnect4Wins(33);
        gary.setConnect4Losses(66);
        gary.setTictactoeWins(55);
        gary.setTictactoeLosses(44);
        leaderboard.addPlayer(gary);

        // add Haley
        Player haley = new Player("Haley");
        haley.setCheckerWins(3);
        haley.setCheckerLosses(16);
        haley.setConnect4Wins(4);
        haley.setConnect4Losses(10);
        haley.setTictactoeWins(5);
        haley.setTictactoeLosses(24);
        leaderboard.addPlayer(haley);

        // add Isiah
        Player isiah = new Player("Isiah");
        isiah.setCheckerWins(34);
        isiah.setCheckerLosses(12);
        isiah.setConnect4Wins(65);
        isiah.setConnect4Losses(34);
        isiah.setTictactoeWins(90);
        isiah.setTictactoeLosses(83);
        leaderboard.addPlayer(isiah);

        // add Jack
        Player jack = new Player("Jack");
        jack.setCheckerWins(53);
        jack.setCheckerLosses(23);
        jack.setConnect4Wins(23);
        jack.setConnect4Losses(53);
        jack.setTictactoeWins(13);
        jack.setTictactoeLosses(98);
        leaderboard.addPlayer(jack);

        // add Kylie
        Player kylie = new Player("Kylie");
        kylie.setCheckerWins(2);
        kylie.setCheckerLosses(12);
        kylie.setConnect4Wins(42);
        kylie.setConnect4Losses(1);
        kylie.setTictactoeWins(41);
        kylie.setTictactoeLosses(24);
        leaderboard.addPlayer(kylie);

        // add Lebron
        Player lebron = new Player("Lebron");
        lebron.setCheckerWins(90);
        lebron.setCheckerLosses(0);
        lebron.setConnect4Wins(93);
        lebron.setConnect4Losses(0);
        lebron.setTictactoeWins(67);
        lebron.setTictactoeLosses(0);
        leaderboard.addPlayer(lebron);

        // add Mathew
        Player mathew = new Player("Mathew");
        mathew.setCheckerWins(43);
        mathew.setCheckerLosses(12);
        mathew.setConnect4Wins(23);
        mathew.setConnect4Losses(45);
        mathew.setTictactoeWins(37);
        mathew.setTictactoeLosses(85);
        leaderboard.addPlayer(mathew);

        // add Nick
        Player nick = new Player("Nick");
        nick.setCheckerWins(12);
        nick.setCheckerLosses(23);
        nick.setConnect4Wins(75);
        nick.setConnect4Losses(24);
        nick.setTictactoeWins(74);
        nick.setTictactoeLosses(25);
        leaderboard.addPlayer(nick);

        // add Obi
        Player obi = new Player("Obi");
        obi.setCheckerWins(56);
        obi.setCheckerLosses(34);
        obi.setConnect4Wins(36);
        obi.setConnect4Losses(53);
        obi.setTictactoeWins(37);
        obi.setTictactoeLosses(23);
        leaderboard.addPlayer(obi);
    }

    /**
     * Prints the top N players based on sorting criteria.
     */
    public void printTopPlayers(int amount, String sortingCriteria) {
        List<Player> topPlayers = leaderboard.getTopNPlayers(amount, sortingCriteria);
        System.out.println("\nTop " + amount + " Players by " + sortingCriteria + ":");
        for (Player p : topPlayers) {
            switch (sortingCriteria) {
                case "checkers wins":
                    System.out.println(p.getUsername() + " - " + p.getCheckerWins());
                    break;
                case "checkers losses":
                    System.out.println(p.getUsername() + " - " + p.getCheckerLosses());
                    break;
                case "tictactoe wins":
                    System.out.println(p.getUsername() + " - " + p.getTictactoeWins());
                    break;
                case "tictactoe losses":
                    System.out.println(p.getUsername() + " - " + p.getTictactoeLosses());
                    break;
                case "connect4 wins":
                    System.out.println(p.getUsername() + " - " + p.getConnect4Wins());
                    break;
                case "connect4 losses":
                    System.out.println(p.getUsername() + " - " + p.getConnect4Losses());
                    break;
                case "total wins":
                    System.out.println(p.getUsername() + " - " + p.getTotalWins());
                    break;
                case "total losses":
                    System.out.println(p.getUsername() + " - " + p.getTotalLosses());
                    break;
                case "win percentage":
                    double winPercentage = p.getWinPercentage();
                    String percentFormat = String.format("%.2f%%", winPercentage * 100);
                    System.out.println(p.getUsername() + " - " + percentFormat);
            }
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


        // Print top 5 players with most wins
        stub.printTopPlayers(5, "total wins");


        // print top 4 players with most losses
        stub.printTopPlayers(4, "total losses");

        // print top 7 players with the highest win percentage
        stub.printTopPlayers(7, "win percentage");
    }
}

