package oop;

import java.util.*;

public class test {

    static class Node {
        int val;
        Node left;
        Node right;

        public Node(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "val=" + val +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return val == node.val && Objects.equals(left, node.left) && Objects.equals(right, node.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(val, left, right);
        }
    }

    static class NodeWrapper {
        Node node;
        int level;

        public NodeWrapper(Node node, int level) {
            this.node = node;
            this.level = level;
        }
    }

    /** The idea is pretty simple - use a queue and insert the (value, level) into a queue.
     * and then pop it and if the current level is not equal to prevLevel, there is a change in level,
     * update level.
     *
     * Print the element
     *
     * Add the left and right child if they exist for the children with the new level.
     *
     * Iterator can be implemented in a similar way.
     * @param root
     */
    public void printLevelByLevel(Node root) {
        int prevLevel = 0;
        Queue<NodeWrapper> queue = new ArrayDeque<>();
        queue.add(new NodeWrapper(root, 0));

        while (!queue.isEmpty()) {
            NodeWrapper curr = queue.poll();

            // When a new level is reached
            if (curr.level != prevLevel) {
                System.out.println(); // Move to the next line for the new level
                prevLevel = curr.level; // Update prevLevel to the current level
            }

            // Print the current node's value
            System.out.print(curr.node.val + " ");

            // Add left and right children to the queue
            if (curr.node.left != null) {
                queue.add(new NodeWrapper(curr.node.left, curr.level + 1));
            }
            if (curr.node.right != null) {
                queue.add(new NodeWrapper(curr.node.right, curr.level + 1));
            }
        }
    }


    public static void main(String[] args) {

        /** Create a Tree */
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        n1.setLeft(n2);
        n1.setRight(n5);
        n2.setLeft(n3);
        n2.setRight(n4);
        n5.setLeft(n6);
        n5.setRight(n7);
        test ob = new test();

        // Print Level by Level
        ob.printLevelByLevel(n1);

        /** Test way to use a heap*/
        Queue<Integer> queue = new PriorityQueue<>(5, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        queue.offer(10);
        queue.offer(102);
        queue.offer(24);

        System.out.println("\nHeap\n");
        while (!queue.isEmpty()) {
            int val = queue.poll();
            System.out.println(val);
        }
    }
}
