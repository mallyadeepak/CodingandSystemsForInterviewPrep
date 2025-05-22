package systemstyle.cache.basics;

import java.util.*;

/**
 * LRU Cache is a Map + DLL.
 *
 * The DLL maintains a head and tail dummy node. The node at the front is the most
 * recently used, whereas at the back (tail) is the LRU.
 *
 * Whenever the node is accessed (put or get), it is updated,removed and added to the front of the DLL.
 * When size hits capacity, then the node is removed from the tail and map,
 * and new node is created and added to the front.
 *
 */
class LRUCache {
    private class Node {
        int key, value;
        Node prev, next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> cache;
    private final Node head, tail;  // Dummy nodes for easy insertion/deletion

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();

        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addToFront(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) return -1;

        Node node = cache.get(key);
        remove(node);
        addToFront(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.value = value;
            remove(node);
            addToFront(node);
        } else {
            if (cache.size() >= capacity) {
                Node lru = tail.prev; // Get LRU node
                remove(lru);
                cache.remove(lru.key);
            }
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addToFront(newNode);
        }
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);  // Capacity = 2
        cache.put(1, 1); // Cache = {1=1}
        cache.put(2, 2); // Cache = {1=1, 2=2}
        System.out.println(cache.get(1)); // Returns 1, moves key 1 to front
        cache.put(3, 3); // Evicts key 2, Cache = {1=1, 3=3}
        System.out.println(cache.get(2)); // Returns -1 (not found)
        cache.put(4, 4); // Evicts key 1, Cache = {4=4, 3=3}
        System.out.println(cache.get(1)); // Returns -1 (not found)
        System.out.println(cache.get(3)); // Returns 3
        System.out.println(cache.get(4)); // Returns 4
    }
}