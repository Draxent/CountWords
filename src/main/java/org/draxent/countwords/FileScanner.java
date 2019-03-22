package org.draxent.countwords;

import java.io.InputStream;
import java.util.Scanner;

class FileScanner {

    private static final char SINGLE_QUOTE = '\'';
    private static final char DOUBLE_QUOTE = '"';
    private static final char HYPHEN = '-';
    private static final String EMPTY = "";

    private Scanner scanner;

    FileScanner(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
        this.scanner.useDelimiter("\\s+|,|!|:|\\.|;|\\?|--|\\(|\\)|\\[|]|\\{|}");
    }

    boolean hasNext() {
        return scanner.hasNext();
    }

    String next() {
        String word = scanner.next().toLowerCase();
        if (word.length() == 0) {
            return EMPTY;
        } else if (word.length() == 1) {
            if (firstChar(word) == SINGLE_QUOTE) {
                return EMPTY;
            } else if (firstChar(word) == DOUBLE_QUOTE) {
                return EMPTY;
            } else if (firstChar(word) == HYPHEN) {
                return EMPTY;
            } else {
                return word;
            }
        }

        String wordWithoutSingleQuote = removeQuoteFromWord(word, SINGLE_QUOTE);
        String wordWithoutDoubleQuote = removeQuoteFromWord(word, DOUBLE_QUOTE);
        if (wordWithoutSingleQuote != null) {
            return wordWithoutSingleQuote;
        } else if (wordWithoutDoubleQuote != null) {
            return wordWithoutDoubleQuote;
        } else {
            return word;
        }
    }

    void close() {
        scanner.close();
    }

    private String removeQuoteFromWord(String word, char c) {
        if (firstChar(word) == c && lastChar(word) == c) {
            return word.substring(1, word.length() - 1);
        } else if (firstChar(word) == c) {
            return word.substring(1);
        } else if (lastChar(word) == c) {
            return word.substring(0, word.length() - 1);
        }
        return null;
    }

    private char firstChar(String word) {
        return word.charAt(0);
    }

    private char lastChar(String word) {
        return word.charAt(word.length() - 1);
    }
}
