package org.draxent.countwords;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class WordCounterTest {

    private FileScanner fileScannerMock;

    @Before
    public void before() {
        fileScannerMock = mock(FileScanner.class);
    }

    @Test
    public void testWordCounter_constructor() {
        WordCounter wordCounter = new WordCounter(fileScannerMock);
        assertTrue(wordCounter.getMap().isEmpty());
        assertTrue(wordCounter.getSortedMap().isEmpty());
    }

    @Test
    public void testExecute_ScannerEmpty() {
        when(fileScannerMock.hasNext()).thenReturn(false);
        WordCounter wordCounter = createAndExecuteWordCounter();
        assertTrue(wordCounter.getMap().isEmpty());
        assertTrue(wordCounter.getSortedMap().isEmpty());
    }

    @Test
    public void testExecute_ScannerEmptyWorld() {
        when(fileScannerMock.hasNext()).thenReturn(true,  false);
        when(fileScannerMock.next()).thenReturn("");
        WordCounter wordCounter = createAndExecuteWordCounter();
        assertTrue(wordCounter.getMap().isEmpty());
        assertTrue(wordCounter.getSortedMap().isEmpty());
    }

    @Test
    public void testExecute_ScannerOneWord() {
        when(fileScannerMock.hasNext()).thenReturn(true, false);
        when(fileScannerMock.next()).thenReturn("w1");
        WordCounter wordCounter = createAndExecuteWordCounter();
        assertEquals(1, wordCounter.getMap().size());
        assertEquals(1, wordCounter.getMap().get("w1").intValue());
        assertEquals(1, wordCounter.getSortedMap().size());
    }

    @Test
    public void testExecute_ScannerSameWordTwice() {
        when(fileScannerMock.hasNext()).thenReturn(true, true, false);
        when(fileScannerMock.next()).thenReturn("w1", "w1");
        WordCounter wordCounter = createAndExecuteWordCounter();
        assertEquals(1, wordCounter.getMap().size());
        assertEquals(2, wordCounter.getMap().get("w1").intValue());
        assertEquals(1, wordCounter.getSortedMap().size());
    }

    @Test
    public void testExecute_SortedMap() {
        when(fileScannerMock.hasNext()).thenReturn(true, true, true, true, true, true, true, true, true, true, false);
        when(fileScannerMock.next()).thenReturn("w1", "w2", "w2", "w2", "w3", "w3", "w4", "w4", "w4", "w4");
        WordCounter wordCounter = createAndExecuteWordCounter();
        assertEquals(4, wordCounter.getMap().size());
        assertEquals(1, wordCounter.getMap().get("w1").intValue());
        assertEquals(3, wordCounter.getMap().get("w2").intValue());
        assertEquals(2, wordCounter.getMap().get("w3").intValue());
        assertEquals(4, wordCounter.getMap().get("w4").intValue());
        assertEquals(4, wordCounter.getSortedMap().size());
        Iterator<Integer> it = wordCounter.getSortedMap().values().iterator();
        assertEquals(4, it.next().intValue());
        assertEquals(3, it.next().intValue());
        assertEquals(2, it.next().intValue());
        assertEquals(1, it.next().intValue());
    }

    @Test
    public void testGetResultsToPrint() {
        when(fileScannerMock.hasNext()).thenReturn(true, true, true, true, true, true, true, true, true, true, false);
        when(fileScannerMock.next()).thenReturn("w1", "w2", "w2", "w2", "w3", "w3", "w4", "w4", "w4", "w4");
        WordCounter wordCounter = createAndExecuteWordCounter();
        List<String> results = wordCounter.getResultsToPrint(3);
        assertEquals(3, results.size());
        assertEquals("w4 (4)", results.get(0));
        assertEquals("w2 (3)", results.get(1));
        assertEquals("w3 (2)", results.get(2));
    }

    private WordCounter createAndExecuteWordCounter() {
        WordCounter wordCounter = new WordCounter(fileScannerMock);
        wordCounter.execute();
        return wordCounter;
    }
}
