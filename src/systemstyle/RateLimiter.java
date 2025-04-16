package systemstyle;

class RateLimitException extends Exception {
    RateLimitException(String message) {
        super(message);
    }
}
public interface RateLimiter {

    abstract Long getTimeGranularity();

    abstract void process() throws RateLimitException;
}
