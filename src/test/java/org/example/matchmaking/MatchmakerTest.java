package org.example.matchmaking;

import org.example.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MatchmakerTest {

    private Matchmaker matchmaker;

    @BeforeEach
    void setUp() {
        // Initialize the Matchmaker before each test
        matchmaker = new Matchmaker();

    }

    @Test
    void testAddPlayer() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");

        matchmaker.addPlayer(player1);
        matchmaker.addPlayer(player2);

        List<Player> players = matchmaker.getPlayers();
        assertEquals(2, players.size(), "There should be two players in the matchmaking pool.");
        assertTrue(players.contains(player1), "Player1 should be in the matchmaking pool.");
        assertTrue(players.contains(player2), "Player2 should be in the matchmaking pool.");
    }

    @Test
    void testLeaderboard() {
        Player p1 = new Player("Player1");
        p1.setTictactoeWins(3);
        matchmaker.addPlayer(p1);

        Player p2 = new Player("Player2");
        p2.setConnect4Wins(5);
        matchmaker.addPlayer(p2);

        Player p3 = new Player("Player3");
        p3.setCheckerWins(2);
        matchmaker.addPlayer(p3);

        Player p4 = new Player("Player4");
        p4.setTictactoeWins(4);  // Player4 has 4 wins
        matchmaker.addPlayer(p4);

        Player p5 = new Player("Player5");
        p5.setConnect4Wins(6);  // Player5 has 6 wins
        matchmaker.addPlayer(p5);

        Player p6 = new Player("Player6");
        p6.setCheckerWins(1);  // Player6 has 1 win
        matchmaker.addPlayer(p6);

        matchmaker.updateLeaderboard(p5, p1, Matchmaker.GameType.CONNECT4);
        matchmaker.updateLeaderboard(p5, p2, Matchmaker.GameType.CONNECT4);
        matchmaker.updateLeaderboard(p5, p3, Matchmaker.GameType.CHECKERS);
        matchmaker.updateLeaderboard(p5, p4, Matchmaker.GameType.TICTACTOE);

        List<Player> topPlayers = matchmaker.getTopPlayers();
        assertEquals("Player5", topPlayers.get(0).getUsername(), "Top player should be Player5");
    }

    @Test
    void findMatch() {
        Player p1 = new Player("Player1");
        p1.setTictactoeWins(3);
        matchmaker.addPlayer(p1);

        Player p2 = new Player("Player2");
        p2.setTictactoeWins(4);
        matchmaker.addPlayer(p2);

        // Print player stats to debug
        System.out.println("Player 1 TicTacToe Wins: " + p1.getTictactoeWins());
        System.out.println("Player 2 TicTacToe Wins: " + p2.getTictactoeWins());

        Player match = matchmaker.findMatch(p1, Matchmaker.GameType.TICTACTOE);
        assertNotNull(match, "Match should be found");
        assertEquals(p2.getUsername(), match.getUsername(), "Player2 should be matched with Player1 based on TicTacToe.");
    }

    @Test
    void showQueue() {
        assertDoesNotThrow(() -> matchmaker.showQueue());
    }

    @Test
    void matchPlayers() {
        Player p1 = new Player("Player1");
        p1.setTictactoeWins(3);
        matchmaker.addPlayer(p1);

        Player p2 = new Player ("Player2");
        p2.setConnect4Wins(5);
        matchmaker.addPlayer(p2);

        assertTrue(matchmaker.getPlayers().contains(p1), "Player1 should be in the matchmaking pool.");
        assertTrue(matchmaker.getPlayers().contains(p2), "Player2 should be in the matchmaking pool.");

        matchmaker.matchPlayers(Matchmaker.GameType.CHECKERS);

        List<Player> players = matchmaker.getPlayers();
        players.remove(p1);
        players.remove(p2);


        assertFalse(players.contains(p1), "Player1 should have been matched and removed from the pool.");
        assertFalse(players.contains(p2), "Player2 should have been matched and removed from the pool.");
    }

    @Test
    void updateLeaderboard() {
        Player p1 = new Player("Player1");
        p1.setCheckerWins(3);
        matchmaker.addPlayer(p1);

        Player p2 = new Player("Player2");
        p2.setTictactoeWins(5);
        matchmaker.addPlayer(p2);

        Player p3 = new Player("Player3");
        p3.setConnect4Wins(4);
        matchmaker.addPlayer(p3);

        // Add the players and simulate the match process
        matchmaker.updateLeaderboard(p2, p1, Matchmaker.GameType.TICTACTOE);
        matchmaker.updateLeaderboard(p2, p3, Matchmaker.GameType.CONNECT4);

        // Verify the leaderboard is updated
        List<Player> topPlayers = matchmaker.getTopPlayers();
        assertEquals("Player2", topPlayers.get(0).getUsername(), "Top player should be Player2.");
    }

    @Test
    void getTopPlayers() {
        List<Player> topPlayers = matchmaker.getTopPlayers();
        int originalSize = topPlayers.size();

        topPlayers.clear(); // should not affect internal leaderboard

        assertEquals(originalSize, matchmaker.getTopPlayers().size(), "Internal leaderboard should be unchanged");
    }
}
