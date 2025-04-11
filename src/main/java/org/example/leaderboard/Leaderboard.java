package org.example.leaderboard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.example.Player;

import javafx.stage.Stage;

public class Leaderboard {

    // list of players
    private List<Player> players = new ArrayList<>();
    public void showLeaderboard(Stage stage) {
    }

    /**
     * Add a player to the leaderboard
     * @param player: the player to be added to the leaderboard
     */
    public void addPlayer(Player player) {
        if (!isPlayerInLeaderboard(player)) {
            players.add(player); // add player to the list of players
        }
    }

    /**
     * Checks if a player is already inside the leaderboard
     * @param player player to check
     * @return true if player is in leaderboard, otherwise false
     */
    public Boolean isPlayerInLeaderboard(Player player) {
        return players.contains(player);
    }

    /**
     * view the top n players of the leaderboard in a certain sorting criteria (i.e. top 5 checker wins, top 10 tic-tac-toe losses, etc.)
     * Pass sorting criteria via the following approved criteria:
     *          "checkers wins": sort via checkers wins
     *          "tictactoe wins": sort via tictactoe wins
     *          "connect4 wins": sort via connect4 wins
     *          "total wins": sort via total wins (all games)
     *          "checkers losses": sort via checkers losses
     *          "tictactoe losses": sort via tictactoe losses
     *          "connect4 losses": sort viaconnect4 losses
     *          "total losses": sort via total losses (all games)
     *          "checkers percentage": sort via checkers percentage (checkers win percentage)
     *          "tictactoe percentage": sort via tictactoe percentage (tictactoe win percentage)
     *          "connect4 percentage": sort via connect4 percentage (connect4 win percentage)
     *          "win percentage": sort via win percentage (total win percentage [all games])
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
     * @param criteriaString criteria like wins/losses
     */
    private void sortRankings(String criteriaString){ // TODO: change the paramter to just the criteria string
        String[] parts = criteriaString.split(" "); // split the criteria string into parts so when on button press in GUI, it will be like "sort by checker wins" and this will split it into ["sort", "by", "checker", "wins"]
        if (parts.length != 2){
            throw new IllegalArgumentException("Invalid sorting criteria format.");
        }
        String game = parts[0].toLowerCase(); // get the game name
        String type = parts[1].toLowerCase(); // get the type of sorting criteria (i.e. wins/losses)
        Comparator<Player> comparator = getComparator(game, type);
        players.sort(comparator); // sort the players based on the comparator
    }
    /**
     * Is a helper method for sortRanking. Get the comparator based on the game name and criteria
     *
     * @param gameName the name of the game
     * @param criteriaString the criteria like wins/losses
     * @return the comparator
     */
    private Comparator<Player> getComparator(String gameName, String criteriaString){
        if ("wins".equals(criteriaString)){ // if the criteria is wins
            switch (gameName) { // switch based on the game name
                case "checkers":
                    return Comparator.comparingInt(Player::getCheckerWins).reversed(); // return the comparator based on the checker wins. reversed() is used to sort in descending order
                case "tictactoe":
                    return Comparator.comparingInt(Player::getTictactoeWins).reversed();
                case "connect4":
                    return Comparator.comparingInt(Player::getConnect4Wins).reversed();
                case "total":
                    return Comparator.comparingInt(Player::getTotalWins).reversed();
                default:
                    throw new IllegalArgumentException("invalid game name: "+gameName);
            }
        } else if ("losses".equals(criteriaString)){ // if the criteria is losses
            switch (gameName) { // switch based on the game name
                case "checkers":
                    return Comparator.comparingInt(Player::getCheckerLosses).reversed();
                case "tictactoe":
                    return Comparator.comparingInt(Player::getTictactoeLosses).reversed();
                case "connect4":
                    return Comparator.comparingInt(Player::getConnect4Losses).reversed();
                case "total":
                    return Comparator.comparingInt(Player::getTotalLosses).reversed();
                default:
                    throw new IllegalArgumentException("invalid game name: "+gameName);
            }
        } else if ("percentage".equals(criteriaString)) {
            switch (gameName) {
                case "checkers":
                    return Comparator.comparingDouble(Player::getCheckersWinPercentage).reversed();
                case "tictactoe":
                    return Comparator.comparingDouble(Player::getTicTacToeWinPercentage).reversed();
                case "connect4":
                    return Comparator.comparingDouble(Player::getConnect4WinPercentage).reversed();
                case "win":
                    return Comparator.comparingDouble(Player::getWinPercentage).reversed();
                default:
                    throw new IllegalArgumentException("invalid game name: " + gameName);
            }
        } else {
            throw new IllegalArgumentException("invalid sorting criteria: "+criteriaString);
        }
    }

    public List<Player> getPlayers() {
        return players;
    }
}

