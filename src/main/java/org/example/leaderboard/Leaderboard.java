package org.example.leaderboard;

import javafx.stage.Stage;
import org.example.Player;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard {

    private ArrayList<Player> players;

    public void showLeaderboard(Stage stage) {
    }

    /**
     * Add a player to the leaderboard
     * @param player: the player to be added to the leaderboard
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * view the top n players of the leaderboard in a certain sorting criteria
     * @param amount the amount of players to be viewed (i.e. top 5, bottom 3, etc.)
     * @param sortingCriteria the sorting criteria to view to top players (i.e. sort by checker wins, connect4 losses, etc.)
     */
    public ArrayList<Player> getTopNPlayers(int amount,String sortingCriteria) {
        sortRankings(sortingCriteria);
        ArrayList<Player> result = new ArrayList<>();
        for (int i=0; i<amount; i++) {
            result.add(players.get(i));
        }

        return result;
    }

    /**
     * Sorts the rankings based on the criteria like wins/losses
     * @param sortingCriteria criteria like checker wins, tictactoe losses, etc
     */
    private void sortRankings(String sortingCriteria) {
        //TODO: change current sorting method from using ranking entry to Player attributes
    }
}
