package org.example.leaderboard;

import java.util.Comparator;
import java.util.List;

import javafx.stage.Stage;

public class Leaderboard {

    private DatabaseInterface databaseInterface = new DatabaseStubLeaderboard;
    public void showLeaderboard(Stage stage) {
    }

    /**
     * Add a player to the leaderboard
     * @param playerName name of the player to be added
     * @param playerScore the score of the player (potentially change to be a win-loss-tie format
     *                    instead of a single score: to be determined later)
     */
    public void addPlayer(String playerName, int playerScore) {
        // TODO: implement logic to add a player to the leaderboard
    }

    /**
     * Update the score of a player.
     * Note: potentially change this or add a new method that adds an integer value to a players current score
     * @param playerName name of player that's score is to be updated
     * @param newScore the new score of the player
     */
    public void updateScore(String playerName, int newScore) {
        // TODO: implement logic to update the score of a player
    }

    /**
     * Retrieves the top players based on their score
     * @param count the number of top players to retrieve
     * @return a list of top players
     */
    public List<String> getTopPlayers(int count) {
        // TODO: implement logic to retrieve the top 'count' number of players
        return null;
    }

    public void exportLeaderboard(String filePath) {
        // TODO: implement the logic to export leaderboard data to a file
        // most most likely will call a file writer class, to be implemented later
    }

    /**
     * Displays the leaderboard in a formatted manner
     */
    public void displayLeaderboard() {
        // TODO: implement logic to display the leaderboard
        // note: this method may not be needed, as exportLeaderboard may be sufficient
    }

    /**
     * Sorts the rankings based on the criteria like wins/losses
     * 
     * @param rankings list of ranking entries
     * @param criteriaString criteria like wins/losses
     */
    private void sortRankings(List<RankingEntry> rankings, String criteriaString){
        switch (criteriaString){
            case "wins":
                rankings.sort(Comparator.comparingInt(RankingEntry::getWins).reversed());
                break;
            case "losses":
                rankings.sort(Comparator.comparingInt(RankingEntry::getLosses));
                break;
            default:
                throw new IllegalArgumentException("invalid sorting criteria: "+criteriaString);
        }
    }
}
