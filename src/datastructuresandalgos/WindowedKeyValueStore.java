package datastructuresandalgos;

import java.util.*;

/**
 *
 * A Windowed Key-Value Store supporting put, get, and avg with expiry.
 *
 */
public class WindowedKeyValueStore {

    class ValueNode {
        int value;
        long timestamp; // in millis

        ValueNode(int value, long timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }
    }

    private final Map<String, ValueNode> kvMap = new HashMap<>();
    private final Deque<String> keyQueue = new LinkedList<>();
    private final long expiryInMillis;

    private long totalSum = 0;
    private int validCount = 0;

    public WindowedKeyValueStore(long expiryInMillis) {
        this.expiryInMillis = expiryInMillis;
    }

    public void put(String key, int value, long timestamp) {
        cleanup(timestamp);

        if (kvMap.containsKey(key)) {
            ValueNode oldNode = kvMap.get(key);
            totalSum -= oldNode.value;
        } else {
            validCount++;
        }

        ValueNode newNode = new ValueNode(value, timestamp);
        kvMap.put(key, newNode);
        keyQueue.addLast(key);
        totalSum += value;
    }

    public int get(String key, long now) {
        cleanup(now);

        ValueNode node = kvMap.get(key);
        if (node != null && now - node.timestamp <= expiryInMillis) {
            return node.value;
        } else {
            return -1;
        }
    }

    public float avg(long now) {
        cleanup(now);
        return validCount == 0 ? 0 : (float) totalSum / validCount;
    }

    private void cleanup(long now) {
        while (!keyQueue.isEmpty()) {
            String key = keyQueue.peekFirst();
            ValueNode node = kvMap.get(key);
            if (node == null || now - node.timestamp > expiryInMillis) {
                keyQueue.pollFirst();
                if (node != null) {
                    totalSum -= node.value;
                    validCount--;
                    kvMap.remove(key);
                }
            } else {
                break; // All later nodes are valid
            }
        }
    }
}
