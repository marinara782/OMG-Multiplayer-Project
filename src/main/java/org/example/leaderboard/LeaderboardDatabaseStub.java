package org.example.leaderboard;

import java.util.ArrayList;
import java.util.List;

/**
 * Stub class for the database interface
 */
public class LeaderboardDatabaseStub implements DatabaseInterface {
    @Override
    public List<RankingEntry> getRankings(String gameName){
        List<RankingEntry> entries = new ArrayList<>();
        switch (gameName) {
            case "Tic-Tac-Toe":
                entries.add(new RankingEntry("Alice", 20, 10));
                entries.add(new RankingEntry("Bob", 21, 5));
                entries.add(new RankingEntry("Charlie", 5, 3));
                break;
            case "Checkers":
                entries.add(new RankingEntry("Alice", 2, 1));
                entries.add(new RankingEntry("Bob", 5, 3));
                entries.add(new RankingEntry("Charlie", 6, 3));
                break;
            case "Connect4":
                entries.add(new RankingEntry("Alice", 5, 3));
                entries.add(new RankingEntry("Bob", 6, 4));
                entries.add(new RankingEntry("Charlie", 7, 8));
                break;
            default:
                break;
        }
        
        return entries; // unsorted right now
    }
}