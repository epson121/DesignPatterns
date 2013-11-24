
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
    public boolean canPlay() {
        return true;
    }

    @Override
    public void positionLoss() {
        this.sportsClub.setState(sportsClub.getWeakCompetitor());
    }

    @Override
    public void positionGain() {
        // do nothing
    }

    @Override
    public void samePosition() {
        // do nothing
    }


}
