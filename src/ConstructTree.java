import com.sun.source.tree.Tree;

import java.util.*;

/**
 * Create a TreeNode
 */
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) {
        val = x;
        left = right = null;
    }
}

enum NodeStatus2 {
    UNPROCESSED,
    TO_BE_VISITED,
    VISITED
}

class NodeState2 {
    TreeNode node;
    NodeStatus2 status;

    NodeState2(TreeNode node, NodeStatus2 status) {
        this.node = node;
        this.status = status;
    }
}

public class ConstructTree {
    private Map<Integer, Integer> inorderIndexMap;
    private int postIndex;

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        inorderIndexMap = new HashMap<>();
        postIndex = postorder.length - 1; // Start from last element in postorder

        // Store indices of inorder values for quick lookup
        for (int i = 0; i < inorder.length; i++) {
            inorderIndexMap.put(inorder[i], i);
        }

        return buildTreeHelper(inorder, postorder, 0, inorder.length - 1);
    }

    private TreeNode buildTreeHelper(int[] inorder, int[] postorder, int inLeft, int inRight) {
        // Base case: No elements to construct the tree
        if (inLeft > inRight) return null;

        // Get root value from postorder
        int rootVal = postorder[postIndex--];
        TreeNode root = new TreeNode(rootVal);

        // Find root index in inorder
        int inorderIndex = inorderIndexMap.get(rootVal);

        // **Important:** Build right subtree first, then left subtree (because of postorder)
        root.right = buildTreeHelper(inorder, postorder, inorderIndex + 1, inRight);
        root.left = buildTreeHelper(inorder, postorder, inLeft, inorderIndex - 1);

        return root;
    }

    // Utility function to print inorder traversal for verification
    public void printInorder(TreeNode root) {
        if (root == null) return;
        printInorder(root.left);
        System.out.print(root.val + " ");
        printInorder(root.right);
    }

    public void printPostOrderIterative(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();

        /**
         * Wall through the tree and go left and insert element into stack
         */
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            System.out.print(node.val + " ");
            node = node.right;
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
    }

    public void printPreorderIterative(TreeNode root) {
        if (root == null) return;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            System.out.print(node.val + " "); // Visit node first (preorder)

            // Push right first so that left is processed first (stack = LIFO)
            if (node.right != null) {
                stack.push(node.right);
            }

            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    // Helper class to store node and its processing state
    class NodeState {
        TreeNode node;
        boolean isChildrenProcessed;

        NodeState(TreeNode node, boolean isChildrenProcessed) {
            this.node = node;
            this.isChildrenProcessed = isChildrenProcessed;
        }
    }

    /**
     * Solution with one stack.
     * @param root
     */
    public void printPostorderIterative(TreeNode root) {
        if (root == null) return;

        Stack<NodeState> stack = new Stack<>();
        stack.push(new NodeState(root, false));

        while (!stack.isEmpty()) {
            NodeState current = stack.pop();

            if (current.isChildrenProcessed) {
                // Now it's time to process the node
                System.out.print(current.node.val + " ");
            } else {
                // First push the node back, marked as ready for processing
                stack.push(new NodeState(current.node, true));

                // Then push right and left children (in that order)
                if (current.node.right != null) {
                    stack.push(new NodeState(current.node.right, false));
                }
                if (current.node.left != null) {
                    stack.push(new NodeState(current.node.left, false));
                }
            }
        }
    }

    public void postorderWithState(TreeNode root) {
        if (root == null) return;

        Stack<NodeState2> stack = new Stack<>();
        stack.push(new NodeState2(root, NodeStatus2.UNPROCESSED));

        while (!stack.isEmpty()) {
            NodeState2 current = stack.pop();

            if (current.status == NodeStatus2.UNPROCESSED) {
                // Push the node back as ready to be visited after children
                stack.push(new NodeState2(current.node, NodeStatus2.VISITED));

                // Push right and left children
                if (current.node.right != null) {
                    stack.push(new NodeState2(current.node.right, NodeStatus2.UNPROCESSED));
                }
                if (current.node.left != null) {
                    stack.push(new NodeState2(current.node.left, NodeStatus2.UNPROCESSED));
                }
            } else if (current.status == NodeStatus2.VISITED) {
                System.out.print(current.node.val + " ");
            }
        }
    }


    public static void main(String[] args) {
        ConstructTree tree = new ConstructTree();

        int[] inorder = {9, 3, 15, 20, 7};
        int[] postorder = {9, 15, 7, 20, 3};

        TreeNode root = tree.buildTree(inorder, postorder);

        System.out.print("Inorder traversal of constructed tree: ");
        tree.printInorder(root);
        System.out.println("\n");
        tree.printPostorderIterative(root);
        System.out.println("\n");
        tree.postorderWithState(root);
    }
}
