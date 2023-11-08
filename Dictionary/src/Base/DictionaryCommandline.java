package Base;

import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class DictionaryCommandline {

    public static void main(String[] args) {
        DictionaryManagement DicManagement = new DictionaryManagement();
        QuizGame quizGame = new QuizGame();
        Scanner scanner = new Scanner(System.in);
        DicManagement.insertFromFile();
        int op;
        do {
            System.out.println("English-Vietnamese Dictionary");
            System.out.println("1. Searcher ");
            System.out.println("2. Look up ");
            System.out.println("3. Insert word ");
            System.out.println("4. Edit word ");
            System.out.println("5. Delete word ");
            System.out.println("6. Show all word ");
            System.out.println("7. Play QuizGame ");
            System.out.println("8. End program");
            System.out.println("--------------------------------------------------------------");
            do {
                System.out.println("Enter option (1 - 8): ");
                op = DicManagement.checkNumber();
                if (op < 1 || op > 8) {
                    System.out.println("Action not supported");
                }
            } while (op < 1 || op > 8);
            switch (op) {
                case 1:
                    DicManagement.dictionarySearcher();
                    break;
                case 2:
                    DicManagement.dictionaryLookup();
                    break;
                case 3:
                    DicManagement.insertFromCommandline();
                    break;
                case 4:
                    DicManagement.editWordInDictionary();
                    break;
                case 5:
                    DicManagement.deleteWordInDictionary();
                    break;
                case 6:
                    DicManagement.showAllWords();
                    break;
                case 7:
                    System.out.print("Bắt đầu nào. Bạn muốn luyện tập với bao nhiêu câu hỏi? ");
                    int numberOfQuestions = Integer.parseInt(scanner.nextLine());

                    try {
                        // Nhap duong dan den file chua cau hoi
                        quizGame.loadRandomQuestions("C:/Users/QUOC BAO/Downloads/questions.txt", numberOfQuestions);
                        quizGame.start(numberOfQuestions);
                    } catch (FileNotFoundException var11) {
                        System.out.println("Không tìm thấy tệp câu hỏi.");
                        var11.printStackTrace();
                    }
                    break;
                case 8:
                    System.out.println("You want end program ? (Y/N)?");
                    char option = scanner.next().charAt(0);
                    if (option == 'Y' || option == 'y') {
                        scanner.close();
                        return;
                    }

            }
            DicManagement.dictionaryExportToFile();

        } while (op >= 1 && op <= 8);
    }

}

