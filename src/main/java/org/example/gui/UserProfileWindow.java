package org.example.gui;

// JavaFX imports for UI components and layout management
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.authentication.Login;
import org.example.authentication.UserProfile;

import java.util.HashMap;
import java.util.Map;

//Represents the user profile window for displaying user-related stats and settings.

public class UserProfileWindow {
    private final Stage stage; // Reference to the main application stage (window)
    private final UserProfile userProfile; // The current user's profile
    private Scene scene; // The scene graph for the profile window
    private BorderPane mainLayout; // The main layout container
    private TabPane tabPane; // Container for organizing content in tabs

    // Mock data for games and their statistics
    private Map<String, Integer> gameStats;
    private Map<String, Integer> ranks;

    // Constructor initializes the UI and mock data
    public UserProfileWindow(Stage stage, UserProfile userProfile) {
        this.stage = stage;
        this.userProfile = userProfile;
        initializeMockData();  // Sets up temporary stats data
        initializeUI();        // Builds the UI
    }

    // Creates mock statistics and ranking values for each game
    private void initializeMockData() {
        gameStats = Map.of(
                "Tic-Tac-Toe", 10,
                "Connect Four", 9,
                "Checkers", 7
        );

        ranks = Map.of(
                "Tic-Tac-Toe", 1250,
                "Connect Four", 1423,
                "Checkers", 1342
        );
    }

    // Constructs the user interface including layout, tabs, and scene
    private void initializeUI() {
        mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #2c3e50;"); // Set dark theme background

        mainLayout.setTop(createHeader()); // Adds a header section

        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); // Disable closing tabs

        // Add tabs for different sections
        tabPane.getTabs().addAll(
                new Tab("Overview", createOverviewPane()),
                new Tab("Game Stats", createStatsPane()),
                new Tab("Match History", createMatchHistoryPane()),
                new Tab("Settings", createSettingsPane())
        );

        mainLayout.setCenter(tabPane); // Place the tab content in the center

