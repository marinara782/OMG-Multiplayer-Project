package org.example.leaderboard;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.xml.sax.EntityResolver;

public class DatabaseStubLeaderboard implement DatabaseInterface{
    // TODO: stub implementation for leaderboard 

    @Override
    public List<rankingEntry> getRankings(String gameName){
        // suppose fetching from a real database
        List<rankingEntry> entries  =new ArrayList<>();
    
        // chess game
        if ("Connect Four".equals(gameName)){
            entries.add(new rankingEntry("Alice", 20, 10));
            entries.add(new rankingEntry("Bob", 21, 5));
            entries.add(new rankingEntry("Charlie", 5, 3));
        }

        // checkers game
        if ("Checkers".equals(gameName)){
            entries.add(new rankingEntry("Alice", 2, 1));
            entries.add(new rankingEntry("Bob", 5, 3));
            entries.add(new rankingEntry("Charlie", 6, 3));

        }

        // tictactoe game
        if ("Tic-Tac-Toe".equals(gameName)){
            entries.add(new rankingEntry("Alice", 5, 3));
            entries.add(new rankingEntry("Bob", 6, 3));
            entries.add(new rankingEntry("Charlie", 7, 8));
        } else {
            return entries;
        }

        // sort in descending order 
        entries.sort(Comparator.comparingInt(rankingEntry::getWins).reversed());
        return entries;

    }

}
