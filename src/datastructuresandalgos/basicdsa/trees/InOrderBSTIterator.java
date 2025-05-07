package datastructuresandalgos.basicdsa.trees;

import java.util.Stack;

/**
 * An BST iterator is an iterator implementation for a Binary search tree - returns an inorder traversal
 */
public class InOrderBSTIterator implements Iterator<Node> {

    private Stack<Node> stack = new Stack();

    public InOrderBSTIterator(Node root) {
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
    }

    @Override
    public Node next() {
        Node node = stack.pop();
        Node nodeRight = node.right;
        while (nodeRight != null) {
            stack.push(nodeRight);
            nodeRight = nodeRight.left;
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

        InOrderBSTIterator inOrderBSTIterator = new InOrderBSTIterator(n1);
        while (inOrderBSTIterator.hasNext()) {
            Node node = inOrderBSTIterator.next();
            System.out.println(node);
        }
    }
}
