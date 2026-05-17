import java.util.Map;

// Final entry point for milestone 3.
// Reads matches.csv and prints a clean telemetry report.
public class TestMatchesAnalysis {

    public static void main(String[] args) throws Exception {
        // The simulator must be run first so matches.csv exists.
        MatchDataset dataset = MatchFactory.parseFile("matches.csv");

        System.out.println("======================================");
        System.out.println(" MINESWEEPER TELEMETRY REPORT");
        System.out.println("======================================");
        System.out.println("Total matches loaded: " + dataset.size());
        System.out.println();

        System.out.println("1) Average clicks by result");
        System.out.printf("   VICTORY: %.2f clicks%n", dataset.getAverageClicksByResult("VICTORY"));
        System.out.printf("   DEFEAT : %.2f clicks%n", dataset.getAverageClicksByResult("DEFEAT"));
        System.out.println();

        System.out.println("2) Win rate by bot");
        System.out.printf("   RandomBot: %.2f%%%n", dataset.getWinRateForBot("RandomBot"));
        System.out.println();

        System.out.println("3) Match with highest click rate");
        MatchSummary highestClickRate = dataset.getMatchWithHighestClickRate();
        System.out.println("   " + (highestClickRate == null ? "No data" : highestClickRate));
        System.out.println();

        System.out.println("4) Fastest victory");
        MatchSummary fastestVictory = dataset.getFastestVictory();
        System.out.println("   " + (fastestVictory == null ? "No victory found" : fastestVictory));
        System.out.println();

        System.out.println("5) Match count by result");
        for (Map.Entry<String, Long> entry : dataset.getMatchCountByResult().entrySet()) {
            System.out.println("   " + entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("======================================");
    }
}
