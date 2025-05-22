package datastructuresandalgos.basicdsa.trees;

import java.util.Stack;

/**
 * General comment:
 * When you are iterating a collection or a tree, you only get a pointer, root or a iterator to the collection or tree,
 * when you are creating an Iterator implementation - it makes sense because otherwise what is the
 * point of having an iterator!
 */
public class PreOrderBSTIterator implements Iterator<Node> {

    private Stack<Node> stack;

    public PreOrderBSTIterator(Node node) {
        stack = new Stack<>();
        if (node != null) {
            stack.push(node);
        }
    }

    @Override
    public Node next() {
        Node node = stack.pop();
        // Push right and left of this node
        if (node.right != null) {
            stack.push(node.right);
        }
        if (node.left != null) {
            stack.push(node.left);
        }
        return node;
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public static void main(String[] args) {

        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        Node n8 = new Node(8);

        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        n2.right = n5;
        n3.left = n6;
        n3.right = n7;

        PreOrderBSTIterator preOrderBSTIterator = new PreOrderBSTIterator(n1);
        while (preOrderBSTIterator.hasNext()) {
            Node node = preOrderBSTIterator.next();
            System.out.println(node);
        }

    }
}
