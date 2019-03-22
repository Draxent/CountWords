package org.draxent.countwords;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.print("Usage: ./count-words input_path");
            return;
        }

        String inputPath = args[0];
        FileScanner fileScanner = createFileScanner(inputPath);
        if (fileScanner == null) {
            return;
        }

        WordCounter wordCounter = new WordCounter(fileScanner);
        wordCounter.execute();
        for (String result : wordCounter.getResultsToPrint(10)) {
            System.out.println(result);
        }

        fileScanner.close();
    }

    private static FileScanner createFileScanner(String inputPath) {
        try {
            return new FileScanner(new FileInputStream(new File(inputPath)));
        } catch (FileNotFoundException e) {
            System.err.print("File not found");
            System.err.println(e.getMessage());
            return null;
        }
    }
}