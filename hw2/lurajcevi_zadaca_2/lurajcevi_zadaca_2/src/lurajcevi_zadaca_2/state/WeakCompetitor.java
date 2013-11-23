
package lurajcevi_zadaca_2.state;

import lurajcevi_zadaca_2.model.SportsClub;

/**
 *
 * @author luka
 */
public class WeakCompetitor implements SportsClubState {
    SportsClub sportsClub;
    int weakCounter;
    
    public WeakCompetitor(SportsClub sportsClub) {
        this.sportsClub = sportsClub;
        weakCounter = 0;
    }

    @Override
    public void decreasedEfficiency() {
        if (weakCounter == 0) {
            this.sportsClub.setState(sportsClub.getWeakCompetitor());
        } else if (weakCounter == 3){
            this.sportsClub.setState(sportsClub.getDisqualified());
            
        } else {
            weakCounter += 1;
        }
    }

    @Override
    public void increasedEfficiency() {
        if (this.sportsClub.getState() instanceof WeakCompetitor) {
            this.sportsClub.setState(sportsClub.getCompetitor());
            weakCounter = 0;
        }
    }

    @Override
    public boolean canPlay() {
        return false;
    }

    
}
