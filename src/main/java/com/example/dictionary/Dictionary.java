package com.example.dictionary;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class Dictionary extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("Main.fxml").openStream());

            // Load CSS
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/StyleCSS/Main.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle("Dictionary");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Word {
    private String word;
    private String def;
    private boolean isBookmarked;

    public Word(String word, String def) {
        this.word = word;
        this.def = def;
        this.isBookmarked = false;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public boolean isBookmark() {
        return isBookmarked;
    }
}