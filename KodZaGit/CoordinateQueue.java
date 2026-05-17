// Custom FIFO queue used by Board.revealCell() for BFS cascade reveal.
// IMPORTANT: This class does NOT use java.util collections or native arrays.
public class CoordinateQueue {

    // Public return type for dequeue().
    // We use this instead of int[] so the queue still avoids native arrays.
    public static final class Coordinate {
        private final int row;
        private final int col;

        private Coordinate(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }
    }

    // Private static node class required by the assignment.
    // Because it is private, other classes cannot depend on the internal implementation.
    private static class CoordinateNode {
        private final int row;
        private final int col;
        private CoordinateNode next;

        private CoordinateNode(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    // front is the next element that will be removed.
    private CoordinateNode front;

    // rear is the last element, where new coordinates are added.
    private CoordinateNode rear;

    // Adds a coordinate at the back of the queue.
    public void add(int row, int col) {
        CoordinateNode node = new CoordinateNode(row, col);

        if (rear == null) {
            // Empty queue: the new node is both front and rear.
            front = node;
            rear = node;
        } else {
            // Non-empty queue: link the old rear to the new node.
            rear.next = node;
            rear = node;
        }
    }

    // Removes and returns the coordinate at the front of the queue.
    public Coordinate remove() {
        if (front == null) {
            return null;
        }

        Coordinate result = new Coordinate(front.row, front.col);

        // Move front forward. The old node becomes unreachable and can be garbage-collected.
        front = front.next;

        // If the queue became empty, rear must also be null.
        if (front == null) {
            rear = null;
        }

        return result;
    }

    public boolean isEmpty() {
        return front == null;
    }
}
