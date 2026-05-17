// Represents one row from matches.csv as a Java object.
// It implements Comparable so it can be stored inside a SortedSet/TreeSet.
public class MatchSummary implements Comparable<MatchSummary> {
    private final int matchId;
    private final String botType;
    private final String result;
    private final long timeMs;
    private final int totalClicks;

    public MatchSummary(int matchId, String botType, String result, long timeMs, int totalClicks) {
        this.matchId = matchId;
        this.botType = botType;
        this.result = result;
        this.timeMs = timeMs;
        this.totalClicks = totalClicks;
    }

    public int getMatchId() {
        return matchId;
    }

    public String getBotType() {
        return botType;
    }

    public String getResult() {
        return result;
    }

    public long getTimeMs() {
        return timeMs;
    }

    public int getTotalClicks() {
        return totalClicks;
    }

    // Click rate is defined by the assignment as totalClicks / timeMs.
    public double getClickRate() {
        // If timeMs is zero, treat it as 1 to avoid division by zero.
        long safeTime = timeMs == 0 ? 1 : timeMs;
        return (double) totalClicks / safeTime;
    }

    @Override
    public int compareTo(MatchSummary other) {
        // Natural sorting: fastest matches first.
        int byTime = Long.compare(this.timeMs, other.timeMs);

        if (byTime != 0) {
            return byTime;
        }
        // If two matches have the same time, compare by matchId too.
        // Otherwise TreeSet may think they are duplicates and remove one.
        return Integer.compare(this.matchId, other.matchId);
    }

    @Override
    public String toString() {
        return "Match #" + matchId +
                " | Bot=" + botType +
                " | Result=" + result +
                " | TimeMs=" + timeMs +
                " | TotalClicks=" + totalClicks +
                " | ClickRate=" + String.format("%.3f", getClickRate());
    }
}
