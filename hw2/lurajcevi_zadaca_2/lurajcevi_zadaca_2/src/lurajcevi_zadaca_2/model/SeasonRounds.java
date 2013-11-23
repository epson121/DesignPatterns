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

    public SeasonRounds() {
        this.seasonRounds = new ArrayList<>();
    }

    public void addRound(Round round) {
        seasonRounds.add(round);
    }

    public List<Round> getSeasonRounds() {
        return seasonRounds;
    }
    
    

    public Iterator<Round> iterator() {
        return new SeasonRoundsIterator();
    }

    class SeasonRoundsIterator implements Iterator<Round> {
        
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
        public Round next() {
            return seasonRounds.get(currentIndex++);
        }

        @Override
        public void remove() {
            seasonRounds.remove(--currentIndex);
        }

    }
}
