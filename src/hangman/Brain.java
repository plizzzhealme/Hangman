package hangman;

import java.util.Arrays;

public class Brain {

    /**
     * English letters, frequency order
     */
    private static final char[] letters = "etainoshrdlucmfwygpbvkqjxz".toCharArray();
    private int currentLetterIndex;
    private String[] dictionary;
    private String hiddenWord;

    public Brain(String[] wordList, String target) {
        dictionary = wordList;
        hiddenWord = target;
        rebuildDictionary();
    }

    public void setHiddenWord(String hiddenWord) {
        this.hiddenWord = hiddenWord;
        rebuildDictionary();
    }

    public char guessLetter() {
        char result;

        while (!hasLetter(letters[currentLetterIndex])) {
            currentLetterIndex++;
        }
        result = letters[currentLetterIndex];
        currentLetterIndex++;
        return result;
    }

    /**
     * Checks the dictionary whether any word contains the letter or not
     *
     * @param letter the letter to search
     * @return true if contains
     */
    private boolean hasLetter(char letter) {
        for (int i = currentLetterIndex; i < letters.length; i++) {
            for (String word : dictionary) {
                if (word.contains(String.valueOf(letter))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Rebuilds the dictionary according to hiddenWord pattern
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