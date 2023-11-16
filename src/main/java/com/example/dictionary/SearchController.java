package com.example.dictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    private static final String DATA_FILE_PATH = "src/main/data/E_V.txt";
    //private static final String FXML_FILE_PATH = "./src/main/resources/com/example/dictionary/dictionary-view.fxml";
    private static final String SPLITTING_CHARACTERS = "<html>";
    private Map<String, Word> data = new HashMap<>();

    @FXML
    private ListView<String> listView;
    @FXML
    private WebView definitionView;
    @FXML
    private Button backwardButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button bookmarkButton;
    private Dictionary dictionary;

    public void initComponents() {
        this.listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    Word selectedWord = data.get(newValue.trim());
                    String definition = selectedWord.getDef();
                    this.definitionView.getEngine().loadContent(definition, "text/html");
                }
        );
    }
    public void readData() throws IOException {
        FileReader fis = new FileReader(DATA_FILE_PATH);
        BufferedReader br = new BufferedReader(fis);
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(SPLITTING_CHARACTERS);
            String word = parts[0];
            String definition = SPLITTING_CHARACTERS + parts[1];
            Word wordObj = new Word(word, definition);
            data.put(word, wordObj);
        }
    }
    public void loadWordList() {
        this.listView.getItems().addAll(data.keySet());
    }


    @FXML
    public void handleBackwardButton(ActionEvent event) {
        try {
            // Load FXML file của Search Scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent mainRoot = loader.load();

            // Tạo scene mới với root là searchRoot
            Scene mainScene = new Scene(mainRoot);

            // Lấy stage từ nút được nhấn
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Thiết lập scene mới cho stage
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("Dictionary");

            // Hiển thị scene mới
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            readData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loadWordList();
        initComponents();
    }

}
