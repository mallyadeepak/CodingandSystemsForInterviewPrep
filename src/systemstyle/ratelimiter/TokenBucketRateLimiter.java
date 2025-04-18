package systemstyle.ratelimiter;

/**
 * Pros:
 *
 * Smooth request handling (unlike leaky bucket, which enforces a strict rate)
 *
 * Allows bursts up to bucket_capacity
 *
 * Easy to implement
 *
 *
 * Imagine a bucket that holds tokens.
 *
 * Tokens = permission to perform an action (like make a request).
 *
 * Tokens are added to the bucket at a fixed rate (e.g., 1 token per second).
 *
 * The bucket has a maximum capacity (e.g., 10 tokens).
 *
 * To do something (e.g., send a request), you must take a token from the bucket.
 *
 * If a token is available → ✅ Allowed
 *
 * If no tokens are available → ❌ Denied (rate limit hit)
 *
 */
class TokenBucket {
    private final int capacity;
    private final int refillRate; // tokens per second
    private double tokens;
    private long lastRefillTimestamp;

    public TokenBucket(int capacity, int refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.tokens = capacity;
        this.lastRefillTimestamp = System.nanoTime();
    }

    public synchronized boolean allowRequest() {
        refill();

        if (tokens >= 1) {
            tokens -= 1;
            return true;
        }
        return false;
    }

    private void refill() {
        long now = System.nanoTime();
        double secondsElapsed = (now - lastRefillTimestamp) / 1_000_000_000.0;
        double tokensToAdd = secondsElapsed * refillRate;
        tokens = Math.min(capacity, tokens + tokensToAdd);
        lastRefillTimestamp = now;
    }
}
