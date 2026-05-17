import java.util.Random;

// Main Minesweeper game engine.
// Responsible for the matrix, mine placement, adjacent counts, BFS reveal, and game outcome.
public class Board {
    // The board is a matrix of Cell objects.
    private final Cell[][] grid;

    // Board is square: size x size.
    private final int size;

    private final int numMines;
    private final Random random;

    public Board(int size, int numMines) {
        this(size, numMines, new Random());
    }

    public Board(int size, int numMines, Random random) {
        // Input validation that's required.
        if (size <= 0) {
            throw new IllegalArgumentException("Board size must be positive.");
        }

        if (numMines < 0 || numMines >= size * size) {
            throw new IllegalArgumentException("Number of mines must be non-negative and less than size * size.");
        }

        this.size = size;
        this.numMines = numMines;
        this.random = random;
        this.grid = new Cell[size][size];

        initializeCells();
        placeMines();
        calculateAdjacentMines();
    }

    // Creates an actual Cell object in every matrix position.
    private void initializeCells() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                grid[row][col] = new Cell();
            }
        }
    }

    // Randomly places mines while avoiding duplicate mine positions.
    private void placeMines() {
        int placed = 0;

        while (placed < numMines) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);

            // Only place a mine if this cell does not already contain one.
            if (!grid[row][col].isMine()) {
                grid[row][col].setMine(true);
                placed++;
            }
        }
    }

    // Calculates how many mines surround each safe cell.
    private void calculateAdjacentMines() {
        // dx and dy represent the 8 possible neighbor directions.
        // Example: (-1, -1) means top-left, (1, 0) means down.
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                // Mine cells do not need an adjacent-mine number.
                if (grid[row][col].isMine()) {
                    continue;
                }

                int mineCount = 0;

                // Check all 8 neighboring positions.
                for (int i = 0; i < dx.length; i++) {
                    int neighborRow = row + dx[i];
                    int neighborCol = col + dy[i];

                    // Avoid going outside the board edges/corners.
                    if (isInsideBoard(neighborRow, neighborCol) && grid[neighborRow][neighborCol].isMine()) {
                        mineCount++;
                    }
                }

                grid[row][col].setAdjacentMines(mineCount);
            }
        }
    }
 // Adds all hidden safe neighbors of a zero-cell to the BFS queue.
    private void addHiddenSafeNeighbors(CoordinateQueue queue, int row, int col) {
        for (int rowDelta = -1; rowDelta <= 1; rowDelta++) {
            for (int colDelta = -1; colDelta <= 1; colDelta++) {
                // Skip the center cell itself.
                if (rowDelta == 0 && colDelta == 0) {
                    continue;
                }

                int neighborRow = row + rowDelta;
                int neighborCol = col + colDelta;

                if (isInsideBoard(neighborRow, neighborCol)) {
                    Cell neighbor = grid[neighborRow][neighborCol];

                    if (neighbor.getState() == CellState.HIDDEN && !neighbor.isMine()) {
                        queue.add(neighborRow, neighborCol);
                    }
                }
            }
        }
    }
    // Reveals the selected cell.
    // If the selected cell has 0 adjacent mines, it starts a BFS cascade reveal.
    public void revealCell(int row, int col) {
        validateCoordinates(row, col);

        Cell startingCell = grid[row][col];

        // Do nothing if the cell is already revealed or flagged.
        if (startingCell.getState() != CellState.HIDDEN) {
            return;
        }

        // If a mine is clicked, reveal it and let getGameState() report DEFEAT.
        if (startingCell.isMine()) {
            startingCell.reveal();
            return;
        }

        // BFS queue starts with the clicked safe cell.
        CoordinateQueue queue = new CoordinateQueue();
        queue.add(row, col);

        while (!queue.isEmpty()) {
            CoordinateQueue.Coordinate coordinate = queue.remove();

            int currentRow = coordinate.getRow();
            int currentCol = coordinate.getCol();

            if (!isInsideBoard(currentRow, currentCol)) {
                continue;
            }

            Cell currentCell = grid[currentRow][currentCol];

            // Skip cells that were already processed.
            // Also never reveal mines during cascade.
            if (currentCell.getState() != CellState.HIDDEN || currentCell.isMine()) {
                continue;
            }

            currentCell.reveal();

            // Cascade only continues from empty cells.
            // Numbered cells are revealed, but they do not expand further.
            if (currentCell.getAdjacentMines() == 0) {
            	addHiddenSafeNeighbors(queue, currentRow, currentCol);
            }
        }
    }

   

    // Determines whether the game is lost, won, or still running.
    public GameOutcome getGameState() {
        boolean allSafeCellsRevealed = true;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Cell cell = grid[row][col];

                // Revealing any mine means immediate defeat.
                if (cell.isMine() && cell.isRevealed()) {
                    return GameOutcome.DEFEAT;
                }

                // Victory requires every non-mine cell to be revealed.
                if (!cell.isMine() && !cell.isRevealed()) {
                    allSafeCellsRevealed = false;
                }
            }
        }

        return allSafeCellsRevealed ? GameOutcome.VICTORY : GameOutcome.IN_PROGRESS;
    }

    public boolean isRevealed(int row, int col) {
        validateCoordinates(row, col);
        return grid[row][col].isRevealed();
    }

    public boolean isMine(int row, int col) {
        validateCoordinates(row, col);
        return grid[row][col].isMine();
    }

    public int getSize() {
        return size;
    }

    public Cell getCell(int row, int col) {
        validateCoordinates(row, col);
        return grid[row][col];
    }

    private boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    private void validateCoordinates(int row, int col) {
        if (!isInsideBoard(row, col)) {
            throw new IllegalArgumentException("Coordinates are outside the board.");
        }
    }
}
