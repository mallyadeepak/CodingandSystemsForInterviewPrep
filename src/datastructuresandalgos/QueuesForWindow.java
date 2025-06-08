package datastructuresandalgos;

import java.util.ArrayDeque;
import java.util.Deque;



/**
 * This class QueuesForWindow demonstrates how to use 
 * Queues in a window fashion specfically for solving 
 * sliding-window problems. The idea is you expand the window
 * on the right by adding elements on right, and shrink the window from the left by removing elements
 * from the left.
 */
public class QueuesForWindow {

    public static void main(String[] args) {

        QueuesForWindow q = new QueuesForWindow();
        q.simulateWindow(5);
    }

    private void simulateWindow(int windowSize) {
        Deque<String> deque = new ArrayDeque<>();
        for (int i = 0; i < windowSize; i++) {
            deque.offer("a" + i); // Add on the right
        }
        System.out.println(deque);
        while (!deque.isEmpty()) {
            String elem = deque.removeFirst(); // Remove from the left
            System.out.println("Removing element = " + elem);
            System.out.println(deque);
        }
    }
}
