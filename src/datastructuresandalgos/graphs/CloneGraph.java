package datastructuresandalgos.graphs;

import java.util.*;

// Definition for a graph node.
class Node {
    public int val;
    public List<Node> neighbors;

    public Node() {
        val = 0;
        neighbors = new ArrayList<>();
    }

    public Node(int val) {
        this.val = val;
        neighbors = new ArrayList<>();
    }

    public Node(int val, List<Node> neighbors) {
        this.val = val;
        this.neighbors = neighbors;
    }
}

/**
 *
 * Clones the graph using DFS - keeps a map to store the node and cloneNode in visited map,
 * so that if you see the same node, you can just return the clone node.
 *
 */
public class CloneGraph {
    private Map<Node, Node> visited = new HashMap<>();

    public Node cloneGraph(Node node) {
        if (node == null) return null;

        // If the node was already cloned, return the clone
        if (visited.containsKey(node)) {
            return visited.get(node);
        }

        // Create a new node with the same value
        Node clone = new Node(node.val);
        visited.put(node, clone); // Save the clone in the map

        // Recursively clone all the neighbors
        for (Node neighbor : node.neighbors) {
            clone.neighbors.add(cloneGraph(neighbor));
        }

        return clone;
    }

    // Optional: A main method to demonstrate usage or testing
    public static void main(String[] args) {
        // Example: create graph 1 -- 2
        //                    |    |
        //                    4 -- 3

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        node1.neighbors.addAll(Arrays.asList(node2, node4));
        node2.neighbors.addAll(Arrays.asList(node1, node3));
        node3.neighbors.addAll(Arrays.asList(node2, node4));
        node4.neighbors.addAll(Arrays.asList(node1, node3));

        CloneGraph sol = new CloneGraph();
        Node cloned = sol.cloneGraph(node1);

        System.out.println("Original Node: " + node1.val);
        System.out.println("Cloned Node: " + cloned.val);
    }
}