package lurajcevi_zadaca_2.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author luka
 */
public class SeasonRounds {

    private List<Round> seasonRounds;
    private int roundCount;

    public SeasonRounds() {
        this.seasonRounds = new ArrayList<>();
        this.roundCount = 0;
    }

    public void addRound(Round round) {
        seasonRounds.add(round);
        roundCount += 1;
    }

    public List<Round> getSeasonRounds() {
        return seasonRounds;
    }

    public int getRoundCount() {
        return roundCount;
    }
    
    public Round getSeasonRound(int id) {
        return seasonRounds.get(id-1);
    }

    
}
