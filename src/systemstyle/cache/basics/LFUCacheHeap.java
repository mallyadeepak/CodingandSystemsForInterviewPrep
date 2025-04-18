package systemstyle.cache.basics;

import java.util.*;

class LFUCacheHeap {
    private final int capacity;
    private int time; // because this is a simulation of a time as elements are added and removed to the cache.
    private final Map<Integer, Node> keyToNode; // Key -> Node
    private final PriorityQueue<Node> minHeap; // Min-Heap for LFU eviction

    // Node structure
    private static class Node {
        int key, value, frequency, timestamp;

        Node(int key, int value, int frequency, int timestamp) {
            this.key = key;
            this.value = value;
            this.frequency = frequency;
            this.timestamp = timestamp;
        }
    }

    public LFUCacheHeap(int capacity) {
        this.capacity = capacity;
        this.time = 0;
        this.keyToNode = new HashMap<>();
        this.minHeap = new PriorityQueue<>((a, b) ->
                a.frequency == b.frequency ? Integer.compare(a.timestamp, b.timestamp) : Integer.compare(a.frequency, b.frequency)
        );
    }

    public int get(int key) {
        if (!keyToNode.containsKey(key)) return -1;

        Node node = keyToNode.get(key);
        updateNode(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;

        if (keyToNode.containsKey(key)) {
            Node node = keyToNode.get(key);
            node.value = value;
            updateNode(node);
        } else {
            if (keyToNode.size() >= capacity) {
                evictLFU();
            }
            Node newNode = new Node(key, value, 1, time++);
            keyToNode.put(key, newNode);
            minHeap.offer(newNode);
        }
    }

    private void updateNode(Node node) {
        minHeap.remove(node);
        node.frequency++;
        node.timestamp = time++; // Update timestamp for LRU tie-breaking
        minHeap.offer(node);
    }

    private void evictLFU() {
        Node lfuNode = minHeap.poll();
        if (lfuNode != null) {
            keyToNode.remove(lfuNode.key);
        }
    }

    public static void main(String[] args) {
        LFUCacheHeap lfu = new LFUCacheHeap(3);
        lfu.put(1, 10);
        lfu.put(2, 20);
        lfu.put(3, 30);
        System.out.println(lfu.get(1)); // 10
        lfu.put(4, 40); // Evicts key 2 (LFU)
        System.out.println(lfu.get(2)); // -1 (evicted)
        System.out.println(lfu.get(3)); // 30
    }
}
