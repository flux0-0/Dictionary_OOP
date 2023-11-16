package com.example.dictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.annotation.Inherited;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController {
    @FXML
    private AnchorPane mainContent;
    @FXML
    private AnchorPane searchPane;
    @FXML
    private AnchorPane showAllPane;
    @FXML
    private AnchorPane gamePane;
    @FXML
    private AnchorPane historyPane;
    @FXML
    private AnchorPane settingPane;
    @FXML
    private AnchorPane translatePane;


    @FXML
    private SearchController searchController;
    @FXML
    private HistoryController historyController;
    @FXML
    private SettingController settingController;

    @FXML
    private Button searchButton;
    @FXML
    private Button translateButton;
    @FXML
    private Button gameButton;
    @FXML
    private Button mainHistoryButton;
    @FXML
    private Button settingButton;

    private void setMainContent(AnchorPane anchorPane) {
        mainContent.getChildren().setAll(anchorPane);
    }
    @FXML
    private void showSearchPane(ActionEvent event) {
        try {
            // Load FXML file của Search Scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Search.fxml"));
            Parent searchRoot = loader.load();

            // Tạo scene mới với root là searchRoot
            Scene searchScene = new Scene(searchRoot);

            // Lấy stage từ nút được nhấn
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Thiết lập scene mới cho stage
            primaryStage.setScene(searchScene);
            primaryStage.setTitle("Search");

            // Hiển thị scene mới
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void showSettingPane(ActionEvent event) {
        try {
            // Load FXML file của Search Scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Setting.fxml"));
            Parent settingRoot = loader.load();

            // Tạo scene mới với root là searchRoot
            Scene settingScene = new Scene(settingRoot);

            // Lấy stage từ nút được nhấn
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Thiết lập scene mới cho stage
            primaryStage.setScene(settingScene);
            primaryStage.setTitle("Setting");

            // Hiển thị scene mới
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void showTranslatePane() {
        translateButton.getStyleClass().add("active");
        setMainContent(translatePane);
    }

    @FXML
    public void showSettingPane() {
        settingButton.getStyleClass().add("active");
        setMainContent(settingPane);
    }
}

