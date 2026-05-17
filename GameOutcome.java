// Represents the state of the whole game after a move.
public enum GameOutcome {
    // The game is still running.
    IN_PROGRESS,

    // All safe cells were revealed.
    VICTORY,

    // A mine was revealed.
    DEFEAT
}