        scene = new Scene(mainLayout, 900, 700);
        stage.setTitle("User Profile - OMG Platform");
        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(500);
    }

    // Builds the top header with avatar, name, status, and overall rank
    private HBox createHeader() {
        HBox header = new HBox(20);
        header.setPadding(new Insets(15));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #1a2530;");

        // Simulated circular avatar
        Region avatar = new Region();
        avatar.setPrefSize(60, 60);
        avatar.setStyle("-fx-background-color: #3498db; -fx-background-radius: 30;");

        // Shows user's name and online status
        VBox userInfo = new VBox(5);
        Label nameLabel = new Label(userProfile.getUsername());
        nameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label statusLabel = new Label("Online");
        statusLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #2ecc71;");
        userInfo.getChildren().addAll(nameLabel, statusLabel);

        // Shows overall rank summary
        VBox rankInfo = new VBox(2);
        rankInfo.setAlignment(Pos.CENTER_RIGHT);
        rankInfo.setPadding(new Insets(0, 20, 0, 20));
        rankInfo.getChildren().addAll(
                createStyledLabel("Gold", 14, "#f39c12"),
                createStyledLabel("1342", 24, "white"),
                createStyledLabel("Overall rating", 10, "#bdc3c7")
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS); // Push rankInfo to the right

        header.getChildren().addAll(avatar, userInfo, spacer, rankInfo);
        return header;
    }

    // Helper function to create a label with custom styles
    private Label createStyledLabel(String text, int fontSize, String color) {
        Label label = new Label(text);
        label.setStyle(String.format("-fx-font-size: %dpx; -fx-text-fill: %s;", fontSize, color));
        return label;
    }

    // Overview tab showing a summary and a pie chart of games played
    private VBox createOverviewPane() {
        VBox pane = createStyledVBox();

        Label welcomeLabel = new Label("Welcome back!");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");

        GridPane statsGrid = new GridPane(); // Display stats like win rate, etc.
        statsGrid.setHgap(20);
        statsGrid.setVgap(10);
        statsGrid.setPadding(new Insets(10, 0, 20, 0));
        addStatRow(statsGrid); // Fill the grid with data

        // Add pie chart showing the distribution of games played
        PieChart pieChart = new PieChart();
        gameStats.forEach((k, v) -> pieChart.getData().add(new PieChart.Data(k, v)));
        TitledPane piePane = new TitledPane("Game Distribution", pieChart);
        piePane.setCollapsible(false);

        pane.getChildren().addAll(welcomeLabel, statsGrid, piePane);
        return pane;
    }

    // Adds rows to the stats grid with values like win rate, total games, etc.
    private void addStatRow(GridPane grid) {
        int totalGames = 0, wins = 0;
        String[] matches = getAllMatches();

        for (String match : matches) {
            totalGames++;
            if (match.contains("Won")) wins++;
        }

        int winRate = totalGames == 0 ? 0 : (int) Math.round((wins * 100.0) / totalGames);

        String[] labels = {"Total Games", "Win Rate", "Highest Rank", "Last Played"};
        String[] values = {
                String.valueOf(totalGames),
                winRate + "%",
                "Platinum III",
                "2 hours ago"
        };

        // Populate the grid with label/value pairs
        for (int i = 0; i < labels.length; i++) {
            Label label = new Label(labels[i]);
            label.setStyle("-fx-font-size: 14px; -fx-text-fill: #bdc3c7;");
            GridPane.setConstraints(label, i, 0);

            Label value = new Label(values[i]);
            value.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");
            GridPane.setConstraints(value, i, 1);

            grid.getChildren().addAll(label, value);
        }
    }

    // Returns mock list of matches played, used across multiple views
    private String[] getAllMatches() {
        return new String[]{
                "Won against PlayerA in Tic-Tac-Toe",
                "Lost against PlayerB in Connect Four",
                "Draw with PlayerC in Checkers",
                "Won against PlayerD in Tic-Tac-Toe",
                "Lost against PlayerE in Connect Four",
                "Won against PlayerF in Checkers",
                "Won against PlayerG in Connect Four",
                "Draw with PlayerH in Tic-Tac-Toe",
                "Lost against PlayerI in Checkers",
                "Won against PlayerJ in Tic-Tac-Toe",
                "Won against PlayerK in Connect Four",
                "Draw with PlayerL in Checkers",
                "Lost against PlayerM in Tic-Tac-Toe",
                "Won against PlayerN in Connect Four",
                "Lost against PlayerO in Checkers",
                "Won against PlayerP in Tic-Tac-Toe",
                "Won against PlayerQ in Connect Four",
                "Lost against PlayerR in Checkers",
                "Won against PlayerS in Tic-Tac-Toe",
                "Won against PlayerT in Checkers",
                "Won against PlayerU in Connect Four",
                "Lost against PlayerV in Tic-Tac-Toe",
                "Draw with PlayerW in Checkers",
                "Won against PlayerX in Tic-Tac-Toe",
                "Won against PlayerY in Connect Four",
                "Lost against PlayerZ in Checkers"
        };
    }

    // Builds the Game Stats tab with tabs for each individual game
    private VBox createStatsPane() {
        VBox pane = createStyledVBox();
        Label statsTitle = new Label("Game Stats");
        statsTitle.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        TabPane gameTabs = new TabPane();
        gameTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Add a tab for each game and all games combined
        gameTabs.getTabs().addAll(
                new Tab("All Games", createStatsChart("All Games")),
                new Tab("Tic-Tac-Toe", createStatsChart("Tic-Tac-Toe")),
                new Tab("Connect Four", createStatsChart("Connect Four")),
                new Tab("Checkers", createStatsChart("Checkers"))
        );

        pane.getChildren().addAll(statsTitle, gameTabs);
        return pane;
    }

    // Creates pie chart for a selected game showing win/loss/draw breakdown
    private VBox createStatsChart(String gameType) {
        VBox outerBox = createStyledVBox(); // Dark background as the outer container

        VBox whiteCard = new VBox(10); // White inner card-style container
        whiteCard.setPadding(new Insets(20));
        whiteCard.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2);");

        Label title = new Label("Win Rate for " + gameType);
        title.setStyle("-fx-font-size: 16px; -fx-text-fill: #2c3e50; -fx-font-weight: bold;");

        PieChart chart = new PieChart();
        Map<String, Integer> results = calculateResults(gameType);
        results.forEach((k, v) -> chart.getData().add(new PieChart.Data(k, v)));

        whiteCard.getChildren().addAll(title, chart);
        outerBox.getChildren().add(whiteCard);
        return outerBox;

    }

    // Calculates match outcomes for a specific game or all games
    private Map<String, Integer> calculateResults(String type) {
        Map<String, Integer> results = new HashMap<>();
        int win = 0, loss = 0, draw = 0;

        for (String match : getAllMatches()) {
            if ("All Games".equals(type) || match.contains(type)) {
                if (match.contains("Won")) win++;
                else if (match.contains("Lost")) loss++;
                else if (match.contains("Draw")) draw++;
            }
        }

        results.put("Wins", win);
        results.put("Losses", loss);
        results.put("Draws", draw);
        return results;
    }

    // Reusable VBox with padding and background color
    private VBox createStyledVBox() {
        VBox vBox = new VBox(20);
        vBox.setPadding(new Insets(20));
        vBox.setStyle("-fx-background-color: #2c3e50;");
        return vBox;
    }

    // Match history tab showing a ListView of matches, filtered by game
    private Node createMatchHistoryPane() {
        VBox pane = createStyledVBox();
        Label title = new Label("Match History");
        title.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        TabPane tabs = new TabPane();
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabs.getTabs().addAll(
                new Tab("All Games", createMatchList("")),
                new Tab("Tic-Tac-Toe", createMatchList("Tic-Tac-Toe")),
                new Tab("Connect Four", createMatchList("Connect Four")),
                new Tab("Checkers", createMatchList("Checkers"))
        );

        pane.getChildren().addAll(title, tabs);
        return pane;
    }

    // Builds the match list view for a given game filter
    private Node createMatchList(String filter) {
        VBox box = createStyledVBox();
        Label title = new Label(filter.isEmpty() ? "Recent Matches" : "Matches in " + filter);
        title.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        ListView<String> listView = new ListView<>();
        listView.setPrefHeight(400);
        listView.setStyle("-fx-control-inner-background: #1a2530; -fx-text-fill: white;");

        // Filter matches based on the game
        for (String match : getAllMatches()) {
            if (filter.isEmpty() || match.contains(filter)) {
                listView.getItems().add(match);
            }
        }

        box.getChildren().addAll(title, listView);
        return box;
    }

    // Settings tab with options to change password and manage notifications, plus bug report
    private Node createSettingsPane() {
        VBox pane = createStyledVBox();
        pane.setAlignment(Pos.CENTER);

        Label title = new Label("Settings");
        title.setStyle("-fx-font-size: 26px; -fx-text-fill: white; -fx-font-weight: bold;");

        Button changePassword = new Button("Change Password");
        changePassword.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px;");
        changePassword.setPrefWidth(250);
        changePassword.setOnAction(e -> openChangePasswordDialog());

        Button notifications = new Button("Notification Settings");
        notifications.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px;");
        notifications.setPrefWidth(250);
        notifications.setOnAction(e -> openNotificationSettings());

        // --- Bug Report Section ---
        Button reportBugButton = new Button("Report a Bug");
        reportBugButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 16px;");

        Label bugTypeLabel = new Label("Select Bug Type(s):");
        bugTypeLabel.setStyle("-fx-text-fill: white;");

        CheckBox uiBug = new CheckBox("UI Issue");
        uiBug.setStyle("-fx-text-fill: white;");
        CheckBox gameLogicBug = new CheckBox("Game Logic");
        gameLogicBug.setStyle("-fx-text-fill: white;");
        CheckBox performanceBug = new CheckBox("Performance");
        performanceBug.setStyle("-fx-text-fill: white;");
        CheckBox networkBug = new CheckBox("Network");
        networkBug.setStyle("-fx-text-fill: white;");
        CheckBox otherBug = new CheckBox("Other");
        otherBug.setStyle("-fx-text-fill: white;");

        VBox bugTypeBox = new VBox(5, bugTypeLabel, uiBug, gameLogicBug, performanceBug, networkBug, otherBug);

        bugTypeBox.setAlignment(Pos.CENTER_LEFT);
        bugTypeBox.setVisible(false);

        // Bug description (initially hidden)
        TextArea bugTextArea = new TextArea();
        bugTextArea.setPromptText("Describe the bug here...");
        bugTextArea.setWrapText(true);
        bugTextArea.setPrefRowCount(4);
        bugTextArea.setMaxWidth(500);
        bugTextArea.setVisible(false);

        Button submitBugButton = new Button("Submit Bug Report");
        submitBugButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 14px;");
        submitBugButton.setVisible(false);

        // Reveal bug type checkboxes on click
        reportBugButton.setOnAction(e -> {
            bugTypeBox.setVisible(true);
        });

        // Show bug textarea and submit button when a box is selected
        ChangeListener<Boolean> selectionChanged = (obs, oldVal, newVal) -> {
            boolean atLeastOneSelected = uiBug.isSelected() || gameLogicBug.isSelected()
                    || performanceBug.isSelected() || networkBug.isSelected() || otherBug.isSelected();

            bugTextArea.setVisible(atLeastOneSelected);
            submitBugButton.setVisible(atLeastOneSelected);
        };

        uiBug.selectedProperty().addListener(selectionChanged);
        gameLogicBug.selectedProperty().addListener(selectionChanged);
        performanceBug.selectedProperty().addListener(selectionChanged);
        networkBug.selectedProperty().addListener(selectionChanged);
        otherBug.selectedProperty().addListener(selectionChanged);

        // Submit button logic
        submitBugButton.setOnAction(e -> {
            String bugText = bugTextArea.getText().trim();
            StringBuilder bugTypesSelected = new StringBuilder();

            if (uiBug.isSelected()) bugTypesSelected.append("UI Issue, ");
            if (gameLogicBug.isSelected()) bugTypesSelected.append("Game Logic, ");
            if (performanceBug.isSelected()) bugTypesSelected.append("Performance, ");
            if (networkBug.isSelected()) bugTypesSelected.append("Network, ");
            if (otherBug.isSelected()) bugTypesSelected.append("Other, ");

            if (bugText.isEmpty()) {
                showAlert("Please describe the bug before submitting.");
                return;
            }

            if (bugTypesSelected.length() == 0) {
                showAlert("Please select at least one bug type.");
                return;
            }

            String selectedTypes = bugTypesSelected.substring(0, bugTypesSelected.length() - 2);

            System.out.println("Bug Submitted:\nDescription: " + bugText + "\nTypes: " + selectedTypes);

            // Clear inputs
            bugTextArea.clear();
            uiBug.setSelected(false);
            gameLogicBug.setSelected(false);
            performanceBug.setSelected(false);
            networkBug.setSelected(false);
            otherBug.setSelected(false);
            bugTextArea.setVisible(false);
            submitBugButton.setVisible(false);
            bugTypeBox.setVisible(false);

            showSuccess("Thank you for your report! Our team will review it.");
        });

        VBox bugSection = new VBox(10, reportBugButton, bugTypeBox, bugTextArea, submitBugButton);
        bugSection.setAlignment(Pos.CENTER);

        pane.getChildren().addAll(title, changePassword, notifications, new Separator(), bugSection);
        return pane;
    }





    // Dialog window for changing the user's password
    private void openChangePasswordDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Change Password");

        ButtonType changeButtonType = new ButtonType("Change", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(changeButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("New Password");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");


        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(new Label("New Password:"), 0, 2);
        grid.add(newPasswordField, 1, 2);
        grid.add(new Label("Confirm Password:"), 0, 3);
        grid.add(confirmPasswordField, 1, 3);


        dialog.getDialogPane().setContent(grid);

        Node changeButton = dialog.getDialogPane().lookupButton(changeButtonType);
        changeButton.addEventFilter(ActionEvent.ACTION, event -> {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String newPassword = newPasswordField.getText().trim();
            String confirmPassword = confirmPasswordField.getText().trim();


            if (email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                showAlert("Please fill in all fields.");
                event.consume();
                return;
            }

            if (newPassword.length() < 8) {
                showAlert("Password must be at least 8 characters long.");
                event.consume();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                showAlert("Passwords do not match.");
                event.consume();
                return;
            }

            Login login = new Login();
            try {
                boolean success = login.forgot_password(username, email, newPassword);
                if (success) {
                    showAlert("Password changed successfully!");
                } else {
                    showAlert("Email and username do not match or user doesn't exist.");
                    event.consume(); // Keep the dialog open
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("An error occurred. Please try again.");
                event.consume();
            }
        });

        dialog.showAndWait();
    }


    // Opens notification preference toggles
    private void openNotificationSettings() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Notification Settings");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        CheckBox emailNotif = new CheckBox("Email Notifications");
        CheckBox appNotif = new CheckBox("In-App Notifications");

        emailNotif.setSelected(true);
        appNotif.setSelected(true);

        VBox content = new VBox(10, emailNotif, appNotif);
        content.setPadding(new Insets(20));
        dialog.getDialogPane().setContent(content);

        dialog.showAndWait();
    }

    // Displays a simple warning alert
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }
    // Displays a confirmation alert
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Call this to display the profile window
    public void show() {
        stage.show();
    }
}