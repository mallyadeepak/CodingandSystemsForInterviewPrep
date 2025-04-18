package systemstyle.ratelimiter;

public class FixedRateLimiterByMinute extends SimpleRateLimiter {

    public FixedRateLimiterByMinute(int limit) {
        super(limit);
    }

    @Override
    public Long getTimeGranularity() {
        long currentTimeInMinute = System.currentTimeMillis() / 60000;
        return currentTimeInMinute;
    }
}
