package systemstyle.concurrency;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedExample {
    private int count = 0;

    private final ReentrantLock lock = new ReentrantLock();

    private final Semaphore semaphore = new Semaphore(1); // 2 threads allowed

    public void accessResource() {
        try {
            semaphore.acquire();  // acquire permit
            System.out.println(Thread.currentThread().getName() + " using resource");
            Thread.sleep(1000);   // simulate work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();  // release permit
        }
    }

    public void increment_with_reentrant() {
        lock.lock();  // acquire lock
        try {
            count++;
        } finally {
            lock.unlock();  // always release lock in finally
        }
    }

    public synchronized void increment() {
        count++;  // synchronized ensures mutual exclusion
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {

        SynchronizedExample synchronizedExample = new SynchronizedExample();

        Thread t1 = new Thread(() -> {
            synchronizedExample.increment_with_reentrant();
            System.out.println(Thread.currentThread() + ":" + synchronizedExample.getCount());
            synchronizedExample.accessResource();
        });

        Thread t2 = new Thread(() -> {
            synchronizedExample.increment_with_reentrant();
            System.out.println(Thread.currentThread() + ":" + synchronizedExample.getCount());
            synchronizedExample.accessResource();
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
