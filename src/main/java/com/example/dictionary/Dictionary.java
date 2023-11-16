package com.example.dictionary;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.*;

public class Dictionary extends Application {

    private static final String DATA_FILE_PATH = "src/main/data/E_V.txt";
    //private static final String FXML_FILE_PATH = "./src/main/resources/com/example/dictionary/dictionary-view.fxml";
    private static final String SPLITTING_CHARACTERS = "<html>";
    private Map<String, Word> data = new HashMap<>();

    @FXML
    private ListView<String> listView;
    @FXML
    private WebView definitionView;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        AnchorPane root = fxmlLoader.load(getClass().getResourceAsStream("Main.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dictionary");
        primaryStage.show();
    }
}

class Word {
    private String word;
    private String def;

    public Word(String word, String def) {
        this.word = word;
        this.def = def;
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
}