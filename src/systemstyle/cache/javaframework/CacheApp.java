package systemstyle.cache.javaframework;

import java.util.LinkedHashMap;
import java.util.Map;

public class CacheApp {

    public static void main(String[] args) throws InterruptedException {
        LRUCacheImpl cache = new LRUCacheImpl(2);
        for (int i = 0; i < 10; i++) {
            String key = "key" + i;
            cache.put(key, new Node(key, System.currentTimeMillis()));
            System.out.println(cache.entrySet());
            Thread.sleep(1000);
        }

        Map<Integer, String> map = new LinkedHashMap<>(16, 0.75f, true);
        map.put(1, "A");
        map.put(2, "B");
        map.put(3, "C");

        map.get(1);  // Accessing 1, now it's most recently used
        map.put(4, "D");

        System.out.println(map.keySet()); // Output: [2, 3, 1, 4]
    }
}
