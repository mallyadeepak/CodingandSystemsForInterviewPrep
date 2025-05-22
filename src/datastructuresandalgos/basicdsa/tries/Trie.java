package datastructuresandalgos.basicdsa.tries;

import java.util.HashMap;
import java.util.Map;

/**
 * Trie is a data structure to store strings.
 * Every node has a character -> node pointer i.e so there can be 26 children for english characters.
 * It also stores state if a word ends here.
 */
class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEndOfWord;
}

/**
 *
 * Trie class seperates operations on a Trie - insert(word), search(word) and startsWith(word)
 * The complexity for these operations is the length of the input word len(word)
 *
 */
public class Trie {
    private final TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current = current.children.computeIfAbsent(ch, c -> new TrieNode());
        }
        current.isEndOfWord = true;
    }

    public boolean search(String word) {
        TrieNode node = findNode(word);
        return node != null && node.isEndOfWord;
    }

    public boolean startsWith(String prefix) {
        return findNode(prefix) != null;
    }

    private TrieNode findNode(String str) {
        TrieNode current = root;
        for (char ch : str.toCharArray()) {
            current = current.children.get(ch);
            if (current == null) return null;
        }
        return current;
    }
}