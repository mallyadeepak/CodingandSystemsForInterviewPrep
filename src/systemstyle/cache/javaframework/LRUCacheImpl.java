package systemstyle.cache.javaframework;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Node is the value entry in the cache.
 */
class Node {
    String key;
    Long timestampLastAccessed;

    public Node(String key, Long timestampLastAccessed) {

        this.key = key;
        this.timestampLastAccessed = timestampLastAccessed;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(key, node.key) && Objects.equals(timestampLastAccessed, node.timestampLastAccessed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, timestampLastAccessed);
    }

    @Override
    public String toString() {
        return "Node{" +
                "key='" + key + '\'' +
                ", timestampLastAccessed=" + timestampLastAccessed +
                '}';
    }
};

public class LRUCacheImpl extends LinkedHashMap<String, Node>  {

    private final int capacity;

    public LRUCacheImpl(int capacity) {
        super(capacity, 0.75f, true); // accessOrder = true
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, Node> eldest) {
        return size() > capacity;
    }
}
