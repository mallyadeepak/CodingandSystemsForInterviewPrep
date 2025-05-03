package datastructuresandalgos.basicdsa;

import java.util.Stack;

class NodeState {
    public enum State {
        UN_PROCESSED, PROCESSED
    }

    private Node node;
    private State state;

    public NodeState(Node node) {
        this.node = node;
        this.state = State.UN_PROCESSED;
    }

    public Node getNode() {
        return node;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}

class Node {
    int value;
    Node left;
    Node right;
    public Node(int value) {
        this.value = value;
    }
}

public class TreeTraversals {

    public void inorderTraversalIterative(Node root) {
        Stack<Node> stack = new Stack<>();
        while (root != null) {
            stack.push(root);
            root = root.left;
        }

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            System.out.print(node.value + " ");
            if (node.right != null) {
                Node n = node.right;
                while (n != null) {
                    stack.push(n);
                    n = n.left;
                }
            }
        }
    }

    public void preorderTraversalIterative(Node root) {
        Stack<Node> stack = new Stack<>();
        if (root != null) {
            stack.push(root);
        }
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            System.out.print(node.value + " ");
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    public void postorderTraversalIterative(Node root) {
        Stack<NodeState> stack = new Stack<>();
        // LRV
        if (root != null) {

            NodeState nodestate = new NodeState(root);
            nodestate.setState(NodeState.State.UN_PROCESSED);
            stack.push(nodestate);

            while (!stack.isEmpty()) {
                nodestate = stack.pop();
                // System.out.print(nodestate.getNode().value + " " + nodestate.getState());

                if (nodestate.getState() == NodeState.State.PROCESSED) {
                    System.out.print(nodestate.getNode().value + " ");
                } else {
                    // Set processed by first pushing right. left and the node.

                    nodestate.setState(NodeState.State.PROCESSED);
                    stack.push(nodestate);
                    if (nodestate.getNode().right != null) {
                        stack.push(new NodeState(nodestate.getNode().right));
                    }

                    if (nodestate.getNode().left != null) {
                        stack.push(new NodeState(nodestate.getNode().left));
                    }
                }
            }

        }
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

        TreeTraversals treeTraversals = new TreeTraversals();
        System.out.println("In-order traversal:");
        treeTraversals.inorderTraversalIterative(n1);
        System.out.println();
        System.out.println("Pre-order traversal:");
        treeTraversals.preorderTraversalIterative(n1);
        System.out.println();
        System.out.println("Post-order traversal:");
        treeTraversals.postorderTraversalIterative(n1);
    }
}
