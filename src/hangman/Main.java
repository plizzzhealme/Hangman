package hangman;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int gamesNumber = 100;
        int winCount = 0;
        String[] wordList = readFromFile();

        System.out.println("Dictionary:");
        System.out.println(Arrays.toString(wordList));

        for (int i = 0; i < gamesNumber; i++) {
            System.out.printf("Game %d progress:%n", i + 1);
            if (playGame(wordList)) {
                winCount++;
                System.out.println("Win!");
            } else {
                System.out.println("Lose!");
            }
        }
        System.out.printf("Win rate: %d", winCount * 100 / gamesNumber);
    }

    private static boolean playGame(String[] wordList) {
        Random r = new Random();
        int length = wordList.length;
        int lifeCount = 8;
        String answer;
        String hiddenWord;
        Brain brain;
        answer = wordList[r.nextInt(length)];
        StringBuilder temp = new StringBuilder();


        for (int i = 0; i < answer.length(); i++) {
            temp.append("_");
        }
        hiddenWord = temp.toString();
        brain = new Brain(wordList, hiddenWord);

        while (!hiddenWord.equals(answer)) {
            char letter = brain.guessLetter();
            System.out.println(hiddenWord);
            if (answer.contains(String.valueOf(letter))) {
                for (int i = 0; i < answer.length(); i++) {
                    if (answer.charAt(i) == letter) {
                        hiddenWord = replace(hiddenWord, i, letter);
                    }
                }
                brain.hiddenWord = hiddenWord;
            } else {
                lifeCount--;
            }
        }
        System.out.println(hiddenWord);
        return lifeCount > 0;
    }

    private static String replace(String str, int index, char c) {
        char[] arr = str.toCharArray();
        arr[index] = c;
        return String.valueOf(arr);
    }

    private static String[] readFromFile() {
        try {
            return Files.readAllLines(Paths.get("in.txt")).toArray(new String[0]);
        } catch (IOException e) {
            return new String[0];
        }
    }
}