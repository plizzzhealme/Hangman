package hangman;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Brain {
    private static final char[] letters = "etainoshrdlucmfwygpbvkqjxz".toCharArray(); //english letters, frequency order
    public final String[] dictionary;
    public String hiddenWord;
    List<String> matchingWords;
    int currentLetter;

    public Brain(String[] wordList, String target) {
        dictionary = wordList;
        hiddenWord = target;
        matchingWords = Arrays.asList(dictionary);
    }

    public char guessLetter() {
        rebuildDictionary();
        char result;

        while (!hasLetter(letters[currentLetter])) {
            currentLetter++;
        }
        result = letters[currentLetter];
        currentLetter++;
        return result;
    }

    /**
     * Checks the matching words list whether it contains letter or not
     *
     * @param letter letter to search
     * @return true of contains
     */
    private boolean hasLetter(char letter) {
        for (int i = currentLetter; i < letters.length; i++) {
            for (String word : matchingWords) {
                if (word.contains(String.valueOf(letter))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Rebuilds the matching words list
     */
    private void rebuildDictionary() {
        matchingWords = matchingWords.stream().filter(this::isMatches).collect(Collectors.toList());
    }

    /**
     * Checks if the word in the dictionary matches the target
     *
     * @param word word from dictionary to check
     * @return true if matches
     */
    private boolean isMatches(String word) {
        if (word.length() != hiddenWord.length()) {
            return false;
        }

        for (int i = 0; i < word.length(); i++) {
            if (hiddenWord.charAt(i) != '_' && word.charAt(i) != hiddenWord.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}