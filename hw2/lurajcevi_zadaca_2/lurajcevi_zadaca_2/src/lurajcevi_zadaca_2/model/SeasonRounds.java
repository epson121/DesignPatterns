package lurajcevi_zadaca_2.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luka
 */
public class SeasonRounds {

    private List<RoundArchiveItem> seasonRounds;
    private int roundCount;

    public SeasonRounds() {
        this.seasonRounds = new ArrayList<>();
        this.roundCount = 0;
    }

    public void saveRound(RoundArchiveItem round) {
        seasonRounds.add(round);
        roundCount += 1;
    }

    public List<RoundArchiveItem> getSeasonRounds() {
        return seasonRounds;
    }

    public int getRoundCount() {
        return roundCount;
    }
    
    public RoundArchiveItem getSeasonRound(int id) {
        return seasonRounds.get(id);
    }

    
}
