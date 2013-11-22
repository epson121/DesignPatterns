
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
    public void playRound() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void calculateEfficiency() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
