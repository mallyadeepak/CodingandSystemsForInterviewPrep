package generics;

import java.util.*;

/**
 *
 * For my understanding of generics:
 *
 * At compile time: type checking is done and generic syntax validation
 * After compile: The compiler outputs one class file .class with type erasures.
 * At runtime: There is no generic types - all type info is erased.
 *
 * @param <K>
 * @param <V>
 */
class Item<K, V> {
    K key;
    V value;

    Item(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "[" + key + ": " + value + "]";
    }
}

class KeyedQueue<K, V> {
    private Queue<Item<K, V>> queue = new LinkedList<>();
    private Map<K, Item<K, V>> map = new HashMap<>();

    // Enqueue an item
    public void enqueue(K key, V value) {
        if (map.containsKey(key)) {
            throw new IllegalArgumentException("Duplicate key not allowed: " + key);
        }
        Item<K, V> item = new Item<>(key, value);
        queue.offer(item);
        map.put(key, item);
    }

    // Dequeue the front item
    public Item<K, V> dequeue() {
        Item<K, V> item = queue.poll();
        if (item != null) {
            map.remove(item.key);
        }
        return item;
    }

    // Fetch item by key
    public Item<K, V> getByKey(K key) {
        return map.get(key);
    }

    public int size() {
        return queue.size();
    }

    public void printQueue() {
        for (Item<K, V> item : queue) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        KeyedQueue<String, Integer> q = new KeyedQueue<>();

        q.enqueue("one", 1);
        q.enqueue("two", 2);
        q.enqueue("three", 3);

        q.printQueue(); // [one: 1] [two: 2] [three: 3]

        System.out.println("Get by key 'two': " + q.getByKey("two")); // [two: 2]

        q.dequeue();
        q.printQueue(); // [two: 2] [three: 3]

        System.out.println("Q class = " + q.getClass());

    }
}
