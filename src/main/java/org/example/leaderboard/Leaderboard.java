package org.example.leaderboard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.example.Player;

import javafx.stage.Stage;

import java.util.List;

public class Leaderboard {

    // list of players
    private List<Player> players;
    public void showLeaderboard(Stage stage) {
    }

    /**
     * Add a player to the leaderboard
     * @param player: the player to be added to the leaderboard
     */
    public void addPlayer(Player player) {
        players.add(player); // add player to the list of players
    }

    /**
     * view the top n players of the leaderboard in a certain sorting criteria
     * @param amount the amount of players to be viewed (i.e. top 5, bottom 3, etc.)
     * @param sortingCriteria the sorting criteria to view to top players (i.e. sort by checker wins, connect4 losses, etc.)
     */
    public ArrayList<Player> getTopNPlayers(int amount,String sortingCriteria) {
        sortRankings(sortingCriteria); // don't change this
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

    /**
     * Sorts the rankings based on the criteria like wins/losses
     * 
     * @param rankings list of ranking entries
     * @param criteriaString criteria like wins/losses
     */
    private void sortRankings(List<Player> players, String criteriaString){ // TODO: change the paramter to just the criteria string
        switch (criteriaString){ // this class will no longer be using comparator but will now use `Player.java` and getComparator method
            case "wins":
                rankings.sort(Comparator.comparingInt(Player::getWins).reversed());
                break;  
            case "losses":
                rankings.sort(Comparator.comparingInt(Player::getLosses));
                break;
            default:
                throw new IllegalArgumentException("invalid sorting criteria: "+criteriaString);
        }
    }
    /**
     * Get the comparator based on the game name and criteria
     * 
     * @param gameName the name of the game
     * @param criteriaString the criteria like wins/losses
     * @return the comparator
     */
    private Comparator<Player> geComparator(String gameName, String criteriaString){
        if ("wins".equals(criteriaString)){ // if the criteria is wins
            switch (gameName) { // switch based on the game name
                case "checkers":
                    return Comparator.comparingInt(Player::getCheckerWins).reversed(); // return the comparator based on the checker wins. reversed() is used to sort in descending order
                case "tictactoe":
                    return Comparator.comparingInt(Player::getTictactoeWins).reversed();
                case "connect4":
                    return Comparator.comparingInt(Player::getConnect4Wins).reversed();
                default:
                    throw new IllegalArgumentException("invalid game name: "+gameName);
            }
        } else if ("losses".equals(criteriaString)){ // if the criteria is losses
            switch (gameName) { // switch based on the game name
                case "checkers":
                    return Comparator.comparingInt(Player::getCheckerLosses);
                case "tictactoe":
                    return Comparator.comparingInt(Player::getTictactoeLosses);
                case "connect4":
                    return Comparator.comparingInt(Player::getConncet4Losses);
                default:
                    throw new IllegalArgumentException("invalid game name: "+gameName);
            }
        } else {
            throw new IllegalArgumentException("invalid sorting criteria: "+criteriaString);
        } 
    }
}
