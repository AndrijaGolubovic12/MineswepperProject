import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

// Stores all match summaries and provides stream-based analysis methods.
public class MatchDataset {
    // SortedSet keeps matches sorted according to MatchSummary.compareTo().
    private final SortedSet<MatchSummary> matches;

    public MatchDataset() {
        this.matches = new TreeSet<>();
    }

    public void add(MatchSummary match) {
        matches.add(match);
    }

    public int size() {
        return matches.size();
    }

    // Calculates average clicks for a concrete result, for example VICTORY or DEFEAT.
    public Double getAverageClicksByResult(String result) {
        return matches.stream()
                // Keep only matches with the requested result.
                .filter(match -> match.getResult().equalsIgnoreCase(result))

                // Convert MatchSummary objects into their totalClicks values.
                .mapToInt(MatchSummary::getTotalClicks)

                // Calculate average clicks.
                .average()

                // If no match has that result, return 0.0.
                .orElse(0.0);
    }

    // Finds the match with the highest totalClicks/timeMs ratio.
    public MatchSummary getMatchWithHighestClickRate() {
        Optional<MatchSummary> result = matches.stream()
                // Comparator.comparing is required by the assignment.
                .max(Comparator.comparing(MatchSummary::getClickRate));

        return result.orElse(null);
    }

    // Calculates how often a bot wins, in %.
    public Double getWinRateForBot(String botType) {
        long totalForBot = matches.stream()
                .filter(match -> match.getBotType().equalsIgnoreCase(botType))
                .count();

        if (totalForBot == 0) {
            return 0.0;
        }

        long victories = matches.stream()
                .filter(match -> match.getBotType().equalsIgnoreCase(botType))
                .filter(match -> match.getResult().equalsIgnoreCase("VICTORY"))
                .count();

        return victories * 100.0 / totalForBot;
    }

    // Finds the fastest match that ended in victory.
    public MatchSummary getFastestVictory() {
        return matches.stream()
                .filter(match -> match.getResult().equalsIgnoreCase("VICTORY"))
                .min(Comparator.comparingLong(MatchSummary::getTimeMs))
                .orElse(null);
    }

    // Groups matches by result and counts how many are in each group.
    // Example output could be: VICTORY -> 5, DEFEAT -> 995.
    public Map<String, Long> getMatchCountByResult() {
        return matches.stream()
                .collect(Collectors.groupingBy(
                        MatchSummary::getResult,
                        Collectors.counting()
                ));
    }
}
