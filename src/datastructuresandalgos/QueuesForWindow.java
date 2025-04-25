package datastructuresandalgos;

import java.util.ArrayDeque;
import java.util.Deque;

public class QueuesForWindow {

    public static void main(String[] args) {

        QueuesForWindow q = new QueuesForWindow();
        q.simulateWindow(5);
    }

    private void simulateWindow(int windowSize) {
        Deque<String> deque = new ArrayDeque<>();
        for (int i = 0; i < windowSize; i++) {
            deque.offer("a" + i);
        }
        while (!deque.isEmpty()) {
            String str = deque.removeLast();
            System.out.println(str);
        }
    }
}
