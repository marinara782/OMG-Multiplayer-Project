package org.example.leaderboard;


import org.junit.jupiter.api.Test;

import org.example.Player;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderboardDatabaseStubTest {

    @Test
    public void testInitializePlayers_addsAllPlayers() {
        LeaderboardDatabaseStub stub = new LeaderboardDatabaseStub();

        // Access the private leaderboard field via reflection
        Leaderboard leaderboard = null;
        try {
            java.lang.reflect.Field leaderboardField = stub.getClass().getDeclaredField("leaderboard");
            leaderboardField.setAccessible(true);
            leaderboard = (Leaderboard) leaderboardField.get(stub);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }

        // Access the private players list in Leaderboard via reflection
        List<Player> players = null;
        try {
            java.lang.reflect.Field playersField = leaderboard.getClass().getDeclaredField("players");
            playersField.setAccessible(true);
            players = (List<Player>) playersField.get(leaderboard);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }

        // Assert the expected number of players is added (15 in the provided stub)
        assertEquals(15, players.size());
    }

    @Test
    public void testGetTopPlayer_Checkers() {
        LeaderboardDatabaseStub stub = new LeaderboardDatabaseStub();
        Leaderboard leaderboard = null;
        try {
            java.lang.reflect.Field leaderboardField = stub.getClass().getDeclaredField("leaderboard");
            leaderboardField.setAccessible(true);
            leaderboard = (Leaderboard) leaderboardField.get(stub);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }

        // Test for "checkers wins" - expecting "Lebron" (90 wins) to be at the top
        List<Player> topPlayers = leaderboard.getTopNPlayers(1, "checkers wins");
        assertEquals("Lebron", topPlayers.get(0).getUsername());
    }

    @Test
    public void testInvalidSortingCriteria_throwsException() {
        LeaderboardDatabaseStub stub = new LeaderboardDatabaseStub();
        Leaderboard leaderboard = null;
        try {
            java.lang.reflect.Field leaderboardField = stub.getClass().getDeclaredField("leaderboard");
            leaderboardField.setAccessible(true);
            leaderboard = (Leaderboard) leaderboardField.get(stub);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }

        // This should throw an exception due to an invalid sorting criteria
        Leaderboard finalLeaderboard = leaderboard;
        assertThrows(IllegalArgumentException.class, () -> finalLeaderboard.getTopNPlayers(2, "invalid criteria"));
    }

    @Test
    public void testPrintTopCheckersWinsPlayers_output() {
        LeaderboardDatabaseStub stub = new LeaderboardDatabaseStub();

        // Redirect System.out to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Test print method; for example, print top 3 by checkers wins
        stub.printTopPlayers(3, "checkers wins");

        System.setOut(originalOut);
        String output = outContent.toString();

        // Check that the output includes expected information.
        // For instance, verify that it contains "Lebron - 90" (if Lebron is expected as top).
        assertTrue(output.contains("Lebron - 90"));
    }

    @Test
    public void testPrintTopCheckersLossesPlayers_output() {
        LeaderboardDatabaseStub stub = new LeaderboardDatabaseStub();

        // Redirect System.out to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Test print method; for example, print top 3 by checkers wins
        stub.printTopPlayers(3, "checkers losses");

        System.setOut(originalOut);
        String output = outContent.toString();

        // Check that the output includes expected information.
        assertTrue(output.contains("Erick - 56"));
    }

    @Test
    public void testPrintTopCheckersPercentagePlayeers_output() {
        LeaderboardDatabaseStub stub = new LeaderboardDatabaseStub();

        // Redirect System.out to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Test print method; for example, print top 3 by checkers wins
        stub.printTopPlayers(3, "checkers percentage");

        System.setOut(originalOut);
        String output = outContent.toString();

        // Check that the output includes expected information.
        assertTrue(output.contains("Lebron - 100.00%"));
    }

    @Test
    public void testPrintTopConnect4WinsPlayers_output() {
        LeaderboardDatabaseStub stub = new LeaderboardDatabaseStub();

        // Redirect System.out to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Test print method; for example, print top 3 by checkers wins
        stub.printTopPlayers(4, "connect4 wins");

        System.setOut(originalOut);
        String output = outContent.toString();

        // Check that the output includes expected information.
        assertTrue(output.contains("Lebron - 93"));
    }

    @Test
    public void testPrintTopConnect4LossesPlayers_output() {
        LeaderboardDatabaseStub stub = new LeaderboardDatabaseStub();

        // Redirect System.out to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Test print method; for example, print top 3 by checkers wins
        stub.printTopPlayers(4, "connect4 losses");

        System.setOut(originalOut);
        String output = outContent.toString();

        // Check that the output includes expected information.
        assertTrue(output.contains("Gary - 66"));
    }

    @Test
    public void testPrintTopConnect4PercentagePlayers_output() {
        LeaderboardDatabaseStub stub = new LeaderboardDatabaseStub();

        // Redirect System.out to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Test print method; for example, print top 3 by checkers wins
        stub.printTopPlayers(4, "connect4 percentage");

        System.setOut(originalOut);
        String output = outContent.toString();

        // Check that the output includes expected information.
        assertTrue(output.contains("Lebron - 100.00%"));
    }
    @Test
    public void testPrintTopTictactoeWinsPlayers_output() {
        LeaderboardDatabaseStub stub = new LeaderboardDatabaseStub();

        // Redirect System.out to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Test print method; for example, print top 3 by checkers wins
        stub.printTopPlayers(4, "tictactoe wins");

        System.setOut(originalOut);
        String output = outContent.toString();

        // Check that the output includes expected information.
        assertTrue(output.contains("Fred - 98"));
    }

    @Test
    public void testPrintTopTictactoeLossesPlayers_output() {
        LeaderboardDatabaseStub stub = new LeaderboardDatabaseStub();

        // Redirect System.out to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Test print method; for example, print top 3 by checkers wins
        stub.printTopPlayers(4, "tictactoe losses");

        System.setOut(originalOut);
        String output = outContent.toString();

        // Check that the output includes expected information.
        assertTrue(output.contains("Jack - 98"));
    }

    @Test
    public void testPrintTopTictactoePercentagePlayers_output() {
        LeaderboardDatabaseStub stub = new LeaderboardDatabaseStub();

        // Redirect System.out to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Test print method; for example, print top 3 by checkers wins
        stub.printTopPlayers(8, "tictactoe percentage");

        System.setOut(originalOut);
        String output = outContent.toString();

        // Check that the output includes expected information.
        assertTrue(output.contains("Lebron - 100.00%"));
    }

    @Test
    public void testPrintTopTotalWinsPlayers_output() {
        LeaderboardDatabaseStub stub = new LeaderboardDatabaseStub();

        // Redirect System.out to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Test print method; for example, print top 3 by checkers wins
        stub.printTopPlayers(8, "total wins");

        System.setOut(originalOut);
        String output = outContent.toString();

        // Check that the output includes expected information.
        assertTrue(output.contains("Lebron - 250"));
    }

    @Test
    public void testPrintTopTotalLossesPlayers_output() {
        LeaderboardDatabaseStub stub = new LeaderboardDatabaseStub();

        // Redirect System.out to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Test print method; for example, print top 3 by checkers wins
        stub.printTopPlayers(8, "total losses");

        System.setOut(originalOut);
        String output = outContent.toString();

        // Check that the output includes expected information.
        assertTrue(output.contains("Jack - 174"));
    }

    @Test
    public void testPrintTopWinPercentagePlayers_output() {
        LeaderboardDatabaseStub stub = new LeaderboardDatabaseStub();

        // Redirect System.out to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Test print method; for example, print top 3 by checkers wins
        stub.printTopPlayers(5, "win percentage");

        System.setOut(originalOut);
        String output = outContent.toString();

        // Check that the output includes expected information.
        assertTrue(output.contains("Lebron - 100.00%"));
    }

    @Test
    public void testMainOutput() {
        // Save original System.out
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        // Redirect System.out to capture prints
        System.setOut(new PrintStream(outContent));

        // Call main (or another entry method)
        LeaderboardDatabaseStub.main(new String[]{});

        // Restore the original System.out
        System.setOut(originalOut);

        // Convert captured output to String
        String output = outContent.toString();

        // Assert that output contains expected results, e.g., "Lebron - 90"
        // (Customize this according to the expected output from the test data)
        assertTrue(output.contains("Lebron - 90"));
    }

    @Test
    void testGetSortedPlayers() {
        LeaderboardDatabaseStub stub = new LeaderboardDatabaseStub();

        // Act: Get all players sorted by "checkers wins" (the entire list should be returned sorted)
        List<Player> sortedPlayers = stub.getSortedPlayers("checkers wins");

        // Assert: Check that the list size matches the number of players expected (15 based on initialization)
        int expectedPlayersCount = 15;
        assertEquals(expectedPlayersCount, sortedPlayers.size());

        // Assert: Check that the players are sorted in descending order based on checkers wins.
        for (int i = 0; i < sortedPlayers.size() - 1; i++) {
            int currentWins = sortedPlayers.get(i).getCheckerWins();
            int nextWins = sortedPlayers.get(i + 1).getCheckerWins();
            // ensure that the leaderboard is in sorted order
            assertTrue( currentWins >= nextWins);
        }

        // Spot-check known value: From the initialization, "Lebron" should have 90 wins—the highest.
        assertEquals( "Lebron", sortedPlayers.getFirst().getUsername());
    }
}
