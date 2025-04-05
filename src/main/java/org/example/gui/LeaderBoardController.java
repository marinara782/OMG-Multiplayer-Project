package org.example.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.authentication.UserProfile;

import java.io.IOException;
import java.util.List;


public class LeaderBoardController {
    private Stage stage;
    public TableView leaderboardTable;//will need to include contents of table later
    public VBox gamesMenu;
    private UserProfile user; // Add the user instance variable

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public void handleSearch(ActionEvent actionEvent) {
    }

    public void handleSort(ActionEvent actionEvent) {
    }

//Navigation Bar, might make this into its own class
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

    // Collapse and Expand the VBox to show the game options
    public void handleGames(ActionEvent actionEvent) {
        if (gamesMenu.isVisible()) {
            gamesMenu.setVisible(false);
        } else {
            gamesMenu.setVisible(true);
        }
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

    public void handleProfile(ActionEvent actionEvent) {
        //will need to be added to go to the window
    }

    public void handleSettings(ActionEvent actionEvent) {
        //will need to be added to go to the window
    }

    public void handleLogout(ActionEvent actionEvent) {
        try {
            // Close the current leaderboard window
            Stage currentStage = (Stage) leaderboardTable.getScene().getWindow();
            currentStage.close();

            // Open the login window
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    }

