package com.example.dictionary;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Dictionary;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SearchController implements Initializable {
    private static final String DATA_FILE_PATH = "src/main/data/E_V (1).txt";
    private static final String BOOKMARK_FILE_PATH = "src/main/data/BookmarkList.txt";
    private static final String SPLITTING_CHARACTERS = "<html>";
    private Map<String, Word> data = new HashMap<>();
    private Set<String> bookmarkedWords = new HashSet<>();

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
    private CheckBox bookmarkCheckBox;
    @FXML
    private TextField searchBar;

    private void addWordToBookmark(Word word) {
        bookmarkedWords.add(word.getWord());
        updateCheckBoxState();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKMARK_FILE_PATH, true))) {
            writer.write(word.getWord() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeWordFromBookmark(Word word) {
        bookmarkedWords.remove(word.getWord());
        updateCheckBoxState();
        try {
            Set<String> lines = Files.lines(Paths.get(BOOKMARK_FILE_PATH))
                    .filter(line -> !line.equals(word.getWord()))
                    .collect(Collectors.toSet());

            Files.write(Paths.get(BOOKMARK_FILE_PATH), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCheckBoxState() {
        Word selectedWord = getSelectedWord();
        if (selectedWord != null) {
            bookmarkCheckBox.setSelected(bookmarkedWords.contains(selectedWord.getWord()));
        }
    }
    private Word getSelectedWord() {
        String selectedWordKey = listView.getSelectionModel().getSelectedItem();
        return data.get(selectedWordKey);
    }

    private void saveEditsToFile(String filePath, String editType, String word, String definition) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            switch (editType) {
                case "DELETE":
                    // Ghi thông tin về thao tác xóa vào tệp
                    writer.write("DELETE:" + word);
                    break;
                case "EDIT":
                    // Ghi thông tin về thao tác chỉnh sửa vào tệp
                    writer.write("EDIT:" + word + SPLITTING_CHARACTERS + definition);
                    break;
                // Các loại thao tác khác có thể được xử lý ở đây
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void applyEditsFromFile(String editsFilePath) throws IOException {
        FileReader editsFileReader = new FileReader(editsFilePath);
        BufferedReader editsBufferedReader = new BufferedReader(editsFileReader);

        String editLine;
        while ((editLine = editsBufferedReader.readLine()) != null) {
            String[] editParts = editLine.split(":");
            String editType = editParts[0].trim();
            switch (editType) {
                case "DELETE":
                    String deletedWord = editParts[1].trim();
                    data.remove(deletedWord);
                    break;
                case "EDIT":
                    String editedDefinition = editParts[1].trim();
                    String editedWord = editedDefinition.split(SPLITTING_CHARACTERS)[0];
                    data.put(editedWord, new Word(editedWord, editedDefinition));
                    break;
            }
        }
        editsBufferedReader.close();
    }

    public void initComponents() {
        Map<String, Word> sortedData = new TreeMap<>(data);
        this.listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    Word selectedWord = sortedData.get(newValue.trim());
                    String definition = selectedWord.getDef();
                    this.definitionView.getEngine().loadContent(definition, "text/html");
                }
        );
        this.listView.getItems().clear();
        this.listView.getItems().addAll(sortedData.keySet());
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
        applyEditsFromFile("src/main/data/editedE_V.txt");
    }
    public void loadWordList() {
        this.listView.getItems().addAll(data.keySet());
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

    @FXML
    public void handleSearchBar(KeyEvent event) {
        String keyword = searchBar.getText().toLowerCase();

        Predicate<String> filterCondition = word -> word.toLowerCase().contains(keyword);

        FilteredList<String> filteredList = new FilteredList<>(FXCollections.observableArrayList(data.keySet()), filterCondition);

        listView.setItems(filteredList);

        if (!filteredList.isEmpty()) {
            listView.getSelectionModel().select(0);
            Word selectedWord = data.get(listView.getSelectionModel().getSelectedItem().trim());
            String definition = selectedWord.getDef();
            this.definitionView.getEngine().loadContent(definition, "text/html");
        }
    }

    @FXML
    public void handleDeleteButton(ActionEvent event) {
        String selectedWord = listView.getSelectionModel().getSelectedItem();

        if (selectedWord != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận xóa từ");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn muốn xóa từ '" + selectedWord + "' không?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                data.remove(selectedWord);
                listView.getItems().remove(selectedWord);
                this.definitionView.getEngine().loadContent("", "text/html");
                saveEditsToFile("src/main/data/editedE_V.txt", "DELETE", selectedWord, null);
            }
        }
    }

    @FXML
    public void handleEditButton(ActionEvent event) {
        String selectedWord = listView.getSelectionModel().getSelectedItem();

        if (selectedWord != null) {
            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();

            // Load the current definition into WebView
            String currentDefinition = data.get(selectedWord).getDef();
            String editableContent = "<html><body contenteditable='true'>" + currentDefinition + "</body></html>";
            webEngine.loadContent(editableContent, "text/html");

            // Create an editable dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Chỉnh sửa định nghĩa");
            alert.setHeaderText(null);
            alert.getDialogPane().setContent(webView);

            ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(buttonTypeOk, ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeOk) {
                // Get the edited definition from WebView
                String newDefinition = (String) webEngine.executeScript("document.body.innerHTML");
                data.get(selectedWord).setDef(newDefinition);
                this.definitionView.getEngine().loadContent(newDefinition, "text/html");
                saveEditsToFile("src/main/data/editedE_V.txt", "EDIT", selectedWord, newDefinition);
                listView.getItems().set(listView.getSelectionModel().getSelectedIndex(), selectedWord);
            }
        }
    }
    @FXML
    private void handleBookmarkCheckBox() {
        Word selectedWord = getSelectedWord();
        if (selectedWord != null) {
            boolean isSelected = bookmarkCheckBox.isSelected();

            if (isSelected) {
                addWordToBookmark(selectedWord);
            } else {
                removeWordFromBookmark(selectedWord);
            }
        }
    }
    @FXML
    private void handleListViewClick() {
        updateCheckBoxState();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            bookmarkedWords = new HashSet<>(Files.readAllLines(Paths.get(BOOKMARK_FILE_PATH)));
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        try {
            readData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loadWordList();
        initComponents();
        updateCheckBoxState();
    }
}
