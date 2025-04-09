package org.example.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.authentication.UserProfile;
import java.util.HashMap;
import java.util.Map;

public class UserProfileWindow {
    private Stage stage;
    private Scene scene;
    private BorderPane mainLayout;
    private UserProfile userProfile;
    private TabPane tabPane;

    // Mock data for the profile
    private Map<String, Integer> gameStats;
    private Map<String, Integer> ranks;

    public UserProfileWindow(Stage stage, UserProfile userProfile) {
        this.stage = stage;
        this.userProfile = userProfile;
        initializeMockData();
        initializeUI();
    }

    private void initializeMockData() {
        gameStats = new HashMap<>();
        gameStats.put("Tic-Tac-Toe", 42);
        gameStats.put("Connect Four", 28);
        gameStats.put("Checkers", 16);

        ranks = new HashMap<>();
        ranks.put("Tic-Tac-Toe", 1250);
        ranks.put("Connect Four", 1423);
        ranks.put("Checkers", 1342);
    }

    private void initializeUI() {
        mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #2c3e50;");

        HBox header = createHeader();
        mainLayout.setTop(header);

        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab overviewTab = new Tab("Overview", createOverviewPane());
        Tab statsTab = new Tab("Game Stats", createStatsPane());
        Tab matchHistoryTab = new Tab("Match History", createMatchHistoryPane());
        Tab settingsTab = new Tab("Settings", createSettingsPane());

        tabPane.getTabs().addAll(overviewTab, statsTab, matchHistoryTab, settingsTab);

        mainLayout.setCenter(tabPane);

        scene = new Scene(mainLayout, 900, 700);
        stage.setTitle("User Profile - OMG Platform");
        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(500);
    }

    private Node createSettingsPane() {
        return new Label("Settings content goes here.");
    }

    private Node createMatchHistoryPane() {
        return new Label("Match history content goes here.");
    }

    private Node createStatsPane() {
        return new Label("Game stats content goes here.");
    }

    private HBox createHeader() {
        HBox header = new HBox(20);
        header.setPadding(new Insets(20));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #1a2530;");

        Region avatarPlaceholder = new Region();
        avatarPlaceholder.setPrefSize(80, 80);
        avatarPlaceholder.setStyle("-fx-background-color: #3498db; -fx-background-radius: 40;");

        VBox userInfo = new VBox(5);
        Label nameLabel = new Label(userProfile.getUsername());
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label joinDateLabel = new Label("Member since: January 15, 2023");
        joinDateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #bdc3c7;");

        Label statusLabel = new Label("Online");
        statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2ecc71;");

        userInfo.getChildren().addAll(nameLabel, joinDateLabel, statusLabel);

        VBox rankInfo = new VBox(5);
        rankInfo.setAlignment(Pos.CENTER);
        rankInfo.setPadding(new Insets(0, 0, 0, 40));

        Label rankLabel = new Label("Gold");
        rankLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #f39c12;");

        Label ratingLabel = new Label("1342");
        ratingLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");

        Label ratingTextLabel = new Label("Overall Rating");
        ratingTextLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #bdc3c7;");

        rankInfo.getChildren().addAll(rankLabel, ratingLabel, ratingTextLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        closeButton.setOnAction(e -> stage.close());

        header.getChildren().addAll(avatarPlaceholder, userInfo, rankInfo, spacer, closeButton);

        return header;
    }

    private VBox createOverviewPane() {
        VBox overviewPane = new VBox(20);
        overviewPane.setPadding(new Insets(20));
        overviewPane.setStyle("-fx-background-color: #2c3e50;");

        HBox summary = new HBox(20);
        summary.setAlignment(Pos.CENTER);

        VBox totalGamesBox = createStatBox("Total Games", "86", "#3498db");
        VBox winRateBox = createStatBox("Win Rate", "62%", "#2ecc71");
        VBox highestRankBox = createStatBox("Highest Rank", "Gold II", "#f39c12");

        summary.getChildren().addAll(totalGamesBox, winRateBox, highestRankBox);

        TitledPane chartContainer = new TitledPane();
        chartContainer.setText("Game Distribution");
        chartContainer.setCollapsible(false);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Games Played by Type");
        barChart.setLegendVisible(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : gameStats.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().add(series);
        chartContainer.setContent(barChart);

        overviewPane.getChildren().addAll(summary, chartContainer);
        return overviewPane;
    }

    private VBox createStatBox(String title, String value, String color) {
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 10;");

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        box.getChildren().addAll(valueLabel, titleLabel);
        return box;
    }

    public void show() {
        stage.show();
    }
}