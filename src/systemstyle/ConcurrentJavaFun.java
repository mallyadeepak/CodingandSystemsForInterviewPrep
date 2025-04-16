package systemstyle;

import java.util.List;
import java.util.concurrent.*;

public class ConcurrentJavaFun {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        List<Callable<String>> tasks = List.of(
                () -> "A", () -> "B", () -> "C"
        );

        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Future<String>> results = executor.invokeAll(tasks);

        for (Future<String> f : results) {
            System.out.println(f.get());
        }
        executor.shutdown();

    }
}
