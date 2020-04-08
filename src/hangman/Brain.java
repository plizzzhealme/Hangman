package hangman;

import java.util.Arrays;

public class Brain {
    private static final char[] letters = "etainoshrdlucmfwygpbvkqjxz".toCharArray(); //english letters, frequency order
    private String[] dictionary;
    private String hiddenWord;
    private int currentLetter;

    public Brain(String[] wordList, String target) {
        dictionary = wordList;
        hiddenWord = target;
    }

    public void setHiddenWord(String hiddenWord) {
        this.hiddenWord = hiddenWord;
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
            for (String word : dictionary) {
                if (word.contains(String.valueOf(letter))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Rebuilds the dictionary
     */
    private void rebuildDictionary() {
        dictionary = Arrays.stream(dictionary).filter(this::isMatches).toArray(String[]::new);
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