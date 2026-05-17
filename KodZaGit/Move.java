// Represents one click made by the bot.
// This becomes the payload stored in the custom linked list.
public class Move {
    private final int row;
    private final int column;

    // true means the clicked cell was not a mine.
    // false means the bot clicked a mine.
    private final boolean wasSafe;

    public Move(int row, int column, boolean wasSafe) {
        this.row = row;
        this.column = column;
        this.wasSafe = wasSafe;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean wasSafe() {
        return wasSafe;
    }
}
