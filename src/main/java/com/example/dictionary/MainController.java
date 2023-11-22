package com.example.dictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
    private AnchorPane gamePane;
    @FXML
    private AnchorPane bookmarkPane;
    @FXML
    private AnchorPane settingPane;
    @FXML
    private AnchorPane translatePane;

    @FXML
    private SearchController searchController;
    @FXML
    private BookmarkController bookmarkController;
    @FXML
    private SettingController settingController;
    @FXML
    private TranslateController translateController;

    @FXML
    private Button searchButton;
    @FXML
    private Button translateButton;
    @FXML
    private Button gameButton;
    @FXML
    private Button bookmarkButton;
    @FXML
    private Button settingButton;

    private void setMainContent(AnchorPane anchorPane) {
        mainContent.getChildren().setAll(anchorPane);
    }
    @FXML
    private void showSearchPane(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Search.fxml"));
            Parent searchRoot = loader.load();

            Scene searchScene = new Scene(searchRoot);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            primaryStage.setScene(searchScene);
            primaryStage.setTitle("Search");

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void showSettingPane(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Setting.fxml"));
            Parent settingRoot = loader.load();

            Scene settingScene = new Scene(settingRoot);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            primaryStage.setScene(settingScene);
            primaryStage.setTitle("Setting");

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void showTranslatePane(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Translate.fxml"));
            Parent settingRoot = loader.load();

            Scene settingScene = new Scene(settingRoot);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            primaryStage.setScene(settingScene);
            primaryStage.setTitle("Translate");

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showBookmarkPane(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Bookmark.fxml"));
            Parent searchRoot = loader.load();

            Scene searchScene = new Scene(searchRoot);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            primaryStage.setScene(searchScene);
            primaryStage.setTitle("Bookmark");

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

