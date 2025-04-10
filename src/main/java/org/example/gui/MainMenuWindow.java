package org.example.gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.authentication.Login;
import org.example.authentication.UserProfile;
import org.example.game.checkers.CheckersGame;
import org.example.game.connectFour.ConnectFourGame;
import org.example.game.ticTacToe.TicTacToeGame;
import org.example.leaderboard.Leaderboard;

import java.io.IOException;
import java.util.Optional;

public class MainMenuWindow {
    private final Stage stage;
    private final UserProfile currentUser;

    public MainMenuWindow(Stage stage, UserProfile currentUser) {
        this.stage = stage;
        this.currentUser = currentUser;
//        this.matchmaker = new Matchmaker();
        initializeUI();
    }

    private void initializeUI() {
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #2c3e50;");

        // Top section - Header with logo and user info
        HBox header = createHeader();
        mainLayout.setTop(header);

        // Center section - Game selection
        VBox gameSelection = createGameSelection();
        mainLayout.setCenter(gameSelection);

        // Right section - Leaderboard and active players
        VBox rightPanel = createRightPanel();
        mainLayout.setRight(rightPanel);

        // Bottom section - Status bar
        HBox statusBar = createStatusBar();
        mainLayout.setBottom(statusBar);

        Scene scene = new Scene(mainLayout, 1200, 800);
        stage.setTitle("OMG - Online Multiplayer Game Platform");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        Platform.runLater(() -> {
            stage.centerOnScreen();
        });
    }

    private HBox createHeader() {
        HBox header = new HBox(20);
        header.setPadding(new Insets(10));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #1a2530; -fx-background-radius: 5;");

        // Logo placeholder
        Label logoLabel = new Label("OMG");
        logoLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #3498db;");

        // User profile button
        Button profileButton = new Button("Profile");
        profileButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        profileButton.setOnAction(_ -> openUserProfile());

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // User info
        Label userLabel = new Label("Welcome, " + currentUser.getUsername());
        userLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        // Logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        logoutButton.setOnAction(_ -> logout());

        header.getChildren().addAll(logoLabel, spacer, userLabel, profileButton, logoutButton);

        return header;
    }

    private VBox createGameSelection() {
        VBox gameSelection = new VBox(30);
        gameSelection.setPadding(new Insets(20, 10, 20, 10));
        gameSelection.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label("Select a Game");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        HBox gamesContainer = new HBox(30);
        gamesContainer.setAlignment(Pos.CENTER);

        // TicTacToe Game Card
        VBox ticTacToeCard = createGameCard("Tic-Tac-Toe", "ticTacToe");
        ticTacToeCard.setOnMouseClicked(_ -> runTicTacToeGame());

        // Connect Four Game Card
        VBox connectFourCard = createGameCard("Connect Four", "connectFour");
        connectFourCard.setOnMouseClicked(_ -> handleConnectFourClick());

        // Checkers Game Card
        VBox checkersCard = createGameCard("Checkers", "checkers");
        checkersCard.setOnMouseClicked(_ -> startGame("checkers"));

        gamesContainer.getChildren().addAll(ticTacToeCard, connectFourCard, checkersCard);

        // Matchmaking section
        VBox matchmakingSection = new VBox(15);
        matchmakingSection.setPadding(new Insets(20, 0, 0, 0));
        matchmakingSection.setAlignment(Pos.CENTER);
        matchmakingSection.setStyle("-fx-background-color: #34495e; -fx-background-radius: 5; -fx-padding: 20;");

        Label matchmakingLabel = new Label("Quick Match");
        matchmakingLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        ComboBox<String> gameSelector = new ComboBox<>();
        gameSelector.getItems().addAll("Tic-Tac-Toe", "Connect Four", "Checkers");
        gameSelector.setValue("Tic-Tac-Toe");
        gameSelector.setMinWidth(200);

        Button findMatchButton = new Button("Find Match");
        findMatchButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-min-width: 150px;");
        findMatchButton.setOnAction(_ -> findMatch(gameSelector.getValue()));

        matchmakingSection.getChildren().addAll(matchmakingLabel, gameSelector, findMatchButton);

        gameSelection.getChildren().addAll(titleLabel, gamesContainer, matchmakingSection);

        return gameSelection;
    }


