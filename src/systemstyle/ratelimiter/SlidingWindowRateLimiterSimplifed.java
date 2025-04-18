package systemstyle.ratelimiter;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Pros: Fixes the spikey nature and ensures the request rate limited even across boundaries.
 * Cons: Uses more memory as you need to store the timestamps of every request.
 */
public class SlidingWindowRateLimiterSimplifed implements RateLimiter {

    private final Deque<Long> requests = new ConcurrentLinkedDeque<>();
    private final int limit;

    public SlidingWindowRateLimiterSimplifed(int limit) {
        this.limit = limit;
    }

    @Override
    public Long getTimeGranularity() {
        long grain = System.currentTimeMillis() / 1000;
        return grain;
    }

    @Override
    public void process() throws RateLimitException {
        long now = System.currentTimeMillis() / 1000;

        // Clean up old requests
        while (!requests.isEmpty() && requests.peekFirst() <= now - getTimeGranularity()) {
            requests.pollFirst();
        }

        if (requests.size() >= limit) {
            throw new RateLimitException("Rate limit exceeded");
        }

        requests.addLast(now);
        System.out.println("Request allowed: " + requests);
    }
}
