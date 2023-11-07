package Base;

import java.io.IOException;
import java.util.Scanner;

public class DictionaryCommandline {

    public static void main(String[] args) {
        DictionaryManagement DicManagement = new DictionaryManagement();
        Scanner scan = new Scanner(System.in);
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
            System.out.println("7. End program");
            System.out.println("--------------------------------------------------------------");
            do {
                System.out.println("Enter option (1 - 7): ");
                op = DicManagement.checkNumber();
                if (op < 1 || op > 7) {
                    System.out.println("Option not found ! check again ...");
                }
            } while (op < 1 || op > 7);
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
                    System.out.println("You want end program ? (Y/N)?");
                    char option = scan.next().charAt(0);
                    if (option == 'Y' || option == 'y') {
                        return;
                    }
            }
            DicManagement.dictionaryExportToFile();

        } while (op >= 1 && op <= 7);
    }

}

