package org.example.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.authentication.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class UserProfileWindow {
    private final Stage stage;
    private final UserProfile userProfile;
    private Scene scene;
    private BorderPane mainLayout;
    private TabPane tabPane;

    private Map<String, Integer> gameStats;
    private Map<String, Integer> ranks;

    public UserProfileWindow(Stage stage, UserProfile userProfile) {
        this.stage = stage;
        this.userProfile = userProfile;
        initializeMockData();
        initializeUI();
    }

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



    private void initializeUI() {
        mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #2c3e50;");

        mainLayout.setTop(createHeader());

        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        tabPane.getTabs().addAll(
                new Tab("Overview", createOverviewPane()),
                new Tab("Game Stats", createStatsPane()),
                new Tab("Match History", createMatchHistoryPane()),
                new Tab("Settings", createSettingsPane())
        );

        mainLayout.setCenter(tabPane);

        scene = new Scene(mainLayout, 900, 700);
        stage.setTitle("User Profile - OMG Platform");
        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(500);
    }

    private HBox createHeader() {
        HBox header = new HBox(20);
        header.setPadding(new Insets(15));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #1a2530;");

        Region avatar = new Region();
        avatar.setPrefSize(60, 60);
        avatar.setStyle("-fx-background-color: #3498db; -fx-background-radius: 30;");

        VBox userInfo = new VBox(5);
        Label nameLabel = new Label(userProfile.getUsername());
        nameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label statusLabel = new Label("Online");
        statusLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #2ecc71;");
        userInfo.getChildren().addAll(nameLabel, statusLabel);

        VBox rankInfo = new VBox(2);
        rankInfo.setAlignment(Pos.CENTER_RIGHT);
        rankInfo.setPadding(new Insets(0, 20, 0, 20));

        rankInfo.getChildren().addAll(
                createStyledLabel("Gold", 14, "#f39c12"),
                createStyledLabel("1342", 24, "white"),
                createStyledLabel("Overall rating", 10, "#bdc3c7")
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(avatar, userInfo, spacer, rankInfo);
        return header;
    }

    private Label createStyledLabel(String text, int fontSize, String color) {
        Label label = new Label(text);
        label.setStyle(String.format("-fx-font-size: %dpx; -fx-text-fill: %s;", fontSize, color));
        return label;
    }

    private VBox createOverviewPane() {
        VBox pane = createStyledVBox();

        Label welcomeLabel = new Label("Welcome back!");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");

        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(20);
        statsGrid.setVgap(10);
        statsGrid.setPadding(new Insets(10, 0, 20, 0));

        addStatRow(statsGrid);

        PieChart pieChart = new PieChart();
        gameStats.forEach((k, v) -> pieChart.getData().add(new PieChart.Data(k, v)));

        TitledPane piePane = new TitledPane("Game Distribution", pieChart);
        piePane.setCollapsible(false);

        pane.getChildren().addAll(welcomeLabel, statsGrid, piePane);
        return pane;
    }

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



    private VBox createStatsPane() {
        VBox pane = createStyledVBox();
        Label statsTitle = new Label("Game Stats");
        statsTitle.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        TabPane gameTabs = new TabPane();
        gameTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        gameTabs.getTabs().addAll(
                new Tab("All Games", createStatsChart("All Games")),
                new Tab("Tic-Tac-Toe", createStatsChart("Tic-Tac-Toe")),
                new Tab("Connect Four", createStatsChart("Connect Four")),
                new Tab("Checkers", createStatsChart("Checkers"))
        );

        pane.getChildren().addAll(statsTitle, gameTabs);
        return pane;
    }

    private VBox createStatsChart(String gameType) {
        VBox box = createStyledVBox();

        Label title = new Label("Win Rate for " + gameType);
        title.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        PieChart chart = new PieChart();
        Map<String, Integer> results = calculateResults(gameType);
        results.forEach((k, v) -> chart.getData().add(new PieChart.Data(k, v)));

        box.getChildren().addAll(title, chart);
        return box;
    }

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


    private VBox createStyledVBox() {
        VBox vBox = new VBox(20);
        vBox.setPadding(new Insets(20));
        vBox.setStyle("-fx-background-color: #2c3e50;");
        return vBox;
    }

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

    private Node createMatchList(String filter) {
        VBox box = createStyledVBox();
        Label title = new Label(filter.isEmpty() ? "Recent Matches" : "Matches in " + filter);
        title.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        ListView<String> listView = new ListView<>();
        listView.setPrefHeight(400);
        listView.setStyle("-fx-control-inner-background: #1a2530; -fx-text-fill: white;");

        for (String match : getAllMatches()) {
            if (filter.isEmpty() || match.contains(filter)) {
                listView.getItems().add(match);
            }
        }

        box.getChildren().addAll(title, listView);
        return box;
    }


    private Node createSettingsPane() {
        VBox pane = createStyledVBox();

        Label title = new Label("Settings");
        title.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        Button changePassword = new Button("Change Password");
        Button notifications = new Button("Notification Settings");

        changePassword.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        notifications.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

        pane.getChildren().addAll(title, changePassword, notifications);
        return pane;
    }

    public void show() {
        stage.show();
    }
}
