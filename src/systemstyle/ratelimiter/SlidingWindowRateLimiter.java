package systemstyle.ratelimiter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * SlidingWindowRateLimiter.
 *
 * This keeps track of the request - so it ensures strict rate limiting but with more memory.
 */
public class SlidingWindowRateLimiter implements RateLimiter {

    private final Map<Long, Deque<Long>> grainToRequestsMap;
    private int limit;

    public SlidingWindowRateLimiter(int limit) {
        // this.queue = new ConcurrentLinkedDeque<>();
        this.grainToRequestsMap = new ConcurrentHashMap<>();
        this.limit = limit;
    }

    @Override
    public Long getTimeGranularity() {
        long currentTimeInSeconds = System.currentTimeMillis() / 1000;
        return currentTimeInSeconds;
    }

    @Override
    public void process() throws RateLimitException {

        /**
         * Update the request in the data structure
         */
        Long timeGranularity = getTimeGranularity();
        Deque<Long> requests;
        if (grainToRequestsMap.containsKey(timeGranularity)) {
            // existing grain i.e same second
            requests = grainToRequestsMap.get(timeGranularity);
            requests.add(timeGranularity);
        } else {
            // new grain i.e new second
            requests = new ConcurrentLinkedDeque<>();
            requests.add(timeGranularity);
            grainToRequestsMap.put(timeGranularity, requests);
        }
        /**
         * Check if the requests hit limit and adjust the request queue to remove requests that are older than
         * the grain and throw exception if limit has been exceeded based on the size.
         */
        if (requests.size() > limit) {
            // Remove elements from the rear of the queue that are older
            while (requests.peekLast() != null && requests.peekLast() < timeGranularity) {
                requests.removeLast();
            }
            throw new RateLimitException("Limit exceeded");
        }
        System.out.println("Allowed request, queue = " + requests);
        // Optional cleanup for old entries if using longer windows
        System.out.println("Before cleanup, grainToRequestsMap = " + grainToRequestsMap);
        grainToRequestsMap.entrySet().removeIf(entry -> entry.getKey() < timeGranularity);
        System.out.println("After cleanup, grainToRequestsMap = " + grainToRequestsMap);
    }
}
