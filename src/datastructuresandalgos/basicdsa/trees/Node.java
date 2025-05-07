package datastructuresandalgos.basicdsa.trees;

public class Node {
    public int value;
    public Node left;
    public Node right;
    public Node(int value) {

        this.value = value;
        this.left = null;
        this.right = null;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}