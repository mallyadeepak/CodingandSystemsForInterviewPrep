package systemstyle.cache.javaframework;

public interface Cache<T> {

    abstract T get(String key);

    abstract void put(String key, T value);
}
