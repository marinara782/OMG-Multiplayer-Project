package org.example.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Player;
import org.example.authentication.UserProfile;
import org.example.leaderboard.Leaderboard;
import org.example.leaderboard.LeaderboardDatabaseStub;

import java.io.IOException;
import java.util.List;

public class LeaderBoardController {
    private Stage stage;

    @FXML private TableView<Player> leaderboardTable;
    @FXML private TableColumn<Player, Integer> rankColumn;
    @FXML private TableColumn<Player, String> playerColumn;
    @FXML private TableColumn<Player, Integer> winsColumn;
    @FXML private TableColumn<Player, Integer> lossColumn;
    @FXML private TableColumn<Player, Integer> drawsColumn;
    @FXML private TableColumn<Player, Integer> percentageColumn;

    @FXML private ChoiceBox<String> sortBox;
    @FXML private TextField searchField;
    @FXML public VBox gamesMenu;

    @FXML private Label firstPlaceName, firstPlaceScore;
    @FXML private Label secondPlaceName, secondPlaceScore;
    @FXML private Label thirdPlaceName, thirdPlaceScore;

    private final LeaderboardDatabaseStub databaseStub = new LeaderboardDatabaseStub();
    private final Leaderboard leaderboard = new Leaderboard();
    private UserProfile user;

    private String currentSortCriteria = "total";

    /**
     * Setter method for User
     * @param user
     */
    public void setUser(UserProfile user) {
        this.user = user;
    }