    private VBox createGameCard(String gameName, String gameType) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: #34495e; -fx-background-radius: 5; -fx-cursor: hand;");
        card.setPrefWidth(200);
        card.setPrefHeight(200);

        // Game icon placeholder
        Region iconPlaceholder = new Region();
        iconPlaceholder.setPrefSize(100, 100);
        iconPlaceholder.setStyle("-fx-background-color: #3498db; -fx-background-radius: 10;");

        Label nameLabel = new Label(gameName);
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label playersLabel = new Label("Online: 42 players");
        playersLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #bdc3c7;");

        Button rulesButton = new Button("View Rules");
        rulesButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
        rulesButton.setOnAction(_ -> showGameRules(gameType));

        card.getChildren().addAll(iconPlaceholder, nameLabel, playersLabel, rulesButton);

        return card;
    }

    private void showGameRules(String gameType) {
        switch (gameType.toLowerCase()) {
            case "connectfour" -> SceneManager.loadScene("connectfour_rules.fxml");
            case "tictactoe" -> SceneManager.loadScene("tictactoe_rules.fxml");
            case "checkers" -> SceneManager.loadScene("checkers_rules.fxml");
        }
    }

    private VBox createRightPanel() {
        VBox rightPanel = new VBox(20);
        rightPanel.setPadding(new Insets(10, 0, 10, 20));
        rightPanel.setPrefWidth(250);

        // Leaderboard section
        VBox leaderboardSection = new VBox(10);
        leaderboardSection.setPadding(new Insets(15));
        leaderboardSection.setStyle("-fx-background-color: #34495e; -fx-background-radius: 5;");

        Label leaderboardTitle = new Label("Leaderboard");
        leaderboardTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        VBox leaderEntries = new VBox(8);
        for (int i = 1; i <= 5; i++) {
            HBox entry = new HBox(10);
            entry.setAlignment(Pos.CENTER_LEFT);

            Label rankLabel = new Label("#" + i);
            rankLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #e74c3c;");
            rankLabel.setPrefWidth(30);

            Label nameLabel = new Label("Player " + i);
            nameLabel.setStyle("-fx-text-fill: white;");

            Label scoreLabel = new Label(String.valueOf(1000 - (i * 50)));
            scoreLabel.setStyle("-fx-text-fill: #2ecc71;");

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            entry.getChildren().addAll(rankLabel, nameLabel, spacer, scoreLabel);
            leaderEntries.getChildren().add(entry);
        }

        Button viewMoreButton = new Button("View Full Leaderboard");
        viewMoreButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #3498db;");
        viewMoreButton.setOnAction(_ -> openLeaderboard());

        leaderboardSection.getChildren().addAll(leaderboardTitle, leaderEntries, viewMoreButton);

        // Active players section
        VBox activePlayersSection = new VBox(10);
        activePlayersSection.setPadding(new Insets(15));
        activePlayersSection.setStyle("-fx-background-color: #34495e; -fx-background-radius: 5;");

        Label activePlayersTitle = new Label("Active Players");
        activePlayersTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        VBox playerEntries = new VBox(8);
        for (int i = 1; i <= 5; i++) {
            HBox entry = new HBox(10);
            entry.setAlignment(Pos.CENTER_LEFT);

            Region statusIndicator = new Region();
            statusIndicator.setPrefSize(10, 10);
            statusIndicator.setStyle("-fx-background-color: " + (i % 2 == 0 ? "#2ecc71" : "#e74c3c") + "; -fx-background-radius: 5;");

            Label nameLabel = new Label("Friend " + i);
            nameLabel.setStyle("-fx-text-fill: white;");

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Button challengeButton = new Button("Challenge");
            challengeButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 10px;");
            int finalI = i;
            challengeButton.setOnAction(_ -> challengePlayer("Friend " + finalI));

            entry.getChildren().addAll(statusIndicator, nameLabel, spacer, challengeButton);
            playerEntries.getChildren().add(entry);
        }

        Button refreshButton = new Button("Refresh List");
        refreshButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #3498db;");

        activePlayersSection.getChildren().addAll(activePlayersTitle, playerEntries, refreshButton);

        rightPanel.getChildren().addAll(leaderboardSection, activePlayersSection);

        return rightPanel;
    }

    private HBox createStatusBar() {
        HBox statusBar = new HBox(15);
        statusBar.setPadding(new Insets(10, 5, 5, 5));
        statusBar.setAlignment(Pos.CENTER_LEFT);
        statusBar.setStyle("-fx-background-color: #1a2530; -fx-background-radius: 5;");

        Label statusLabel = new Label("Server Status: Online");
        statusLabel.setStyle("-fx-text-fill: #2ecc71;");

        Separator separator = new Separator();
        separator.setOrientation(javafx.geometry.Orientation.VERTICAL);
        separator.setStyle("-fx-background-color: #7f8c8d;");

        Label playersLabel = new Label("Players Online: 127");
        playersLabel.setStyle("-fx-text-fill: white;");

        Separator separator2 = new Separator();
        separator2.setOrientation(javafx.geometry.Orientation.VERTICAL);
        separator2.setStyle("-fx-background-color: #7f8c8d;");

        Label gamesLabel = new Label("Active Games: 45");
        gamesLabel.setStyle("-fx-text-fill: white;");

        statusBar.getChildren().addAll(statusLabel, separator, playersLabel, separator2, gamesLabel);

        return statusBar;
    }

    // Event handlers
    private void openUserProfile() {
        UserProfileWindow profileWindow = new UserProfileWindow(new Stage(), currentUser);
        profileWindow.show();
    }

    private void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginWindow2.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // No longer needed (GL Team)
