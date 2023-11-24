package com.example.dictionary;

import Base.TexttoSpeech;
import Base.Translator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TranslateController {

    @FXML
    private TextArea area1;

    @FXML
    private TextField area2;

    @FXML
    private AnchorPane translateMainPane;

    @FXML
    private Button getLangFromFirst;

    @FXML
    private Button langFromSecond;

    @FXML
    private Button langFromThird;

    @FXML
    private Button langFromFourth;

    @FXML
    private Button langToSecond;

    @FXML
    private Button langToThird;

    @FXML
    private Button langToFourth;

    @FXML
    private TextField text1;

    @FXML
    private TextField text2;

    String nameFrom;
    String speakFrom;
    String languageFrom = "";

    @FXML
    void vie1() {
        text1.setText("vi");
        languageFrom = "vi";
        nameFrom = "Chi";
        speakFrom = "vi-vn";
    }

    @FXML
    void vie2() {
        text2.setText("vi");
        languageFrom = "vi";
        nameFrom = "Chi";
        speakFrom = "vi-vn";
    }


    @FXML
    void en1() {
        languageFrom = "en";
        text1.setText("en");
        nameFrom = "Linda";
        speakFrom = "en-gb";
    }

    @FXML
    void en2() {
        languageFrom = "en";
        text2.setText("en");
        nameFrom = "Linda";
        speakFrom = "en-gb";
    }

    @FXML
    void korea1() {
        text1.setText("ko");
        languageFrom = "ko";
        nameFrom = "Nari";
        speakFrom = "ko-kr";
    }

    @FXML
    void korea2() {
        text2.setText("ko");
        languageFrom = "ko";
        nameFrom = "Nari";
        speakFrom = "ko-kr";
    }

    @FXML
    void swap() throws IOException {
        if (text1 != null && text2 != null) {
            String temp = text1.getText();
            text1.setText(text2.getText());
            text2.setText(temp);
        }
        if (area1 != null && area2 != null) {
            area1.clear();
            String contentFromArea2 = area2.getText();
            area1.appendText(contentFromArea2);
            area2.clear();
        }
    }
    @FXML
    void translate() throws IOException {
        if (!Objects.equals(area1.getText(), "")) {
            area2.setText(Translator.translate( text1.getText(), text2.getText(), area1.getText()));
        }
    }
    @FXML
    void speak1() throws Exception {
        String textContent = text1.getText().trim();

        if (!textContent.isEmpty()) {
            if ("vi".equals(textContent)) {
                TexttoSpeech.Name = "Chi";
                TexttoSpeech.language = "vi-vn";
            } else if ("en".equals(textContent)) {
                TexttoSpeech.Name = "Linda";
                TexttoSpeech.language = "en-gb";
            } else if ("ko".equals(textContent)) {
                TexttoSpeech.Name = "Nari";
                TexttoSpeech.language = "ko-kr";
            } else {
                TexttoSpeech.Name = "Linda";
                TexttoSpeech.language = "en-gb";
            }
        }
        if (!Objects.equals(area1.getText(), "")) {
            TexttoSpeech.speakWord(area1.getText());
        }
    }
    @FXML
    void speak2() throws Exception {
        String textContent = text2.getText().trim();

        if (!textContent.isEmpty()) {
            if ("vi".equals(textContent)) {
                TexttoSpeech.Name = "Chi";
                TexttoSpeech.language = "vi-vn";
            } else if ("en".equals(textContent)) {
                TexttoSpeech.Name = "Linda";
                TexttoSpeech.language = "en-gb";
            } else if ("ko".equals(textContent)) {
                TexttoSpeech.Name = "Nari";
                TexttoSpeech.language = "ko-kr";
            } else {
                TexttoSpeech.Name = "Linda";
                TexttoSpeech.language = "en-gb";
            }
        }
        if (!Objects.equals(area2.getText(), "")) {
            TexttoSpeech.speakWord(area2.getText());
        }
    }

    @FXML
    private Button homeButton;
    @FXML
    public void handleHomeButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent mainRoot = loader.load();
            Scene mainScene = new Scene(mainRoot);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("Dictionary");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



