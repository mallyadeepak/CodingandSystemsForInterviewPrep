package systemstyle.cache.basics;

import java.util.*;

class LFUCache {

    private final int capacity;
    private int minFreq;
    private final Map<Integer, Node> keyToNode; // Key → Node
    private final Map<Integer, DoublyLinkedList> freqToList; // Frequency → DLL

    // Node definition for doubly linked list
    private static class Node {
        int key, value, frequency;
        Node prev, next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.frequency = 1;
        }
    }

    // Doubly linked list to store nodes of the same frequency
    private static class DoublyLinkedList {
        /**
         * head and tail are dummy nodes with key,value being -1,-1
         */
        Node head, tail;
        int size;

        DoublyLinkedList() {
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        void addNode(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
            size++;
        }

        void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }

        Node removeLast() {
            if (size > 0) {
                Node last = tail.prev;
                removeNode(last);
                return last;
            }
            return null;
        }

        boolean isEmpty() {
            return size == 0;
        }
    }

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;
        this.keyToNode = new HashMap<>();
        this.freqToList = new HashMap<>();
    }

    /**
     * get(key) -> returns the value of the key by getting the node from the Map
     * It also calls updateFrequency(node) that updates the frequency in the DLL and adjusts
     * the minFreq as needed.
     * @param key
     * @return
     */
    public int get(int key) {
        if (!keyToNode.containsKey(key)) return -1;
        Node node = keyToNode.get(key);
        updateFrequency(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;

        if (keyToNode.containsKey(key)) {
            Node node = keyToNode.get(key);
            node.value = value;
            updateFrequency(node);
        } else {
            if (keyToNode.size() >= capacity) {
                evictLFU();
            }
            Node newNode = new Node(key, value);
            keyToNode.put(key, newNode);
            freqToList.computeIfAbsent(1, k -> new DoublyLinkedList()).addNode(newNode);
            minFreq = 1; // Reset minFreq since we just added a new node with frequency 1
        }
    }

    /**
     * Updates the frequency of the node. This removes the node in the DLL.
     * It then checks is the DLL is empty and the oldFreq is same as the current minFreq. If yes, it updates the
     * minFreq
     *
     * It then increments the node frequency and then adds to the freqToList map.
     * @param node
     */
    private void updateFrequency(Node node) {
        int oldFreq = node.frequency;
        DoublyLinkedList oldList = freqToList.get(oldFreq);
        oldList.removeNode(node);

        if (oldList.isEmpty() && minFreq == oldFreq) {
            minFreq++; // Update minFreq if the old frequency list is now empty
        }

        node.frequency++;
        freqToList.computeIfAbsent(node.frequency, k -> new DoublyLinkedList()).addNode(node);
    }

    /**
     * eviction removes the last element from the DLL for the minFreq.
     * Basically finds the DLL with the minFreq, and then removes the last node from it.
     */
    private void evictLFU() {
        DoublyLinkedList list = freqToList.get(minFreq);
        Node lfuNode = list.removeLast();

        if (lfuNode != null) {
            keyToNode.remove(lfuNode.key);
        }
    }

    public static void main(String[] args) {
        LFUCache lfu = new LFUCache(3);
        lfu.put(1, 10);
        lfu.put(2, 20);
        lfu.put(3, 30);
        System.out.println(lfu.get(1)); // 10
        lfu.put(4, 40); // Evicts key 2 (LFU)
        System.out.println(lfu.get(2)); // -1 (evicted)
        System.out.println(lfu.get(3)); // 30
    }
}