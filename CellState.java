// Represents how a cell currently looks to the player/bot.
// This is separate from whether the cell is actually a mine.
public enum CellState {
    // The cell has not been opened yet.
    HIDDEN,

    // The cell has been opened and its content is visible.
    REVEALED,

    // The cell is marked as suspected mine.
    // The current RandomBot does not use flags much, but the assignment requires this state.
    FLAGGED
}
