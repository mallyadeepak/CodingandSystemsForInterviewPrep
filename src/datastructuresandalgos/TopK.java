package datastructuresandalgos;

import java.util.*;

public class TopK {

    static class Node {
        int key;
        String value;
        public Node(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value='" + value + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return key == node.key && Objects.equals(value, node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }

    /**
     * @param nodeList
     * @param k
     * @return
     */
    private List<Node> getTopKWithMinHeap(List<Node> nodeList, int k) {
        Queue<Node> queue = new PriorityQueue<>(k, new Comparator<Node>() {
            @Override
            public int compare(Node a, Node b) {
                return a.key - b.key;
            }
        });

        for (Node node : nodeList) {
            queue.add(node);

            // Smaller elements will be removed once k hits and then elements in heap at the end will be
            // max elements.
            if (queue.size() > k) {
                queue.poll();
            }
        }

        return queue.stream().toList();
    }

    /**
     *
     * @param nodeList
     * @param k
     * @return
     */
    private List<Node> getTopKWithMaxHeap(List<Node> nodeList, int k) {
        Queue<Node> queue = new PriorityQueue<>(k, new Comparator<Node>() {
            @Override
            public int compare(Node a, Node b) {
                return b.key - a.key;
            }
        });

        for (Node node : nodeList) {
            queue.add(node);
        }

        return queue.stream().toList().subList(0, k);
    }

    public static void main(String[] args) {
        TopK topK = new TopK();
        List<Node> nodeList = Arrays.asList(
                new Node(1, "1"),
                new Node(2, "2"),
                new Node(4, "4"),
                new Node(22, "22"),
                new Node(41, "41"),
                new Node(51, "51")
        );
        List<Node> outputList = topK.getTopKWithMinHeap(nodeList, 3);

        for (Node node : outputList) {
            System.out.println(node);
        }

        System.out.println("\n");

        outputList = topK.getTopKWithMaxHeap(nodeList, 3);

        for (Node node : outputList) {
            System.out.println(node);
        }
    }
}
