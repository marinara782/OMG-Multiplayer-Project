package org.example.leaderboard;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardDatabaseStub {
    public List<rankingEntry> getRankings(String gameName){
        List<rankingEntry> entries = new ArrayList<>();
        switch (gameName) {
            case "Tic-Tac-Toe" -> {
                entries.add(new rankingEntry("Alice", 20, 10));
                entries.add(new rankingEntry("Bob", 21, 5));
                entries.add(new rankingEntry("Charlie", 5, 3));
            }
            case "Checkers" -> {
                entries.add(new rankingEntry("Alice", 2, 1));
                entries.add(new rankingEntry("Bob", 5, 3));
                entries.add(new rankingEntry("Charlie", 6, 3));
            }
            case "Connect4" -> {
                entries.add(new rankingEntry("Alice", 5, 3));
                entries.add(new rankingEntry("Bob", 6, 4));
                entries.add(new rankingEntry("Charlie", 7, 8));
            }
            default -> {
            }
        }
        return entries; // unsorted right now
    }
}