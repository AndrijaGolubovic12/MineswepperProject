// Represents one square on the Minesweeper board.
// A Cell does not know its row/column; the Board controls positions.
public class Cell {
    // True if this cell contains a mine.
    private boolean mine;

    // Visual/gameplay state: hidden, revealed, or flagged.
    private CellState state;

    // Number of mines in the 8 neighboring cells.
    private int adjacentMines;

    public Cell() {
        this.mine = false;
        this.state = CellState.HIDDEN;
        this.adjacentMines = 0;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    // Revealing a cell means changing only its state.
    // Whether this causes victory/defeat is checked by Board.getGameState().
    public void reveal() {
        this.state = CellState.REVEALED;
    }

    public boolean isRevealed() {
        return state == CellState.REVEALED;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }
}
