package datastructuresandalgos.basicdsa;

/**
 * Basic Stack Data structure in Java with a generic Type that uses an array
 * @param <T>
 */
public class Stack<T> {

    private Object[] arr;
    private int size;
    private int index;

    public Stack(int size) {
        this.size = size;
        arr = new Object[size];
        index = 0;
    }

    public void push(T elem) throws Exception {
        if (!isFull()) {
            arr[index++] = elem;
        } else {
            throw new Exception("Stack is full, size = " + size);
        }
    }

    @SuppressWarnings("unchecked")
    public T pop() throws Exception {
        if (!isEmpty()) {
            return (T) arr[--index];
        } else {
            throw new Exception("Stack is empty.");
        }
    }

    public boolean isFull() {
        return index == size;
    }

    public boolean isEmpty() {
        return index == 0;
    }

    public static void main(String[] args) throws Exception {
        Stack<Integer> stack = new Stack<>(5);
        stack.push(10);
        stack.push(20);
        stack.push(30);
        int val = stack.pop();
        System.out.println("val = " + val);  // Should print
    }
}