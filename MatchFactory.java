import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

// Factory class for converting CSV text into MatchSummary objects.
public class MatchFactory {

    // Parses one non-header CSV line.
    public static MatchSummary parseMatch(String csvLine) {
        String[] parts = csvLine.split(",");

        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid CSV line: " + csvLine);
        }

        int matchId = Integer.parseInt(parts[0].trim());
        String botType = parts[1].trim();
        String result = parts[2].trim();
        long timeMs = Long.parseLong(parts[3].trim());
        int totalClicks = Integer.parseInt(parts[4].trim());

        return new MatchSummary(matchId, botType, result, timeMs, totalClicks);
    }

    // Reads the entire CSV file and returns a MatchDataset.
    public static MatchDataset parseFile(String filename) throws Exception {
        List<String> lines = Files.readAllLines(Path.of(filename));
        MatchDataset dataset = new MatchDataset();

        // Start from index 1 because index 0 is the CSV header.
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();

            if (!line.isEmpty()) {
                dataset.add(parseMatch(line));
            }
        }

        return dataset;
    }
}
