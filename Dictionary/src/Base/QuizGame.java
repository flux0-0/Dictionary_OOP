package Base;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

// Lớp để đại diện cho một câu hỏi trắc nghiệm
class Question {
    private String questionText;
    private String[] options;
    private char correctAnswer;

    public Question(String questionText, String[] options, char correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public char getCorrectAnswer() {
        return correctAnswer;
    }
}

// Lớp để quản lý trò chơi trắc nghiệm
public class QuizGame {
    private ArrayList<Question> questions;
    private int numberOfQuestionsToAnswer; // Số câu hỏi trong trò chơi sẽ hỏi người chơi

    public QuizGame() {
        questions = new ArrayList<>();
        numberOfQuestionsToAnswer = 0;
    }

    // Phương thức để tải câu hỏi từ một tệp văn bản
    public void loadRandomQuestions(String filePath, int numberOfQuestions) throws FileNotFoundException {
        ArrayList<Question> allQuestions = new ArrayList<>();
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        try {
            while (scanner.hasNextLine()) {
                String questionText = scanner.nextLine().trim();
                if (questionText.isEmpty()) {
                    continue; // Bỏ qua dòng trống
                }

                String[] options = new String[4];
                for (int i = 0; i < options.length; i++) {
                    options[i] = scanner.nextLine().trim();
                }

                String answerLine = scanner.nextLine().trim();
                if (!answerLine.startsWith("Correct: ")) {
                    System.out.println("Định dạng câu trả lời không đúng: " + questionText);
                    continue; // Bỏ qua câu hỏi này nếu định dạng không đúng
                }
                char correctAnswer = answerLine.charAt(answerLine.length() - 1);
                allQuestions.add(new Question(questionText, options, correctAnswer));

                if (scanner.hasNextLine()) {
                    scanner.nextLine(); // Bỏ qua dòng trống
                }
            }
        } finally {
            scanner.close();
        }

        // Xáo trộn danh sách câu hỏi để tạo ngẫu nhiên
        Collections.shuffle(allQuestions);

        // Chọn một số lượng câu hỏi ngẫu nhiên từ danh sách
        questions.clear(); // Xóa danh sách câu hỏi hiện có
        for (int i = 0; i < Math.min(numberOfQuestions, allQuestions.size()); i++) {
            questions.add(allQuestions.get(i));
        }
    }

    // Phương thức để bắt đầu trắc nghiệm
    // Modified start method
    // Phương thức để bắt đầu trắc nghiệm
    public void start(int numberOfQuestionsToAnswer) {
        Scanner scanner = new Scanner(System.in);
        int score = 0;
        int questionsAnswered = 0;

        for (Question question : questions) {
            if (questionsAnswered >= numberOfQuestionsToAnswer) {
                break; // Dừng vòng lặp nếu người dùng đã trả lời số câu hỏi mong muốn
            }

            System.out.println(question.getQuestionText());
            for (String option : question.getOptions()) {
                System.out.println(option);
            }

            char userAnswer = ' ';
            boolean validAnswer = false;
            while (!validAnswer) {
                System.out.print("Lựa chọn của bạn [A/B/C/D]: ");
                String input = scanner.nextLine().toUpperCase();
                if (input.length() == 1) {
                    userAnswer = input.charAt(0);
                    if (userAnswer == 'A' || userAnswer == 'B' || userAnswer == 'C' || userAnswer == 'D') {
                        validAnswer = true;
                    } else {
                        System.out.println("Bạn đã nhập sai kí tự. Xin mời nhập lại!");
                    }
                } else {
                    // Kiểm tra nếu chuỗi nhập vào > 1 kí tự
                    System.out.println("Xin mời nhập lại.");
                }
            }

            if (userAnswer == question.getCorrectAnswer()) {
                System.out.println("Đúng!");
                score++;
            } else {
                System.out.println("Sai! Câu trả lời đúng là " + question.getCorrectAnswer());
            }
            questionsAnswered++; // Tăng số lượng câu hỏi đã trả lời
        }
        // Tính phần trăm điểm số
        double scorePercentage = (double) score / numberOfQuestionsToAnswer * 100;
        System.out.println("Hoàn thành trắc nghiệm! Điểm của bạn: " + score + "/" + numberOfQuestionsToAnswer);
        // Đánh giá điểm số và cung cấp phản hồi
        if (scorePercentage <= 40) {
            System.out.println("Đừng nản lòng! Hãy ôn tập thêm và thử lại lần nữa.");
        } else if (scorePercentage > 40 && scorePercentage < 80) {
            System.out.println("Làm tốt lắm! Bạn đã nắm bắt được khá nhiều kiến thức, cố gắng phát huy nhé.");
        } else if (scorePercentage >= 80) {
            System.out.println("Tuyệt vời! Bạn đã hiểu rất rõ về các chủ đề này. Tiếp tục duy trì phong độ nhé!");
        }
    }

}