//    private void startMatchmaking(String gameType) {
//        System.out.println("Starting matchmaking for " + gameType);
//        showMatchmakingDialog(gameType);
//    }

    private void findMatch(String gameSelection) {
        String gameType = gameSelection.toLowerCase().replace(" ", "");
        showMatchmakingDialog(gameType);
    }

    private void showMatchmakingDialog(String gameType) {
        Stage dialogStage = new Stage();
        VBox dialogContent = new VBox(15);
        dialogContent.setPadding(new Insets(20));
        dialogContent.setAlignment(Pos.CENTER);
        dialogContent.setStyle("-fx-background-color: #2c3e50;");

        Label titleLabel = new Label("Finding Opponent...");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setPrefSize(60, 60);

        Label statusLabel = new Label("Searching for players in your skill range");
        statusLabel.setStyle("-fx-text-fill: #bdc3c7;");

        final boolean[] cancelled = { false };

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        cancelButton.setOnAction(_ -> {
            cancelled[0] = true;       // mark as cancelled
            dialogStage.close();       // close the window
        });
        //cancelButton.setOnAction(_ -> dialogStage.close());

        dialogContent.getChildren().addAll(titleLabel, progressIndicator, statusLabel, cancelButton);

        // Simulate finding a match after 3 seconds
        Thread matchmakingThread = new Thread(() -> {

            try {
                Thread.sleep(3000);    // pretend to search 3 s
                javafx.application.Platform.runLater(() -> {
                    /* only continue if user did NOT press cancel */
                    if (!cancelled[0]) {
                        dialogStage.close();
                        startGame(gameType);
                    }
                });
            } catch (InterruptedException ignored) { }
        });
        matchmakingThread.setDaemon(true);
        matchmakingThread.start();

        Scene dialogScene = new Scene(dialogContent, 300, 200);
        dialogStage.setScene(dialogScene);
        dialogStage.setTitle("Matchmaking");
        dialogStage.setResizable(false);
        dialogStage.show();
    }

    private void startGame(String gameType) {
        switch (gameType) {
            case "ticTacToe", "tictactoe", "tic-tac-toe":
                new GameWindow(stage, new TicTacToeGame(true), currentUser);
                break;
            case "connectfour", "connectFour", "connect-four":
                new GameWindow(stage, new ConnectFourGame(1, 6, 7, 4, true), currentUser);
                break;
            case "checkers":
                Boolean checkersVsComputer = showCheckersModeDialog();
                if (checkersVsComputer == null) return;
                new GameWindow(stage, new CheckersGame(checkersVsComputer), currentUser);
                break;
            default:
                System.out.println("Unknown game type: " + gameType);
        }
    }

    private Boolean showConnectFourModeDialog() {
        Alert modeDialog = new Alert(Alert.AlertType.CONFIRMATION);
        modeDialog.setTitle("Choose Game Mode");
        modeDialog.setHeaderText("Select Connect Four Mode");
        modeDialog.setContentText("Do you want to play against the computer or another player?");

        ButtonType vsComputerButton = new ButtonType("Computer");
        ButtonType multiplayerButton = new ButtonType("Multiplayer (Same Device)");

        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        modeDialog.getButtonTypes().setAll(vsComputerButton, multiplayerButton, cancel);

        DialogPane dialogPane = modeDialog.getDialogPane();

        dialogPane.setStyle("-fx-background-color: #3498db");

        Node header = dialogPane.lookup(".header-panel");
        if (header != null) {
            header.setStyle("-fx-background-color: #3498db;");
        }
        Label content = (Label) dialogPane.lookup(".content.label");
        if (content != null) {
            content.setStyle("-fx-text-fill: white;");
        }
        Node buttonBar = dialogPane.lookup(".buttonBar");
        if (buttonBar != null) {
            buttonBar.setStyle("-fx-background-color: white;");
        }

        modeDialog.showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
            if (isNowShowing) {
                Button computerButton = (Button) dialogPane.lookupButton(vsComputerButton);
                Button multiButton = (Button) dialogPane.lookupButton(multiplayerButton);
                Button cancelButton = (Button) dialogPane.lookupButton(cancel);

                computerButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; ");
                multiButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
                cancelButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
            }
        });


        ButtonType result = modeDialog.showAndWait().orElse(cancel);
        if (result == cancel) {
            return null;
        }

