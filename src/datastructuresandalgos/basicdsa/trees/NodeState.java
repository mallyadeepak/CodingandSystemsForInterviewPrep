package datastructuresandalgos.basicdsa.trees;

/**
 * Wrapper over Node with node status.
 */
public class NodeState {
    public enum State {
        UN_PROCESSED, PROCESSED
    }

    private Node node;
    private NodeState.State state;

    public NodeState(Node node) {
        this.node = node;
        this.state = NodeState.State.UN_PROCESSED;
    }

    public Node getNode() {
        return node;
    }

    public NodeState.State getState() {
        return state;
    }

    public void setState(NodeState.State state) {
        this.state = state;
    }
}
