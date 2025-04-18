package systemstyle.ratelimiter;

public class RateLimiterApp {

    public static void main(String[] args) throws RateLimitException, InterruptedException {
        RateLimiter slidingWindowRateLimiterSimplifed = new SlidingWindowRateLimiterSimplifed(2);
        while (true) {
            // rateLimiter.process();
            slidingWindowRateLimiterSimplifed.process();
            // 1 request per 100 ms, so 10 request in a second.
            // This should hit a rate limit if 2 is the threshold pr second
            Thread.sleep(100);
        }
    }
}