/*/
        modeDialog.showAndWait().ifPresent(response -> {
            boolean vsComputer;
            if (response == vsComputerButton) {
                // Single Player
                vsComputer = true;
                new GameWindow(stage, new ConnectFourGame(1, 6, 7, 4, vsComputer), currentUser);
            } else {
                //Local Multiplayer
                vsComputer = false;
                new GameWindow(stage, new ConnectFourGame(1, 6, 7, 4, vsComputer), currentUser);
            }
        });

 */
        return result == vsComputerButton;
    }

    //This method shows the dialogue box
    // when tic-tac-toe is selected allowing the player to choose a multiplayer or computer game
    private Boolean showTicTacToeModeDialog() {
        Alert modeDialog = new Alert(Alert.AlertType.CONFIRMATION);
        modeDialog.setTitle("Choose Game Mode");
        modeDialog.setHeaderText("Select Tic Tac Toe Mode");
        modeDialog.setContentText("Do you want to play against the computer or another player?");

        ButtonType vsComputerButton = new ButtonType("Computer");
        ButtonType multiplayerButton = new ButtonType("Multiplayer (Same Device)");

        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        modeDialog.getButtonTypes().setAll(vsComputerButton, multiplayerButton, cancel);

        DialogPane dialogPane = modeDialog.getDialogPane();

        dialogPane.setStyle("-fx-background-color: #3498db");

        Node header = dialogPane.lookup(".header-panel");
        if (header != null) {
            header.setStyle("-fx-background-color: #3498db;");
        }
        Label content = (Label) dialogPane.lookup(".content.label");
        if (content != null) {
            content.setStyle("-fx-text-fill: white;");
        }
        Node buttonBar = dialogPane.lookup(".buttonBar");
        if (buttonBar != null) {
            buttonBar.setStyle("-fx-background-color: white;");
        }

        modeDialog.showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
            if (isNowShowing) {
                Button computerButton = (Button) dialogPane.lookupButton(vsComputerButton);
                Button multiButton = (Button) dialogPane.lookupButton(multiplayerButton);
                Button cancelButton = (Button) dialogPane.lookupButton(cancel);

                computerButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; ");
                multiButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
                cancelButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
            }
        });


        ButtonType result = modeDialog.showAndWait().orElse(cancel);
        if (result == cancel) {
            return null;
        }

        return result == vsComputerButton;
    }

    private void handleConnectFourClick() {
        Boolean vsComputer = showConnectFourModeDialog();
        if (vsComputer == null) {
            return;
        }
        showBoardOptionsDialog(vsComputer);
//        if (vsComputer) {
//            ConnectFourGame game = new ConnectFourGame(1, 6, 7, 4, true);
//            new GameWindow(stage, game, currentUser);
//        }else{
//            startMatchmaking("connectFour");
//        }
    }

    //This method makes a new tic-tac-toe game with the parameter
    // of whether the game is against the computer or not through the selected option in the dialogue box
    public boolean isComputerGameTTT;

    private void runTicTacToeGame() {
        Boolean vsComputer = showTicTacToeModeDialog();
        if (vsComputer == null) {          // user hit “Cancel” or closed the dialog
            return;                        // stay in main menu – nothing else happens
        }
        isComputerGameTTT = vsComputer;    // save for later use (startGame)
        TicTacToeGame ticTacToeGame = new TicTacToeGame(vsComputer);
        new GameWindow(stage, ticTacToeGame, currentUser);
    }

    // Game logic addition
    private Boolean showCheckersModeDialog() {
        Alert modeDialog = new Alert(Alert.AlertType.CONFIRMATION);
        modeDialog.setTitle("Choose Game Mode");
        modeDialog.setHeaderText("Select Checkers Mode");
        modeDialog.setContentText("Do you want to play against the computer or another player?");

        ButtonType vsComputerButton = new ButtonType("Computer");
        ButtonType multiplayerButton = new ButtonType("Multiplayer (Same Device)");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        modeDialog.getButtonTypes().setAll(vsComputerButton, multiplayerButton, cancel);

        DialogPane dialogPane = modeDialog.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #3498db");

        Node header = dialogPane.lookup(".header-panel");
        if (header != null) header.setStyle("-fx-background-color: #3498db;");

        Label content = (Label) dialogPane.lookup(".content.label");
        if (content != null) content.setStyle("-fx-text-fill: white;");

        Node buttonBar = dialogPane.lookup(".buttonBar");
        if (buttonBar != null) buttonBar.setStyle("-fx-background-color: white;");

        modeDialog.showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
            if (isNowShowing) {
                Button computerButton = (Button) dialogPane.lookupButton(vsComputerButton);
                Button multiButton = (Button) dialogPane.lookupButton(multiplayerButton);
                Button cancelButton = (Button) dialogPane.lookupButton(cancel);

                computerButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
                multiButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
                cancelButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
            }
        });

        Optional<ButtonType> result = modeDialog.showAndWait();
        if (result.isPresent()) {
            if (result.get() == vsComputerButton) return false;
            else if (result.get() == multiplayerButton) return true;
        }
        return null; // cancelled
    }


    /*
     *  THIS METHOD TAKES CARE OF CUSTOM BOARD SIZES WHICH WAS SUGGESTED IN FEATURE PROPOSAL
     * */
    private void showBoardOptionsDialog(boolean vsComputer) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Select board settings");
        dialog.setHeaderText(null);

        Label customHeader = new Label("Choose board size and Win Condition");
        customHeader.setStyle("-fx-text-fill: white; -fx-font-size: 16px;-fx-font-weight: bold;");
        VBox headerBox = new VBox(customHeader);
        headerBox.setPadding(new Insets(10));
        headerBox.setAlignment(Pos.CENTER_LEFT);
        dialog.getDialogPane().setHeader(headerBox);

        ComboBox<String> boardSize = new ComboBox<>();
        boardSize.getItems().addAll("6 x 7", "5 x 5", "4 x 5", "7 x 8", "8 x 8", "10 x 10");
        boardSize.setValue("6 x 7");

        ComboBox<Integer> goalBox = new ComboBox<>();
        goalBox.setDisable(true);

        Label noteLabel = new Label("Select a goal that fits the board size");
        noteLabel.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 12px;");

        boardSize.setOnAction(_ -> {
            String[] parts = boardSize.getValue().split(" x ");
            int rows = Integer.parseInt(parts[0].trim());
            int cols = Integer.parseInt(parts[1].trim());

            int maxGoal = Math.min(rows, cols);
            goalBox.getItems().clear();
            for (int i = 3; i <= maxGoal; i++) {
                goalBox.getItems().add(i);
            }

            goalBox.setDisable(false);
            goalBox.setValue(Math.min(4, maxGoal));
        });

        boardSize.getOnAction().handle(null);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.add(new Label("Board Size:"), 0, 0);
        grid.add(boardSize, 1, 0);
        grid.add(new Label("Goal Size:"), 0, 1);
        grid.add(goalBox, 1, 1);
        grid.add(noteLabel, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setStyle("-fx-background-color: #3498db;");

        dialog.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                String[] size = boardSize.getValue().split("x");
                int rows = Integer.parseInt(size[0].trim());
                int cols = Integer.parseInt(size[1].trim());
                int goal = goalBox.getValue();

                ConnectFourGame game = new ConnectFourGame(1, rows, cols, goal, vsComputer);
                new GameWindow(stage, game, currentUser);
            }
        });
    }


    private void openLeaderboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LeaderboardWindow.fxml"));
            Parent root = loader.load();
            LeaderBoardController controller = loader.getController();
            controller.setUser(currentUser);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("OMG - Leaderboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void challengePlayer(String playerName) {
        Alert challenge = new Alert(Alert.AlertType.CONFIRMATION);
        challenge.setTitle("Challenge Player");
        challenge.setHeaderText("Challenge " + playerName);
        challenge.setContentText("Select a game to challenge " + playerName);

        ButtonType ticTacToeButton = new ButtonType("Tic-Tac-Toe");
        ButtonType connectFourButton = new ButtonType("Connect Four");
        ButtonType checkersButton = new ButtonType("Checkers");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        challenge.getButtonTypes().setAll(ticTacToeButton, connectFourButton, checkersButton, cancelButton);

        challenge.showAndWait().ifPresent(response -> {
            if (response != cancelButton) {
                String gameType = response.getText().toLowerCase().replace(" ", "");
                showChallengeConfirmationDialog(playerName, gameType);
            }
        });
    }

    private void showChallengeConfirmationDialog(String playerName, String gameType) {
        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
        confirmation.setTitle("Challenge Sent");
        confirmation.setHeaderText("Challenge sent to " + playerName);
        confirmation.setContentText("Waiting for " + playerName + " to accept your challenge for a game of " + gameType + ".");
        confirmation.showAndWait();
    }

    public void show() {
        stage.show();
    }

}