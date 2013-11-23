
package lurajcevi_zadaca_2.state;

import lurajcevi_zadaca_2.model.SportsClub;

/**
 *
 * @author luka
 */
public class Competitor implements SportsClubState {
    SportsClub sportsClub;
    
    public Competitor(SportsClub sportsClub) {
        this.sportsClub = sportsClub;
    }

     @Override
    public void decreasedEfficiency() {
       this.sportsClub.setState(sportsClub.getWeakCompetitor());
    }

    @Override
    public void increasedEfficiency() {
        // do nothing
    }

    @Override
    public boolean canPlay() {
        return true;
    }


}
