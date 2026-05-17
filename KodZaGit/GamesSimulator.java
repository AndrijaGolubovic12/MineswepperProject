import java.io.FileWriter;
import java.io.PrintWriter;

// Runs many automated Minesweeper matches and writes one summary row per match to CSV.
public class GamesSimulator {
    private static final int MATCHES = 1000;
    private static final int BOARD_SIZE = 8;

    // With a purely random bot, 6 mines can produce a very low win rate.
    // Redoce this to 3 if you want higher winrate.
    private static final int MINES = 6;

    private static final String OUTPUT_FILE = "matches.csv";

    // Factory method that's required.
    public static Player createBot(Board board) {
        return new Player(board);
    }

    public static void main(String[] args) throws Exception {
        try (PrintWriter writer = new PrintWriter(new FileWriter(OUTPUT_FILE))) {
            // CSV header. The analysis stage expects exactly these columns.
            writer.println("MatchId,BotType,Result,TimeMs,TotalClicks");

            for (int matchId = 1; matchId <= MATCHES; matchId++) {
                Board board = new Board(BOARD_SIZE, MINES);
                Player bot = createBot(board);

                // nanoTime is better for measuring elapsed time than currentTimeMillis but you can change it.
                long startTime = System.nanoTime();

                GameOutcome outcome;

                // Keep playing until the game ends with VICTORY or DEFEAT.
                do {
                    outcome = bot.playTurn();
                } while (outcome == GameOutcome.IN_PROGRESS);

                // Convert nanoseconds to milliseconds.
                // Math.max(1, ...) prevents a zero time, which is useful for click-rate analysis.
                long elapsedMs = Math.max(1, (System.nanoTime() - startTime) / 1_000_000);

                // Count moves by traversing the custom linked list.
                int totalClicks = countMoves(bot.getMoveHistory());

                // Append one summarized match row to the CSV file.
                writer.println(matchId + "," +
                        bot.getBotType() + "," +
                        outcome + "," +
                        elapsedMs + "," +
                        totalClicks);
            }
        }

        System.out.println("Simulation complete. Created " + OUTPUT_FILE);
    }

    // Traverses the custom linked list to count how many moves the bot made.
    private static int countMoves(MyLinkedList history) {
        int count = 0;
        NodeMove current = history.getHead();

        while (current != null) {
            count++;
            current = current.getNext();
        }

        return count;
    }
}
