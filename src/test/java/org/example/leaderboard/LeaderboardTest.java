package org.example.leaderboard;


import java.util.ArrayList;
import java.util.List;

import org.example.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderboardTest {
    private Leaderboard leaderboard;
    private Player alice, bob, charlie, dave, erick, fred;


    @BeforeEach
    void setUp() {
        leaderboard = new Leaderboard();

        alice = new Player("Alice");
        alice.setCheckerWins(2);
        alice.setCheckerLosses(5);
        alice.setConnect4Wins(5);
        alice.setConnect4Losses(1);
        alice.setTictactoeWins(15);
        alice.setTictactoeLosses(12);

        bob = new Player("Bob");
        bob.setCheckerWins(3);
        bob.setCheckerLosses(4);
        bob.setConnect4Wins(4);
        bob.setConnect4Losses(3);
        bob.setTictactoeWins(10);
        bob.setTictactoeLosses(15);

        charlie = new Player("Charlie");
        charlie.setCheckerWins(7);
        charlie.setCheckerLosses(10);
        charlie.setConnect4Wins(16);
        charlie.setConnect4Losses(10);
        charlie.setTictactoeWins(13);
        charlie.setTictactoeLosses(7);

        dave = new Player("Dave");
        dave.setCheckerWins(35);
        dave.setCheckerLosses(12);
        dave.setConnect4Wins(39);
        dave.setConnect4Losses(45);
        dave.setTictactoeWins(11);
        dave.setTictactoeLosses(19);

        erick = new Player("Erick");
        erick.setCheckerWins(43);
        erick.setCheckerLosses(56);
        erick.setConnect4Wins(86);
        erick.setConnect4Losses(14);
        erick.setTictactoeWins(56);
        erick.setTictactoeLosses(98);

        fred = new Player("Fred");
        fred.setCheckerWins(42);
        fred.setCheckerLosses(9);
        fred.setConnect4Wins(12);
        fred.setConnect4Losses(2);
        fred.setTictactoeWins(98);
        fred.setTictactoeLosses(45);

        leaderboard.addPlayer(alice);
        leaderboard.addPlayer(bob);
        leaderboard.addPlayer(charlie);
        leaderboard.addPlayer(dave);
        leaderboard.addPlayer(erick);
        leaderboard.addPlayer(fred);
    }

    @Test
    void testAddPlayer() {
        Player testPlayer = new Player("testPlayer");
        leaderboard.addPlayer(testPlayer);
        Boolean expected = true;
        Boolean actual = leaderboard.isPlayerInLeaderboard(testPlayer);
        assertEquals(expected, actual );
    }

    @Test
    void testPlayerNotInLeaderBoard() {
        Player testPlayer = new Player("testPlayer");
        Boolean expected = false;
        Boolean actual = leaderboard.isPlayerInLeaderboard(testPlayer);
        assertEquals(expected,actual);
    }

    @Test
    void testGetTop2Players_SortByCheckersWins() {
        List<Player> topPlayers = leaderboard.getTopNPlayers(2, "checkers wins");
        assertEquals(2, topPlayers.size());
        assertEquals(erick, topPlayers.get(0));
        assertEquals(fred, topPlayers.get(1));
    }


    @Test
    void testGetTop4Players_SortByCheckersLosses() {
        List<Player> topPlayers = leaderboard.getTopNPlayers(4, "checkers losses");
        assertEquals(4, topPlayers.size());
        assertEquals(erick, topPlayers.get(0));
        assertEquals(dave, topPlayers.get(1));
        assertEquals(charlie, topPlayers.get(2));
        assertEquals(fred, topPlayers.get(3));
    }


    @Test
    void testGetTop5Players_SortByConnect4Losses() {
        List<Player> topPlayers = leaderboard.getTopNPlayers(5, "connect4 losses");
        assertEquals(5, topPlayers.size());
        assertEquals(dave, topPlayers.get(0));
        assertEquals(erick, topPlayers.get(1));
        assertEquals(charlie, topPlayers.get(2));
        assertEquals(bob, topPlayers.get(3));
        assertEquals(fred, topPlayers.get(4));
    }



    @Test
    void testGetTop3Players_SortByConnect4Wins() {
        List<Player> topPlayers = leaderboard.getTopNPlayers(3, "connect4 wins");
        assertEquals(3, topPlayers.size());
        assertEquals(erick, topPlayers.get(0));
        assertEquals(dave, topPlayers.get(1));
        assertEquals(charlie, topPlayers.get(2));
    }


    @Test
    void testGetTop6Players_SortByTicTacToeWins() {
        List<Player> topPlayers = leaderboard.getTopNPlayers(6, "tictactoe wins");
        assertEquals(6, topPlayers.size());
        assertEquals(fred, topPlayers.get(0));
        assertEquals(erick, topPlayers.get(1));
        assertEquals(alice, topPlayers.get(2));
        assertEquals(charlie, topPlayers.get(3));
        assertEquals(dave, topPlayers.get(4));
        assertEquals(bob, topPlayers.get(5));
    }

    @Test
    void testGetTop4Players_SortByTicTacToeLosses() {
        List<Player> topPlayers = leaderboard.getTopNPlayers(4, "tictactoe losses");
        assertEquals(4, topPlayers.size());
        assertEquals(erick, topPlayers.get(0));
        assertEquals(fred, topPlayers.get(1));
        assertEquals(dave, topPlayers.get(2));
        assertEquals(bob, topPlayers.get(3));
    }

    @Test
    void testGetTop5Players_SortByWinPercentage() {
        List<Player> topPlayers = leaderboard.getTopNPlayers(5, "win percentage");
        assertEquals(5, topPlayers.size());
        assertEquals(fred, topPlayers.get(0));
        assertEquals(charlie, topPlayers.get(1));
        assertEquals(alice, topPlayers.get(2));
        assertEquals(dave, topPlayers.get(3));
    }

    @Test
    void testGetTop3Players_SortByTotalWins() {
        List<Player> topPlayers = leaderboard.getTopNPlayers(3, "total wins");
        assertEquals(3, topPlayers.size());
        assertEquals(erick, topPlayers.get(0));
        assertEquals(fred, topPlayers.get(1));
        assertEquals(dave, topPlayers.get(2));
    }

    @Test
    void testGetTop2Players_SortByTotalLosses() {
        List<Player> topPlayers = leaderboard.getTopNPlayers(2, "total losses");
        assertEquals(2, topPlayers.size());
        assertEquals(erick, topPlayers.get(0));
        assertEquals(dave, topPlayers.get(1));
    }

    @Test
    void testGetTop4Players_SortByCheckersWinPercentage() {
        List<Player> topPlayers = leaderboard.getTopNPlayers(4, "checkers percentage");
        assertEquals(4, topPlayers.size());
        assertEquals(fred, topPlayers.get(0));
        assertEquals(dave, topPlayers.get(1));
        assertEquals(erick, topPlayers.get(2));
        assertEquals(bob, topPlayers.get(3));
    }

    @Test
    void testGetTop3Players_SortByTicTacToeWinPercentage() {
        List<Player> topPlayers = leaderboard.getTopNPlayers(3, "tictactoe percentage");
        assertEquals(3, topPlayers.size());
        assertEquals(fred, topPlayers.get(0));
        assertEquals(charlie, topPlayers.get(1));
        assertEquals(alice, topPlayers.get(2));
    }

    @Test
    void testGetTop5Players_SortByConnect4WinPercentage() {
        List<Player> topPlayers = leaderboard.getTopNPlayers(5, "connect4 percentage");
        assertEquals(5, topPlayers.size());
        assertEquals(erick, topPlayers.get(0));
        assertEquals(fred, topPlayers.get(1));
        assertEquals(alice, topPlayers.get(2));
        assertEquals(charlie, topPlayers.get(3));
        assertEquals(bob, topPlayers.get(4));
    }

    @Test
    void testInvalidSortingCriteria() {
        assertThrows(IllegalArgumentException.class, () -> leaderboard.getTopNPlayers(2, "checkers draws"));
    }

    @Test
    void testInvalidSortingFormat(){
        assertThrows(IllegalArgumentException.class, () -> leaderboard.getTopNPlayers(2, "checkers win percentage"));
    }

    @Test
    void testInvalidGameNameWithWins() {
        assertThrows(IllegalArgumentException.class, () -> leaderboard.getTopNPlayers(2, "chess wins"));
    }

    @Test
    void testInvalidGameNameWithLosses() {
        assertThrows(IllegalArgumentException.class, () -> leaderboard.getTopNPlayers(2, "chess losses"));
    }

    @Test
    void testInvalidGameNameWithPercentage() {
        assertThrows(IllegalArgumentException.class, () -> leaderboard.getTopNPlayers(2, "chess percentage"));
    }

    @Test
    void testEmptyLeaderboard() {
        Leaderboard emptyLeaderboard = new Leaderboard();
        assertThrows(IndexOutOfBoundsException.class, () -> emptyLeaderboard.getTopNPlayers(1, "checkers wins"));
    }

    @Test
    void testGetTopPlayers_TiedScores() {
        Leaderboard lb = new Leaderboard();
        Player a = new Player("A");
        a.setCheckerWins(5);
        Player b = new Player("B");
        b.setCheckerWins(10);
        Player c = new Player("C");
        c.setCheckerWins(5);
        lb.addPlayer(a);
        lb.addPlayer(b);
        lb.addPlayer(c);
        List<Player> topPlayers = lb.getTopNPlayers(3, "checkers wins");
        assertEquals("B", topPlayers.get(0).getUsername()); // when run through with the @BeforeEach setUp(), the expected value is "B" but the actual is "Erick"
        assertEquals("A", topPlayers.get(1).getUsername());
        assertEquals("C", topPlayers.get(2).getUsername());
    }

    @Test
    void testGetPlayers() {
        List<Player> players = leaderboard.getPlayers();
        List<Player> testList = new ArrayList<>();
        testList.add(alice); testList.add(bob); testList.add(charlie); testList.add(dave); testList.add(fred); testList.add(erick);
        for (Player p : players) {
            assertTrue(leaderboard.isPlayerInLeaderboard(p));
        }
    }
}
