package com.kodnest.spellchecker;

import java.util.*;

class Trie {
    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node.children.putIfAbsent(ch, new TrieNode());
            node = node.children.get(ch);
        }
        node.endOfWord = true;
    }

    public boolean search(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            if (!node.children.containsKey(ch)) {
                return false;
            }
            node = node.children.get(ch);
        }
        return node.endOfWord;
    }

    public List<String> getAutocompleteSuggestions(String prefix) {
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            if (!node.children.containsKey(ch)) {
                return Collections.emptyList();
            }
            node = node.children.get(ch);
        }
        List<String> suggestions = new ArrayList<>();
        traverse(node, prefix, suggestions);
        return suggestions;
    }

    private void traverse(TrieNode node, String word, List<String> suggestions) {
        if (node.endOfWord) {
            suggestions.add(word);
        }
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            traverse(entry.getValue(), word + entry.getKey(), suggestions);
        }
    }
}
