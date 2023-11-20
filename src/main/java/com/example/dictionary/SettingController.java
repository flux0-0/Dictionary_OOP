package com.example.dictionary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;

public class SettingController {


    @FXML
    private RadioButton evButton;
    @FXML
    private RadioButton veButton;
    @FXML
    private Button quitButton;
    @FXML
    private Button doneButton;
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
    public void handleSelectButton() {
        toggleGroup = new ToggleGroup();

        // Gắn ToggleGroup cho cả hai RadioButton
        evButton.setToggleGroup(toggleGroup);
        veButton.setToggleGroup(toggleGroup);

        // Đặt sự kiện xử lý khi thay đổi lựa chọn
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                // Không có nút nào được chọn
                System.out.println("No button selected");
            } else if (newValue == evButton) {
                System.out.println("Button 1 selected");
                // Thêm logic xử lý khi nút 1 được chọn
            } else if (newValue == veButton) {
                System.out.println("Button 2 selected");
                // Thêm logic xử lý khi nút 2 được chọn
            }
        });
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


    // Phương thức để hiển thị hộp thoại cảnh báo
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
