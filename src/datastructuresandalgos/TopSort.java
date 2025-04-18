package datastructuresandalgos;

import java.util.*;

public class TopSort {

    // Node definition for Graph
    private static class Node {
        int data;
        List<Node> children;

        Node(int data, List<Node> children) {
            this.data = data;
            this.children = children;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return data == node.data && Objects.equals(children, node.children);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data, children);
        }
    }

    private static class LevelNode {
        int level;
        Node node;

        public LevelNode(int level, Node node) {
            this.level = level;
            this.node = node;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public Node getNode() {
            return node;
        }

        public void setNode(Node node) {
            this.node = node;
        }
    }

    /**
     *
     * @param root
     */
    private static void bfs_level(Node root) {
        Queue<LevelNode> q = new LinkedList<>();
        if (root != null) {
            q.add(new LevelNode(0, root));
        }
        int prevLevel = 0;
        while (!q.isEmpty()) {
            LevelNode lnode = q.poll();
            if (lnode.level != prevLevel) {
                System.out.print("\n");
            }
            prevLevel = lnode.level;
            System.out.print(lnode.node.data + " ");
            if (lnode.node.children != null) {
                for (Node child : lnode.node.children) {
                    q.add(new LevelNode(prevLevel + 1, child));
                }
            }
        }
    }

    /**
     * LRV
     * @param root
     */
    private static void postorder_traversal(Node root) {
        if (root != null && root.children != null)  {
            for (Node child : root.children) {
                postorder_traversal(child);
            }
        }
        // Process the root.
        if (root != null) {
            System.out.print(root.data + " ");
        }
    }


    /**
     * VLR
     * @param root
     */
    private static void preorder_traversal(Node root) {
        // Process the root.
        if (root != null) {
            System.out.print(root.data + " ");
        }
        if (root != null && root.children != null)  {
            for (Node child : root.children) {
                preorder_traversal(child);
            }
        }
    }

    private static void bfs_level2(Node root) {
        Queue<Node> q = new LinkedList<>();
        if (root != null) {
            q.add(root);
        }
        Map<Integer, List<Integer>> levelMap = new HashMap<>();
        levelMap.put(0, Arrays.asList(root.data));
        int level = 0;
        while (!q.isEmpty()) {
            Node currNode = q.poll();
            level = level + 1;
            if (currNode.children != null) {
                List<Integer> levelList = new ArrayList<>();
                for (Node child : currNode.children) {
                    q.add(child);
                    if (!levelMap.containsKey(level)) {
                        levelMap.put(level, levelList);
                    }
                    levelList.add(child.data);
                }
            }
        }
        System.out.print(levelMap);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Node fournode = new Node(4, null);
        Node threenode = new Node(3, Arrays.asList(fournode));
        Node fivenode = new Node(5, null);
        Node sixnode = new Node(6, null);
        Node oneNode = new Node(1, Arrays.asList(threenode));
        Node twonode = new Node(2, Arrays.asList(fivenode, sixnode));
        Node rootnode = new Node(0, Arrays.asList(oneNode, twonode));
        /// bfs_level(rootnode);
        // bfs_level2(rootnode);
        postorder_traversal(rootnode);
        System.out.println("\n");
        preorder_traversal(rootnode);

    }
}

// Tree structure is
// 0 -> 1 -> 3 -> 4, 2 -> 5, 6

