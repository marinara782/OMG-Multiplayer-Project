package org.example.matchmaking;

import org.example.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.example.matchmaking.SkillBasedMatchmaking;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SkillBasedMatchMakingTest {
    private SkillBasedMatchmaking matchmaking;
    private Player alice, bob, charlie, dave;

    @BeforeEach
    void setUp() {
        matchmaking = new SkillBasedMatchmaking(0.05, 10, 0.05); // 0.05 tolerance, 10s max wait, +0.05 each time exceeded

        alice = new Player("Alice");
        alice.setCheckerWins(10);
        alice.setCheckerLosses(5); // skill level (win percentage overall) = 0.6

        bob = new Player("Bob");
        bob.setCheckerWins(8);
        bob.setCheckerLosses(2); // skill level(win percentage overall) = 0.8

        charlie = new Player("Charlie");
        charlie.setCheckerWins(1);
        charlie.setCheckerLosses(9); // skill level (win percentage overall)= 0.1

        dave = new Player("Dave");
        dave.setCheckerWins(7);
        dave.setCheckerLosses(3); // skill level (win percentage overall) = 0.7
    }

    /*This test sees if adding a single player immediately will place them in the unmatched players queue.
     * It should not do this because players should only be added if they have waited long enough -
     * double the max wait time. */
    @Test
    void testAddSinglePlayerToUnmatchedPlayers() {
        matchmaking.addPlayer(alice);
        List<String> unmatched = matchmaking.getUnmatchedPlayers();
        assertFalse(unmatched.contains("Alice")); // Because she hasn't waited long enough
    }

    /*This test sees if adding multiple players immediately will place them in the unmatched players queue.
    * It should not do this because players should only be added if they have waited long enough -
    * double the max wait time. */
    @Test
    void testAddMultiplePlayersToUnmatchedPlayers() {
        matchmaking.addPlayer(alice);
        matchmaking.addPlayer(bob);
        matchmaking.addPlayer(charlie);

        List<String> unmatched = matchmaking.getUnmatchedPlayers();
        assertTrue(unmatched.isEmpty()); // They haven't waited long enough.
    }

    /*This test sees if a match is found for two players who should be matched based
    * on their skill levels and the base tolerance. */
    @Test
    void testFindMatchWithinBaseTolerance() {
        matchmaking.addPlayer(alice);
        matchmaking.addPlayer(dave);

        List<String[]> matches = matchmaking.findMatches();
        assertEquals(1, matches.size());
        assertEquals("Alice", matches.get(0)[0]);
        assertEquals("Dave", matches.get(0)[1]);
    }

    /*This test sees if a match is found for two players who should NOT be matched based
     * on their skill levels and the base tolerance, AFTER time exceeds max wait time. We
     * WANT to match two people EVEN if their skill levels are wildly all over the place
     * if they wait long enough. We never want a situation where two people are looking for
     * matches yet never get paired. */
    @Test
    void testFindMatchOutsideBaseTolerance() {
        matchmaking.addPlayer(alice);
        matchmaking.addPlayer(charlie);

        List<String[]> matches = matchmaking.findMatches();
        assertEquals(1, matches.size());
    }

    /* This test checks if the matchmaking system leaves one player unmatched
     * when there’s an odd number of players. The system should only match full pairs.
     */
    @Test
    void testOddNumberOfPlayersLeavesOneUnmatched() throws InterruptedException {
        matchmaking.addPlayer(alice);   // Matchable
        matchmaking.addPlayer(dave);    // Matchable
        matchmaking.addPlayer(charlie); // Odd one out

        List<String[]> matches = matchmaking.findMatches();

        // Alice and Dave should be matched, Charlie should remain unmatched
        assertEquals(1, matches.size());
        assertEquals("Alice", matches.get(0)[0]);
        assertEquals("Dave", matches.get(0)[1]);

        Thread.sleep(21000);

        List<String> unmatched = matchmaking.getUnmatchedPlayers();
        assertTrue(unmatched.contains("Charlie"));
        assertEquals(1, unmatched.size());
    }

    /* This test checks an edge case where a player is unmatched after waiting too long,
     * and then another player joins the pool, we want to make sure that the original player is still
     * considered unmatched and wasn't affected by the new arrival.
     */
    @Test
    void testUnmatchedPlayerRemainsAfterNewJoiner() throws InterruptedException {
        matchmaking.addPlayer(alice);
        Thread.sleep(21000); // 21s > 2 * 10s

        matchmaking.addPlayer(bob); // joins right after long wait

        List<String> unmatched = matchmaking.getUnmatchedPlayers();
        assertEquals(1, unmatched.size());
        assertTrue(unmatched.contains("Alice")); // Alice should still be considered unmatched
    }

    /*This test sees if the unmatched players queue can be retrieved. this is important
    * because without being able to retrieve it, the list is useless. */
    @Test
    void testGetUnmatchedPlayersEmptyQueue() {
        List<String> unmatched = matchmaking.getUnmatchedPlayers();
        assertTrue(unmatched.isEmpty());
    }

    /*This test sees if a player is added to the unmatched players queue after waiting
     * long enough. this is important because we don't want unmatched players waiting
     * forever. */
    @Test
    void testUnmatchedPlayersOverDoubleWaitTime() throws InterruptedException {
        matchmaking.addPlayer(alice);
        Thread.sleep(21000); // 21s > double the wait time (2 * 10s)
        List<String> unmatched = matchmaking.getUnmatchedPlayers();
        assertTrue(unmatched.contains("Alice"));
    }

    /*This test sees if the scenario in which the same player is somehow added to the match
    * pool twice results in the player being matched with themselves (it should not).*/
    @Test
    void testMatchDoesNotIncludeSelf() {
        matchmaking.addPlayer(alice);
        matchmaking.addPlayer(alice); // same object

        List<String[]> matches = matchmaking.findMatches();
        assertTrue(matches.isEmpty() || !matches.get(0)[0].equals(matches.get(0)[1]));
    }

    /*This test sees if multiple players are matched together properly. EXTREMELY
    * vital test, this sees if the entire system works as intended in usual case
    * scenarios.*/
    @Test
    void testFindMatchesWithMultiplePairs() {
        matchmaking.addPlayer(alice);
        matchmaking.addPlayer(dave);
        matchmaking.addPlayer(bob);
        matchmaking.addPlayer(charlie);

        List<String[]> matches = matchmaking.findMatches();
        assertEquals(2, matches.size()); // Expecting 2 pairs
    }
}
