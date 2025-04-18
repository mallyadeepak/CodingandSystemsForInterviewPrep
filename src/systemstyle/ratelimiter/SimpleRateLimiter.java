package systemstyle.ratelimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Fixed window rate limiter that uses a counter.
 *
 * Pros: Simple to implement
 * Cons: Allows spikey traffic near boundaries.
 *
 */
public class SimpleRateLimiter implements RateLimiter {

    private final Map<Long, AtomicInteger> counter = new ConcurrentHashMap<>();
    private int limit;

    public SimpleRateLimiter(int limit) {
        this.limit = limit;
    }

    @Override
    public Long getTimeGranularity() {
        long currentTimeInSeconds = System.currentTimeMillis() / 1000;
        return currentTimeInSeconds;
    }

    @Override
    public void process() throws RateLimitException {
        Long key = getTimeGranularity();

        counter.putIfAbsent(key, new AtomicInteger(0));
        int count = counter.get(key).incrementAndGet();

        if (count > limit) {
            System.out.println(counter);
            throw new RateLimitException("Rate Exceeded");
        }

        // Optional cleanup for old entries if using longer windows
        counter.entrySet().removeIf(entry -> entry.getKey() < key);
        System.out.println(counter);
    }
}
