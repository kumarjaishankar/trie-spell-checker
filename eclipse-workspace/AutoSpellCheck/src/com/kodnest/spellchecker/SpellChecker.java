package com.kodnest.spellchecker;

import java.util.*;

public class SpellChecker {
    private static Trie trie = new Trie();
    private static Set<String> dictionary = new HashSet<>();

    public static List<String> getSuggestions(String word) {
        List<String> suggestions = new ArrayList<>();
        for (String realWord : dictionary) {
            int distance = DamerauLevenshtein.computeDistance(word, realWord);
            if (distance <= 2) {
                suggestions.add(realWord);
            }
        }
        suggestions.sort(Comparator.comparingInt(w -> DamerauLevenshtein.computeDistance(word, w)));
        return suggestions;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter words to add to the dictionary (type 'done' to finish):");
        while (true) {
            String word = scanner.next();
            if (word.equalsIgnoreCase("done")) {
                break;
            }
            dictionary.add(word);
            trie.insert(word);
        }

        if (dictionary.isEmpty()) {
            System.out.println("Dictionary is empty! Please restart and add words.");
            return;
        }

        while (true) {
            System.out.print("Enter a word to check (or 'q' to quit): ");
            String word = scanner.next();
            if (word.equals("q")) {
                break;
            }
            if (!trie.search(word)) {
                List<String> suggestions = getSuggestions(word);
                List<String> autocompleteSuggestions = trie.getAutocompleteSuggestions(word);
                System.out.println("Did you mean: " + suggestions);
                System.out.println("Autocomplete suggestions: " + autocompleteSuggestions);
            } else {
                System.out.println(word + " is a valid word.");
            }
        }
        scanner.close();
    }
}
