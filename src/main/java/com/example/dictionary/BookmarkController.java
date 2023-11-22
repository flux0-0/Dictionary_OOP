package com.example.dictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.text.ChoiceFormat;
import java.util.*;

public class BookmarkController implements Initializable {

    private static final String DATA_FILE_PATH = "src/main/data/E_V (1).txt";
    private static final String BOOKMARK_FILE_PATH = "src/main/data/BookmarkList.txt";
    private static final String SPLITTING_CHARACTERS = "<html>";
    @FXML
    private ChoiceBox<String> filter;
    @FXML
    private ListView<String> bookmarkListView;
    @FXML
    private WebView bookmarkDefinitionView;
    @FXML
    private Button deleteButton;
    @FXML
    private Button backwardButton;
    @FXML
    private AnchorPane mainBookmarkPane;
    private Map<String, Word> data = new HashMap<>();
    private List<String> bookmarkedWords = new ArrayList<>();
    private String[] option = {"Recently added", "A-Z"};

    private void handleFilterChange(String selectedFilter) {
        List<String> sortedList = new ArrayList<>(bookmarkedWords);

        if ("Recently added".equals(selectedFilter)) {
            Collections.reverse(sortedList);
        } else if ("A-Z".equals(selectedFilter)) {
            Collections.sort(sortedList);
        }

        bookmarkListView.getItems().setAll(sortedList);
    }

    private void updateBookmarkListView() {
        bookmarkListView.getItems().setAll(bookmarkedWords);
    }
    private void displayWordDefinition(String selectedWord) {
        Word selectedWordObj = data.get(selectedWord);

        if (selectedWordObj != null) {
            WebEngine webEngine = bookmarkDefinitionView.getEngine();
            webEngine.loadContent(selectedWordObj.getDef());
        }
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
    public void loadBookmarkedWords() {
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKMARK_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (data.containsKey(line)) {
                    bookmarkedWords.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        handleFilterChange("Recently added");
    }
    private void showDeleteConfirmation(String selectedWord) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Xác nhận xóa từ khỏi Bookmark?");
        alert.setContentText(selectedWord);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Xóa từ khỏi danh sách và cập nhật BookmarkList.txt
            deleteWordAndUpdateFile(selectedWord);
        }
    }
    private void deleteWordAndUpdateFile(String wordToDelete) {
        bookmarkedWords.remove(wordToDelete);
        bookmarkListView.getItems().setAll(bookmarkedWords);

        updateBookmarkListFile();
    }
    private void updateBookmarkListFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKMARK_FILE_PATH))) {
            for (String word : bookmarkedWords) {
                writer.println(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDeleteButton(ActionEvent event) {
        String selectedWord = bookmarkListView.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            showDeleteConfirmation(selectedWord);
        }
    }
    @FXML
    public void handleBackwardButton(ActionEvent event) {
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filter.getItems().addAll(option);
        try {
            readData();
            loadBookmarkedWords();
        } catch (IOException e) {
            e.printStackTrace();
        }

        filter.setValue(option[0]);
        bookmarkListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> displayWordDefinition(newValue)
        );
        filter.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        handleFilterChange(newValue);
                    }
                }
        );
    }
}
