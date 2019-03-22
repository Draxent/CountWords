package org.draxent.countwords;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class FileScannerTest {

    private static final String EMPTY = "";

    @Test
    public void testScanner_withEmptyInput() {
        FileScanner fileScanner = new FileScanner(createInputStreamFromString(""));
        assertFalse(fileScanner.hasNext());
    }

    @Test
    public void testScanner_withSpace() {
        FileScanner fileScanner = new FileScanner(createInputStreamFromString("w1 w2"));
        assertResult(fileScanner, Arrays.asList("w1", "w2"));
    }

    @Test
    public void testScanner_returnLowercaseWords() {
        FileScanner fileScanner = new FileScanner(createInputStreamFromString("w1 w2"));
        assertResult(fileScanner, Arrays.asList("w1", "w2"));
    }

    @Test
    public void testScanner_withPunctuations() {
        FileScanner fileScanner = new FileScanner(createInputStreamFromString("w1,w2!w3:w4.w5;w6?w7--w8"));
        assertResult(fileScanner, Arrays.asList("w1", "w2", "w3", "w4", "w5", "w6", "w7", "w8"));
    }

    @Test
    public void testScanner_withBrackets() {
        FileScanner fileScanner = new FileScanner(createInputStreamFromString("(w1) [w2] {w3}"));
        assertResult(fileScanner, Arrays.asList("w1", EMPTY, EMPTY, "w2", EMPTY, EMPTY, "w3"));
    }

    @Test
    public void testScanner_withQuotes() {
        FileScanner fileScanner = new FileScanner(createInputStreamFromString("'w1' \"w2\" 'w3 w4' \"w5 w6\""));
        assertResult(fileScanner, Arrays.asList("w1", "w2", "w3", "w4", "w5", "w6"));
    }

    @Test
    public void testScanner_withOnlyApostrophe() {
        FileScanner fileScanner = new FileScanner(createInputStreamFromString("'"));
        assertTrue(fileScanner.hasNext());
        assertEquals(EMPTY, fileScanner.next());
        assertFalse(fileScanner.hasNext());
    }

    @Test
    public void testScanner_withOnlyOneQuote() {
        FileScanner fileScanner = new FileScanner(createInputStreamFromString("\""));
        assertTrue(fileScanner.hasNext());
        assertEquals(EMPTY, fileScanner.next());
        assertFalse(fileScanner.hasNext());
    }

    @Test
    public void testScanner_withOnlyOneHyphen() {
        FileScanner fileScanner = new FileScanner(createInputStreamFromString("-"));
        assertTrue(fileScanner.hasNext());
        assertEquals(EMPTY, fileScanner.next());
        assertFalse(fileScanner.hasNext());
    }

    @Test
    public void testScanner_withApostropheInWord() {
        FileScanner fileScanner = new FileScanner(createInputStreamFromString("w1 w'2"));
        assertResult(fileScanner, Arrays.asList("w1", "w'2"));
    }

    @Test
    public void testScanner_withHyphenInWord() {
        FileScanner fileScanner = new FileScanner(createInputStreamFromString("w1 w-2"));
        assertResult(fileScanner, Arrays.asList("w1", "w-2"));
    }

    private InputStream createInputStreamFromString(String input) {
        return new ByteArrayInputStream(input.getBytes());
    }

    private void assertResult(FileScanner fileScanner, List<String> expectedResults) {
        for (String expectedResult : expectedResults) {
            assertTrue(fileScanner.hasNext());
            assertEquals(expectedResult, fileScanner.next());
        }
        assertFalse(fileScanner.hasNext());
    }
}
