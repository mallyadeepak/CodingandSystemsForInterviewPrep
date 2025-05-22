package datastructuresandalgos.graphs;

import java.util.*;

/**
 * Where n = wordList.length, and L = word length.
 *
 * O(n^2 * L) - Time complexity to build the graph
 *
 * Space complexity os O(n * L)
 *
 * and time for BFS travesal which is O(n * L)
 *
 */
public class WordLadder {

    private Map<String, List<String>> graph = new HashMap<>();

    public void buildGraph(List<String> wordList) {

        for (int i = 0; i < wordList.size(); i++) {

            for (int j = i + 1; j < wordList.size(); j++) {

                String word1 = wordList.get(i);
                String word2 = wordList.get(j);

                if (isOneLetterDiff(word1, word2)) {
                    addToGraph(word1, word2);
                    addToGraph(word2, word1);
                }
            }

        }

        System.out.println(graph);
    }

    public void addToGraph(String w1, String w2) {
            List<String> neighbors = null;
            if (graph.containsKey(w1)) {
                neighbors = graph.get(w1);
            } else {
                neighbors = new ArrayList<>();
            }
            neighbors.add(w2);
            graph.put(w1, neighbors);
    }

    public boolean isOneLetterDiff(String a, String b) {
        if (a.length() != b.length()) return false;

        int diffCount = 0;

        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                diffCount++;
                if (diffCount > 1) return false;
            }
        }

        return diffCount == 1;
    }


    public int shortestPath(String beginWord, String endWord) {

        int shortestPath = Integer.MAX_VALUE;
        for (Map.Entry<String, List<String>> entry: graph.entrySet()) {
              String entryKey = entry.getKey();
              if (isOneLetterDiff(entryKey, beginWord)) {

                  // Start BFS with this word!
                  int len = bfs(entryKey, endWord);
                  if (len < shortestPath) {
                      shortestPath = len;
                  }
              }
        }
        return shortestPath;
    }

    class Node {
        String word;
        int depth;

        public Node(String word, int depth) {
            this.word = word;
            this.depth = depth;
        }
    }
    private int bfs(String startWord, String endWord) {

        Set<String> seen = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(startWord, 1));

        while (!queue.isEmpty()) {

            Node currWord = queue.poll();

            if (currWord.word.equals(endWord)) {
                return currWord.depth;
            }

            if (!seen.contains(currWord.word)) {
                seen.add(currWord.word);
                List<String> neigbors = graph.get(currWord.word);

                if (neigbors != null) {
                    for (String n : neigbors) {
                        queue.add(new Node(n, currWord.depth + 1));
                    }
                }

            }

        }
        return Integer.MAX_VALUE;
    }

    public static void main(String[] args) {

        /**
         * beginWord = "hit",
         * endWord = "cog",
         * wordList = ["hot","dot","dog","lot","log","cog"]
         */

        WordLadder wordLadder = new WordLadder();
        // Add begin word to list.
        List<String> wordList = Arrays.asList("hit", "hot", "dot", "dog", "lot", "log", "cog");
        wordLadder.buildGraph(wordList);
        int shortestPath = wordLadder.shortestPath("hit", "cog");
        System.out.println("wordLadder.shortestPath(\"hit\", \"cog\") == " + shortestPath);

    }
}
