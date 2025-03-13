package org.example.leaderboard;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardDatabaseStub implements DatabaseInterface{
    // TODO: stub implementation for leaderboard

    @Override
    public List<Leaderboard.rankingEntry> getRankings(String gameName){
        // suppose this database stub fetches data from a real database
        Leaderboard leaderboard = new Leaderboard();
        List<Leaderboard.rankingEntry> entries = leaderboard.new rankingEntry();

        // checkers game
            entries.add(leaderboard.new rankingEntry("Alice", 20, 10));
            entries.add(leaderboard.new rankingEntry("Bob", 21, 5));
        }
    }
}
