package datastructuresandalgos.basicdsa.trees;

import java.util.Iterator;
import java.util.Stack;

public class PostOrderBSTIterator implements Iterator<Node> {
    private Stack<NodeState> stack = new Stack<>();

    public PostOrderBSTIterator(Node root) {
        if (root != null) {
            stack.push(new NodeState(root));
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public Node next() {
        while (!stack.isEmpty()) {
            NodeState curr = stack.pop();
            Node node = curr.getNode();

            if (curr.getState() == NodeState.State.UN_PROCESSED) {
                // Post-order: push self (marked), then right, then left
                curr.setState(NodeState.State.PROCESSED);
                stack.push(curr); // Push back self to process after children

                if (node.right != null)
                    stack.push(new NodeState(node.right));

                if (node.left != null)
                    stack.push(new NodeState(node.left));

            } else {
                return node;
            }
        }
        return null; // Shouldn't happen if hasNext() was true
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

        PostOrderBSTIterator postOrderBSTIterator = new PostOrderBSTIterator(n1);
        while (postOrderBSTIterator.hasNext()) {
            Node node = postOrderBSTIterator.next();
            System.out.println(node);
        }
    }
}