    /**
     * Intializes the UI, Sets the Table Columns so that they can update based on sort and other demands
     */
    @FXML
    public void initialize() {
        // Setup Table Columns
        rankColumn.setReorderable(false);
        playerColumn.setReorderable(false);
        winsColumn.setReorderable(false);
        lossColumn.setReorderable(false);
        //Updates the Rankings
        rankColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= leaderboardTable.getItems().size()) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1));
                }
            }
        });

        //Gets the players Username for the Table columns
        playerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));

        //Updates the Wins based on the Type of Sort
        winsColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= leaderboardTable.getItems().size()) {
                    setText(null);
                } else {
                    Player player = leaderboardTable.getItems().get(getIndex());
                    int stat = getPlayerStat(player, currentSortCriteria, "wins");
                    setText(String.valueOf(stat));
                }
            }
        });

        //Updates the losses based on the Type of Sort
        lossColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= leaderboardTable.getItems().size()) {
                    setText(null);
                } else {
                    Player player = leaderboardTable.getItems().get(getIndex());
                    int stat = getPlayerStat(player, currentSortCriteria, "losses");
                    setText(String.valueOf(stat));
                }
            }
        });

        sortBox.setValue("Total wins");
        sortBox.setOnAction(this::handleSort);
        loadAndDisplayPlayers("Total wins");
    }

    /**
     * Displays table based on sorting criteria
     * @param criteria
     */
    private void loadAndDisplayPlayers(String criteria) {
        currentSortCriteria = criteria.split(" ")[0].toLowerCase(); // store just the game
        List<Player> sortedPlayers = databaseStub.getSortedPlayers(criteria); // sort using full string
        leaderboardTable.refresh();
        leaderboardTable.setItems(FXCollections.observableArrayList(sortedPlayers));
        updatePodium(sortedPlayers);
    }

    /**
     * Podium of top 3 players are shown, updated based on sort
     * @param players
     */
    private void updatePodium(List<Player> players) {
        firstPlaceName.setText("");
        firstPlaceScore.setText("");
        secondPlaceName.setText("");
        secondPlaceScore.setText("");
        thirdPlaceName.setText("");
        thirdPlaceScore.setText("");

        if (players.size() >= 1) {
            firstPlaceName.setText(players.get(0).getUsername());
            firstPlaceScore.setText(getPlayerStat(players.get(0), currentSortCriteria, "wins") + " wins");
        }
        if (players.size() >= 2) {
            secondPlaceName.setText(players.get(1).getUsername());
            secondPlaceScore.setText(getPlayerStat(players.get(1), currentSortCriteria, "wins") + " wins");
        }
        if (players.size() >= 3) {
            thirdPlaceName.setText(players.get(2).getUsername());
            thirdPlaceScore.setText(getPlayerStat(players.get(2), currentSortCriteria, "wins") + " wins");
        }
    }

    /**
     * Gets players stats based on the game type
     * @param player
     * @param game
     * @param type
     * @return
     */
    private int getPlayerStat(Player player, String game, String type) {
        switch (game) {
            case "total":
                if (type.equals("wins")) {
                    return player.getTotalWins();
                } else {
                    return player.getTotalLosses();
                }
            case "checkers":
                if (type.equals("wins")) {
                    return player.getCheckerWins();
                } else {
                    return player.getCheckerLosses();
                }
            case "connect4":
                if (type.equals("wins")) {
                    return player.getConnect4Wins();
                } else {
                    return player.getConnect4Losses();
                }
            case "tictactoe":
                if (type.equals("wins")) {
                    return player.getTictactoeWins();
                } else {
                    return player.getTictactoeLosses();
                }
            default:
                return 0;
        }
    }

    /**
     * User Inputs sort criteria into sort box, to sort data to get preferred output
     * @param event
     */
    @FXML
    public void handleSort(ActionEvent event) {
        String selected = sortBox.getValue();
        if (selected != null) {
            loadAndDisplayPlayers(selected.toLowerCase());
        }
    }

    /**
     * User inputs username into search bar and will find player(s) based on that
     * @param event
     */
    @FXML
    public void handleSearch(ActionEvent event) {
        String searchTerm = searchField.getText().trim().toLowerCase();

        if (searchTerm.isEmpty()) { //Does the same Job as the Handle Reset
            loadAndDisplayPlayers(sortBox.getValue());
            leaderboardTable.refresh();
            return;
        }

        List<Player> allPlayers = databaseStub.getSortedPlayers(sortBox.getValue());
        List<Player> filtered = allPlayers.stream()
                .filter(p -> p.getUsername().toLowerCase().contains(searchTerm))
                .toList();

        leaderboardTable.setItems(FXCollections.observableArrayList(filtered));
        updatePodium(filtered);

    }

    /**
     * Resets search request
     * @param actionEvent
     */
    @FXML
    public void handleReset(ActionEvent actionEvent) {
        searchField.clear();
        loadAndDisplayPlayers(sortBox.getValue());
        leaderboardTable.refresh();
    }

    //Navigation Bar, might make this into its own class

    /**
     * Moves stage back to main Menu
     * @param actionEvent
     */
    public void handleHome(ActionEvent actionEvent) {
        try {
            Stage mainMenuStage = new Stage(); // Create a new Stage for the main menu
            MainMenuWindow mainMenu = new MainMenuWindow(mainMenuStage, user);
            mainMenuStage.show(); // Show the new main menu stage

            // Close the login window
            Stage currentStage = (Stage) leaderboardTable.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the main menu.");
        }
    }
    /* Not Implemented
    public void handleGames(ActionEvent actionEvent) {
        gamesMenu.setVisible(!gamesMenu.isVisible());
    }
    public void handleTicTacToe(ActionEvent actionEvent) {
        //will need to be added to go to the window, if we implement a specfic game description window
    }
    public void handleCheckers(ActionEvent actionEvent) {
        //will need to be added to go to the window, if we implement a specfic game description window
    }
    public void handleConnect4(ActionEvent actionEvent) {
        //will need to be added to go to the window, if we implement a specfic game description window
    }
    public void handleSettings(ActionEvent actionEvent) {
    }
    */

    /**
     * Opens the User Profile window
     * @param actionEvent
     */
    public void handleProfile(ActionEvent actionEvent) {
        UserProfileWindow profileWindow = new UserProfileWindow(new Stage(), user);
        profileWindow.show();
    }

    /**
     * Logs the User out, opens the login window
     * @param actionEvent
     */
    public void handleLogout(ActionEvent actionEvent) {
        try {
            Stage currentStage = (Stage) leaderboardTable.getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginWindow2.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage loginStage = new Stage();
            loginStage.setScene(scene);
            loginStage.setTitle("Login");
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }}


    /**
     * Pops up alert to show information
     * @param title
     * @param message
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
