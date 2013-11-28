package lurajcevi_zadaca_2.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author luka
 */
public class SeasonRounds {

    private List<RoundArchiveItem> seasonRounds;
    private int roundCount;
    
    /**
     * Creates an object that holds all archived rounds.
     */
    public SeasonRounds() {
        this.seasonRounds = new ArrayList<>();
        this.roundCount = 0;
    }
    
    /**
     * Save round to a list
     * @param round 
     */
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
    
    /**
     * Iterator for the season rounds
     * @return 
     */
    public Iterator<RoundArchiveItem> iterator() {
        return new RoundArchiveItemIterator();
    }
    
    /**
     * Implementation of the iterator for this type of object
     */
    class RoundArchiveItemIterator implements Iterator<RoundArchiveItem> {

        int currentIndex = 0;

        @Override
        public boolean hasNext() {
            if (currentIndex >= seasonRounds.size()) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public RoundArchiveItem next() {
            return seasonRounds.get(currentIndex++);
        }

        @Override
        public void remove() {
            // not needed
        }

    }
}
