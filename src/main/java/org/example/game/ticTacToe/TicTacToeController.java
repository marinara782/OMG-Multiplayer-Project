package org.example.game.ticTacToe;
//
//import javafx.animation.PauseTransition;
//import javafx.fxml.FXML;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//import javafx.util.Duration;
//import org.example.authentication.UserProfile;
//import org.example.gui.MainMenuWindow;
//
public class TicTacToeController {}
//    @FXML
//    private GridPane boardGrid; // Your game board grid
//
//    private TicTacToeGame game;
//    private GameModeSelection.GameMode selectedMode;
//    private final Stage primaryStage;
//    private final UserProfile currentUser;
//
//    // Initialize method
//    @FXML
//    private void initialize(TicTacToeGame game, Stage primaryStage, UserProfile currentUser) {
//        // Game mode and symbol selection
//        this.primaryStage = primaryStage;
//        this.currentUser = currentUser;
//        GameModeSelection gameModeDialog = new GameModeSelection();
//        selectedMode = gameModeDialog.showAndWait();
//
//        SymbolSelection symbolDialog = new SymbolSelection();
//        char playerSymbol = symbolDialog.showAndWait();
//
//        // Create game with mode and symbol
//        game = new TicTacToeGame(playerSymbol, selectedMode);
//
//        // Setup board click handlers
//        setupBoardClickHandlers();
//    }
//
//    private void setupBoardClickHandlers() {
//        for (Node node : boardGrid.getChildren()) {
//            if (node instanceof Button) {
//                node.setOnMouseClicked(event -> {
//                    // Get row and column from the button's location
//                    Integer row = GridPane.getRowIndex(node);
//                    Integer col = GridPane.getColumnIndex(node);
//
//                    makePlayerMove(row, col);
//                });
//            }
//        }
//    }
//
//    private void makePlayerMove(int row, int col) {
//        if (game.isPlayerTurn()) {
//            boolean moved = game.makeMove(row, col);
//
//            if (moved) {
//                // Update UI for player's move
//                updateBoardUI(row, col, game.getPlayerSymbol());
//
//                // Check game status
//                checkGameStatus();
//            }
//        }
//    }
//
//    private void checkGameStatus() {
//        char status = game.checkGameStatus();
//
//        if (status != ' ') {
//            // Game has ended
//            handleGameEnd(status);
//        } else {
//            // If game continues and it's computer mode,
//            // check if it's AI's turn
//            if (selectedMode == GameModeSelection.GameMode.HUMAN_VS_COMPUTER
//                    && !game.isPlayerTurn()) {
//                handleAIMove();
//            }
//        }
//    }
//
//    private void handleAIMove() {
//        // Add a small delay to make AI move feel more natural
//        PauseTransition pause = new PauseTransition(Duration.seconds(1));
//        pause.setOnFinished(event -> {
//            int[] aiMove = game.getAIMove();
//
//            if (aiMove != null) {
//                boolean moved = game.makeMove(aiMove[0], aiMove[1]);
//
//                if (moved) {
//                    updateBoardUI(aiMove[0], aiMove[1], game.getAISymbol());
//                    checkGameStatus();
//                }
//            }
//        });
//        pause.play();
//    }
//
//    private void updateBoardUI(int row, int col, char symbol) {
//        // Find the button at the specific row and column
//        for (Node node : boardGrid.getChildren()) {
//            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
//                if (node instanceof Button) {
//                    ((Button) node).setText(String.valueOf(symbol));
//                    break;
//                }
//            }
//        }
//    }
//
//    private void handleGameEnd(char status) {
//        // Disable all buttons
//        for (Node node : boardGrid.getChildren()) {
//            if (node instanceof Button) {
//                node.setDisable(true);
//            }
//        }
//
//        // Create game result dialog
//        Stage dialogStage = new Stage();
//        dialogStage.initModality(Modality.APPLICATION_MODAL);
//        dialogStage.setTitle("Game Over");
//
//        VBox dialogVBox = new VBox(20);
//        dialogVBox.setAlignment(Pos.CENTER);
//        dialogVBox.setPadding(new Insets(20));
//        dialogVBox.setStyle("-fx-background-color: #1a2530;"); // Match game board background
//
//        Label resultLabel = new Label();
//        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
//
//        Button returnToMenuButton = new Button("Return to Main Menu");
//        returnToMenuButton.setStyle(
//                "-fx-background-color: #4CAF50;" + // Green button
//                        "-fx-text-fill: white;" +
//                        "-fx-font-size: 16px;" +
//                        "-fx-background-radius: 5px;" +
//                        "-fx-padding: 10px 20px;"
//        );
//
//        // Determine game result and set styling
//        switch (status) {
//            case 'X':
//                if (game.getPlayerSymbol() == 'X') {
//                    resultLabel.setText("You Win!");
//                    resultLabel.setStyle("-fx-text-fill: #00FF00;"); // Bright bold green
//                } else {
//                    resultLabel.setText("You Lost!");
//                    resultLabel.setStyle("-fx-text-fill: #8B0000;"); // Dark bold red
//                }
//                break;
//
//            case 'O':
//                if (game.getPlayerSymbol() == 'O') {
//                    resultLabel.setText("You Win!");
//                    resultLabel.setStyle("-fx-text-fill: #00FF00;"); // Bright bold green
//                } else {
//                    resultLabel.setText("You Lost!");
//                    resultLabel.setStyle("-fx-text-fill: #8B0000;"); // Dark bold red
//                }
//                break;
//            case 'D':
//                resultLabel.setText("It's a Draw");
//                resultLabel.setStyle("-fx-text-fill: #808080;"); // Light gray
//                break;
//        }
//
//        // Return to main menu action
//        returnToMenuButton.setOnAction(e -> {
//            dialogStage.close();
//            if (primaryStage != null && currentUser != null) {
//                // Create a new MainMenuWindow with the stage and current user
//                new MainMenuWindow(primaryStage, currentUser);
//            }
//        });
//
//        dialogVBox.getChildren().addAll(resultLabel, returnToMenuButton);
//
//        Scene dialogScene = new Scene(dialogVBox, 300, 200);
//        dialogScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
//
//        // Optional: add a subtle border and shadow to the dialog
//        dialogVBox.setStyle(
//                "-fx-background-color: #1a2530;" +
//                        "-fx-background-radius: 10px;" +
//                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
//        );
//
//        dialogStage.initStyle(StageStyle.TRANSPARENT);
//        dialogStage.setScene(dialogScene);
//        dialogStage.show();
//    }
//
////    private void showEndGameDialog(String message) {
////        // Implement your end game dialog logic
////    }
//}