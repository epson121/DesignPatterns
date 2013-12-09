package lurajcevi_zadaca_2.state;

import lurajcevi_zadaca_2.model.Season;
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
    
    /**
     * Do when a club loses a position
     */
    @Override
    public void positionLoss() {
        weakCounter += 1;
        if (weakCounter >= Season.threshold) {
            System.out.println(this.sportsClub.getSportsClubName() + " is DISQUALIFIED.");
            disqualify();
        }
    }
    
    /**
     * Disqualify club, helper function
     */
    private void disqualify() {
        System.out.println(this.sportsClub.getPosition() + "  "
                               + this.sportsClub.getSportsClubName() + "  "
                               + this.sportsClub.getPoints());
        this.sportsClub.setState(sportsClub.getDisqualified());
        this.sportsClub.unsubscribe();
    }
    
    /**
     * Do when a club gains a position
     */
    @Override
    public void positionGain() {
        System.out.println(this.sportsClub.getSportsClubName() + " went from"
                           + " being WEAK COMPETITOR to being COMPETITOR.");
        this.sportsClub.setState(sportsClub.getCompetitor());
        weakCounter = 0;
    }
    
    /**
     * Do when club does not change position
     */
    @Override
    public void samePosition() {
        weakCounter += 1;
        if (weakCounter >= Season.threshold) {
            System.out.println(this.sportsClub.getSportsClubName() + " is DISQUALIFIED.");
            disqualify();
        } 
    }
}
