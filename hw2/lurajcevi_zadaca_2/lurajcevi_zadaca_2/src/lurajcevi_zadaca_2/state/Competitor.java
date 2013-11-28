
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
    
    /**
     * change state if club loses a position while being competitor
     */
    @Override
    public void positionLoss() {
        System.out.println(sportsClub.getSportsClubName()
                           + " went from being COMPETITOR to being WEAK COMPETITOR.");
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
