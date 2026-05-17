import java.util.Random;

// Automated Minesweeper bot.
// In this version, the bot is intentionally random because the assignment asks for random unrevealed cells
// which leads to disproportional winrate.
public class Player {
    private final Board board;
    private final MyLinkedList moveHistory;
    private final Random random;
    private final String botType;

    public Player(Board board) {
        this(board, "RandomBot", new Random());
    }

    public Player(Board board, String botType, Random random) {
        this.board = board;
        this.botType = botType;
        this.random = random;
        this.moveHistory = new MyLinkedList();
    }

    // Executes exactly one bot turn.
    // Returns the game state after that turn.
    public GameOutcome playTurn() {
        // If the game already ended, do not make another move.
        if (board.getGameState() != GameOutcome.IN_PROGRESS) {
            return board.getGameState();
        }

        int hiddenCells = countHiddenCells();

        if (hiddenCells == 0) {
            return board.getGameState();
        }

        // We choose a random hidden cell without building an ArrayList.
        // First choose a hidden-cell index, then find that hidden cell by scanning the board.
        int selectedIndex = random.nextInt(hiddenCells);
        int currentHiddenIndex = 0;

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (!board.isRevealed(row, col)) {
                    if (currentHiddenIndex == selectedIndex) {
                        // Record whether this click is safe before revealing.
                        boolean safe = !board.isMine(row, col);

                        board.revealCell(row, col);

                        // Store this click in the custom linked list.
                        moveHistory.insert(new Move(row, col, safe));

                        return board.getGameState();
                    }

                    currentHiddenIndex++;
                }
            }
        }

        return board.getGameState();
    }

    // Counts hidden cells by scanning the board.
    // We do this to avoid using a Java collection to store hidden cells.
    private int countHiddenCells() {
        int count = 0;

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (!board.isRevealed(row, col)) {
                    count++;
                }
            }
        }

        return count;
    }

    public MyLinkedList getMoveHistory() {
        return moveHistory;
    }

    public String getBotType() {
        return botType;
    }
}
