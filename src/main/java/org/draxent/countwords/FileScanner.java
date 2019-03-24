package org.draxent.countwords;

import java.io.InputStream;
import java.util.Scanner;

class FileScanner {

    private static final char SINGLE_QUOTE = '\'';
    private static final char HYPHEN = '-';
    private static final String EMPTY = "";

    private Scanner scanner;

    FileScanner(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
        this.scanner.useDelimiter("\\s+|,|!|:|\\.|;|\\?|--|\\(|\\)|\\[|]|\\{|}|\"");
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
            } else if (firstChar(word) == HYPHEN) {
                return EMPTY;
            } else {
                return word;
            }
        }

        if (firstChar(word) == SINGLE_QUOTE && lastChar(word) == SINGLE_QUOTE) {
            return word.substring(1, word.length() - 1);
        } else if (firstChar(word) == SINGLE_QUOTE) {
            return word.substring(1);
        } else if (lastChar(word) == SINGLE_QUOTE) {
            return word.substring(0, word.length() - 1);
        } else {
            return word;
        }
    }

    void close() {
        scanner.close();
    }

            return word.substring(1, word.length() - 1);
    private char firstChar(String word) {
        return word.charAt(0);
    }

    private char lastChar(String word) {
        return word.charAt(word.length() - 1);
    }
}
