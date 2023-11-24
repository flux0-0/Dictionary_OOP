package com.example.dictionary;

import Base.TexttoSpeech;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.prefs.Preferences;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {


    @FXML
    private AnchorPane anchorPane;
    @FXML
    private RadioButton evButton;
    @FXML
    private RadioButton veButton;
    @FXML
    private Button quitButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button undoButton;
    @FXML
    private TabPane addTab;
    @FXML
    private TabPane editAndRemoveButton;
    @FXML
    private TextField addTextField;
    @FXML
    private TextField editAndRemoveTextField;
    private ToggleGroup toggleGroup;
    @FXML
    private ChoiceBox accentChoiceBox;
    private String[] option = {"Eng-UK", "Eng-US"};


    private void restorePreviousState() {
        // Khôi phục trạng thái trước đó từ Preferences
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        String savedOption = prefs.get("selectedOption", null);
        if (savedOption != null) {
            accentChoiceBox.setValue(savedOption);
        }
    }

    private void handleAccentChoiceBox(String selectedOption) {
        if ("Eng-UK".equals(selectedOption)) {
            TexttoSpeech.language = "en-gb";
            TexttoSpeech.Name = "Alice";
        } else if ("Eng-US".equals(selectedOption)) {
            TexttoSpeech.language = "en-us";
            TexttoSpeech.Name = "Linda";
        }
    }

    @FXML
    public void handleQuitButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) quitButton.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.setTitle("Dictionary");
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSaveButton() {
        // Lưu trạng thái hiện tại vào Preferences
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        String selectedOption = (String) accentChoiceBox.getValue();
        prefs.put("selectedOption", selectedOption);

        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accentChoiceBox.getItems().addAll(option);

        accentChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleAccentChoiceBox((String) newValue);
        });

        restorePreviousState();
    }
}
