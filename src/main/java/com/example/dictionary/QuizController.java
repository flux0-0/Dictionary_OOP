package com.example.dictionary;

import Base.QuizQuestion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class QuizController {

    @FXML
    public Label question;

    @FXML
    public Button opt1, opt2, opt3, opt4, opt5;

    private static int counter = 0;
    public static int correct = 0;
    public static int wrong = 0;

    private List<QuizQuestion> questions;

    @FXML
    private void initialize() {
        questions = readQuestionsFromFile("src/main/data/questions.txt");
        Collections.shuffle(questions); // Trộn danh sách câu hỏi
        loadQuestions();
    }

    private List<QuizQuestion> readQuestionsFromFile(String filePath) {
        List<QuizQuestion> quizQuestions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {  // Bỏ qua các dòng trắng
                    String question = line;
                    List<String> options = new ArrayList<>();
                    for (int i = 0; i < 4; i++) {
                        options.add(br.readLine());
                    }
                    String correctLine = br.readLine();
                    String correctAnswer = correctLine.substring(correctLine.lastIndexOf(":") + 1).trim();

                    QuizQuestion quizQuestion = new QuizQuestion(question, options, correctAnswer);
                    quizQuestions.add(quizQuestion);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quizQuestions;
    }

    private void loadQuestions() {
        if (counter < 10) {
            opt5.setVisible(false);
            QuizQuestion currentQuestion = questions.get(counter);
            question.setText( (counter + 1) + ". " + currentQuestion.getQuestion());
            List<String> options = currentQuestion.getOptions();
            //in câu hỏi ra màn hình
            opt1.setText("A. " + options.get(0));
            opt2.setText("B. " + options.get(1));
            opt3.setText("C. " + options.get(2));
            opt4.setText("D. " + options.get(3));
        } else {
            opt5.setVisible(true);
            // Khởi tạo cho lượt chơi mới
            questions = readQuestionsFromFile("src/main/data/questions.txt");
            Collections.shuffle(questions); // Trộn danh sách câu hỏi
            counter = 0; // Khởi tạo lại biến đếm
        }
    }


    @FXML
    public void opt1clicked(ActionEvent event) {
        checkAnswer("A");
        handleAnswerEvent(event);
    }

    @FXML
    public void opt2clicked(ActionEvent event) {
        checkAnswer("B");
        handleAnswerEvent(event);
    }

    @FXML
    public void opt3clicked(ActionEvent event) {
        checkAnswer("C");
        handleAnswerEvent(event);
    }

    @FXML
    public void opt4clicked(ActionEvent event) {
        checkAnswer("D");
        handleAnswerEvent(event);
    }

    @FXML
    public void opt5clicked(ActionEvent event) {
        displayResultScene(event);
    }

    private void handleAnswerEvent(ActionEvent event) {
        if (counter < questions.size()) {
            counter++;
            loadQuestions();
        } else {
            displayResultScene(event);
        }
    }

    private void displayResultScene(ActionEvent event) {
        //In số câu hỏi đúng sai 
        System.out.println("Correct Answers: " + correct);
        System.out.println("Wrong Answers: " + wrong);
        Stage thisStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        thisStage.close();
        // Add code here to display the result scene
        try {
            FXMLLoader quiz = new FXMLLoader(getClass().getResource("Result.fxml"));
            Scene quizscene = new Scene(quiz.load());
            Stage quizstage = new Stage();
            quizstage.setScene(quizscene);
            quizstage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkAnswer(String answer) {
        if (counter < questions.size()) {
            QuizQuestion currentQuestion = questions.get(counter);
            if (currentQuestion.isCorrect(answer)) {
                correct++;
            } else {
                wrong++;
            }
        }
    }

    //Reset lại số câu trả lời đúng và sai  
    public static void resetValues() {
        correct = 0;
        wrong = 0;
    }
}

