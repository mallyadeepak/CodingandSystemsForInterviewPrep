package datastructuresandalgos.basicdsa.trees;

import java.util.*;
import java.util.Iterator;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

class TestItem {
    public Object key;
    public long timestamp;

    public TestItem(Object key, long timestamp) {
        this.key = key;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Item{" +
                "key=" + key +
                ", timestamp=" + timestamp +
                '}';
    }
}

/**
 * ChunkingIterator is an iterator that takes an input iterator, a chunkCondition, a filterPredicate
 * and returns a chunk. The chunk is created as you iterate through the input and items in the iterator
 * are added to the chunk, if they qualify or pass the condition as provided by the filterPredicate.
 *
 *
 * For example: A ChunkIterator that returns chunks based on the condition that all items that are contiguous and
 * the timestamp between the first item in the chunk and the last item in the chunk is within a given user
 * defined width.
 *
 * @param <T>
 */
class ChunkingIterator<T> implements java.util.Iterator<List<T>> {

    private final java.util.Iterator<T> inputIter;
    private final BiPredicate<T, T> chunkCondition;
    private final Predicate<T> filterPredicate;
    private T bufferedItem;

    public ChunkingIterator(Iterator<T> inputIter,
                            BiPredicate<T, T> chunkCondition,
                            Predicate<T> filterPredicate) {
        this.inputIter = inputIter;
        this.chunkCondition = chunkCondition;
        this.filterPredicate = filterPredicate;
        this.bufferedItem = getNextValid(); // preload first valid item
    }

    private T getNextValid() {
        while (inputIter.hasNext()) {
            T next = inputIter.next();
            if (filterPredicate == null || filterPredicate.test(next)) {
                return next;
            }
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        return bufferedItem != null;
    }

    @Override
    public List<T> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        List<T> chunk = new ArrayList<>();
        T base = bufferedItem;
        chunk.add(base);
        bufferedItem = null;

        while (true) {
            T nextItem = getNextValid();
            if (nextItem == null) {
                break;
            }

            if (chunkCondition.test(base, nextItem)) {
                chunk.add(nextItem);
            } else {
                bufferedItem = nextItem;
                break;
            }
        }

        return chunk;
    }

    public static void main(String[] args) {
        List<TestItem> items = Arrays.asList(
                new TestItem("1", 1),
                new TestItem("2", 4),
                new TestItem("4", 9),
                new TestItem("3", 13),
                new TestItem("42", 12),
                new TestItem("5", 4)
        );

        int width = 2;

        // Create a chunking iterator with a filter to only include certain keys
        ChunkingIterator<TestItem> chunkIter = new ChunkingIterator<>(
                items.iterator(),
                (a, b) -> Math.abs(a.timestamp - b.timestamp) <= width, // chunk if timestamps are close
                item -> Arrays.asList("2", "4", "42").contains(item.key.toString()) // filter keys
        );

        while (chunkIter.hasNext()) {
            List<TestItem> chunk = chunkIter.next();
            System.out.println(chunk);
        }
    }
}