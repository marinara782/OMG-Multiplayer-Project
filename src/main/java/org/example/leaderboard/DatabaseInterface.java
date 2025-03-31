package org.example.leaderboard;

import java.util.List;

public interface  DatabaseInterface {

    /**
     * obtains the list of rankings in a specific game
     * 
     * @param gameName name of game like Checkers, tictactoe
     * @return list of ranking entries each containing player name, wins and losses
     */
    List<rankingEntry> getRankings(String gameName);
}
