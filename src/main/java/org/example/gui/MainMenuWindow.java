package org.example.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
//import org.example.matchmaking.Matchmaker;

public class MainMenuWindow {
    private Stage stage;
    private Scene scene;
    private BorderPane mainLayout;
    private UserProfile currentUser;
//    private Matchmaker matchmaker;

    public MainMenuWindow(Stage stage, UserProfile currentUser) {
        this.stage = stage;
        this.currentUser = currentUser;
//        this.matchmaker = new Matchmaker();
        initializeUI();
    }

    private void initializeUI() {
        mainLayout = new BorderPane();
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

        scene = new Scene(mainLayout, 1200, 800);
        stage.setTitle("OMG - Online Multiplayer Game Platform");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
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
        profileButton.setOnAction(e -> openUserProfile());

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // User info
        Label userLabel = new Label("Welcome, " + currentUser.getUsername());
        userLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        // Logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        logoutButton.setOnAction(e -> logout());

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
        ticTacToeCard.setOnMouseClicked(e -> startMatchmaking("ticTacToe"));

        // Connect Four Game Card
        VBox connectFourCard = createGameCard("Connect Four", "connectFour");
        connectFourCard.setOnMouseClicked(e -> startMatchmaking("connectFour"));

        // Checkers Game Card
        VBox checkersCard = createGameCard("Checkers", "checkers");
        checkersCard.setOnMouseClicked(e -> startMatchmaking("checkers"));

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
        findMatchButton.setOnAction(e -> findMatch(gameSelector.getValue()));

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

        card.getChildren().addAll(iconPlaceholder, nameLabel, playersLabel);

        return card;
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
        viewMoreButton.setOnAction(e -> openLeaderboard());

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
            challengeButton.setOnAction(e -> challengePlayer("Friend " + finalI));

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
        Login loginScreen = new Login(stage);
        loginScreen.show();
    }

    private void startMatchmaking(String gameType) {
        System.out.println("Starting matchmaking for " + gameType);
        showMatchmakingDialog(gameType);
    }

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

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        cancelButton.setOnAction(e -> dialogStage.close());

        dialogContent.getChildren().addAll(titleLabel, progressIndicator, statusLabel, cancelButton);

        // Simulate finding a match after 3 seconds
        Thread matchmakingThread = new Thread(() -> {
            try {
                Thread.sleep(3000);
                javafx.application.Platform.runLater(() -> {
                    dialogStage.close();
                    startGame(gameType);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
                new GameWindow(stage, new TicTacToeGame(), currentUser);
                break;
            case "connectfour", "connectFour", "connect-four":
                new GameWindow(stage, new ConnectFourGame(), currentUser);
                break;
            case "checkers":
                new GameWindow(stage, new CheckersGame(), currentUser);
                break;
            default:
                System.out.println("Unknown game type: " + gameType);
        }
    }

    private void openLeaderboard() {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.showLeaderboard(new Stage());
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