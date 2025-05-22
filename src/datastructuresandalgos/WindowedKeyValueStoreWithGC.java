package datastructuresandalgos;
import java.util.*;
import java.util.concurrent.*;

/**
 * A windowed KV store with a background cleaner thread.
 *
 * Reasoning on approach on picking a kv-store design and implementation:
 *
 * There are two ways to handle expiry:
 * Either check for expiry during get()/put() calls,
 * or delegate that to a separate background garbage collector.
 *
 * The first option is simpler to implement and ensures that expired keys are immediately removed,
 * but it adds overhead to every user-facing call — especially if we need to scan for expired items
 * or maintain a complex structure.
 *
 * The GC approach separates concerns and keeps user operations lean.
 * It’s slightly more complex because it requires a scheduler or background thread,
 * but it scales better for systems with lots of writes and reads, like a high-QPS cache.
 *
 * For a high performant K-V store — I’d choose the GC-based approach.
 * It allows me to guarantee low-latency operations for the user while giving
 * me control over cleanup policies in the background.
 *
 * "
 *
 */
public class WindowedKeyValueStoreWithGC {

    class ValueNode {
        int value;
        long timestamp; // in millis

        ValueNode(int value, long timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }
    }

    private final Map<String, ValueNode> kvMap = new ConcurrentHashMap<>();
    private final long expiryMillis;
    private final ScheduledExecutorService cleaner;
    private final Object statsLock = new Object();

    private long totalSum = 0;
    private int validCount = 0;

    public WindowedKeyValueStoreWithGC(long expiryMillis) {
        this.expiryMillis = expiryMillis;
        cleaner = Executors.newSingleThreadScheduledExecutor();
        cleaner.scheduleAtFixedRate(this::cleanupExpired, 5, 5, TimeUnit.SECONDS); // Adjustable frequency
    }

    public void put(String key, int value, long timestamp) {
        ValueNode prev = kvMap.put(key, new ValueNode(value, timestamp));
        synchronized (statsLock) {
            if (prev != null) {
                totalSum -= prev.value;
            } else {
                validCount++;
            }
            totalSum += value;
        }
    }

    public int get(String key) {
        ValueNode node = kvMap.get(key);
        if (node == null) return -1;
        long now = System.currentTimeMillis();
        if (now - node.timestamp > expiryMillis) {
            return -1; // GC will remove it eventually
        }
        return node.value;
    }

    public float avg() {
        synchronized (statsLock) {
            return validCount == 0 ? 0 : (float) totalSum / validCount;
        }
    }

    private void cleanupExpired() {
        long now = System.currentTimeMillis();
        for (Map.Entry<String, ValueNode> entry : kvMap.entrySet()) {
            String key = entry.getKey();
            ValueNode node = entry.getValue();
            if (now - node.timestamp > expiryMillis) {
                if (kvMap.remove(key, node)) {
                    synchronized (statsLock) {
                        totalSum -= node.value;
                        validCount--;
                    }
                }
            }
        }
    }

    public void shutdown() {
        cleaner.shutdown();
    }

    public static void main(String[] args) {
        WindowedKeyValueStoreWithGC store = new WindowedKeyValueStoreWithGC(5000);
        store.put("apple", 10, System.currentTimeMillis());
        store.get("apple");  // Fast
        store.avg();         // Fast
        store.shutdown();    // Clean up the background thread
    }
}
