package com.kodnest.spellchecker;

import java.util.*;

public class SpellChecker {
    private static Trie trie = new Trie();
    private static Set<String> dictionary;

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
        dictionary = new HashSet<>(Arrays.asList("the", "thy", "tar", "thru", "hr", "thor", "tur", "thar", "tor", "hell", "helly", "hello", "help", "hells", "sapa", "supa", "swa", "swap", "spa", "project", "coffee", "coffret", "coffer", "coffea"));
        for (String word : dictionary) {
            trie.insert(word);
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a word (or 'q' to quit): ");
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
