// Custom singly linked list used to store the player's move history.
// It avoids java.util.ArrayList, java.util.LinkedList, and native arrays as required by project file.
public class MyLinkedList {
    // First node in the list.
    private NodeMove head;

    // Last node in the list. This makes insertions O(1).
    private NodeMove tail;

    // Number of moves stored.
    private int size;

    // Inserts a move at the end of the linked list.
    public void insert(Move move) {
        NodeMove node = new NodeMove(move);

        if (head == null) {
            // Empty list: first node is both head and tail.
            head = node;
            tail = node;
        } else {
            // Attach the new node after the current tail.
            tail.setNext(node);
            tail = node;
        }

        size++;
    }

    // Allows external traversal from the first node.
    // This is used by GamesSimulator to count total clicks.
    public NodeMove getHead() {
        return head;
    }

    public int size() {
        return size;
    }
}
