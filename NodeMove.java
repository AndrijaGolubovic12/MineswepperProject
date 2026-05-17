// Node used in the custom singly linked list.
// Each node stores one Move and a reference to the next node.
public class NodeMove {
    private final Move move;
    private NodeMove next;

    public NodeMove(Move move) {
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    public NodeMove getNext() {
        return next;
    }

    public void setNext(NodeMove next) {
        this.next = next;
    }
}
