package org.draxent.countwords;

import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

class WordCounter {

    private FileScanner fileScanner;
    private HashMap<String, Integer> map;
    private HashMap<String, Integer> sortedMap;

    WordCounter(FileScanner fileScanner) {
        this.fileScanner = fileScanner;
        this.map = new HashMap<>();
        this.sortedMap = new HashMap<>();
    }

    HashMap<String, Integer> getMap() {
        return map;
    }

    HashMap<String, Integer> getSortedMap() {
        return sortedMap;
    }

    void execute() {
        while (fileScanner.hasNext()) {
            String word = fileScanner.next();
            if (word.length() > 0) {
                if (map.containsKey(word)) {
                    map.put(word, map.get(word) + 1);
                } else {
                    map.put(word, 1);
                }
            }
        }
        computedSortedMap();
    }

    List<String> getResultsToPrint(int numResults) {
        int index = 1;
        List<String> results = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            results.add(String.format("%s (%d)", entry.getKey(), entry.getValue()));
            index++;
            if (index > numResults) break;
        }
        return results;
    }

    private void computedSortedMap() {
        sortedMap = map.entrySet().stream()
                .sorted(Collections.reverseOrder(comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }
}
