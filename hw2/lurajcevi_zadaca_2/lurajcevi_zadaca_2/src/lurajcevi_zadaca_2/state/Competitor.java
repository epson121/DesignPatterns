
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
}
