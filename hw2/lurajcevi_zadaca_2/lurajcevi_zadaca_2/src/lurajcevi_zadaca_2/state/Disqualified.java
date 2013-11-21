
package lurajcevi_zadaca_2.state;

import lurajcevi_zadaca_2.model.SportsClub;

/**
 *
 * @author luka
 */
public class Disqualified implements SportsClubState {
    SportsClub sportsClub;
    
    public Disqualified(SportsClub sportsClub) {
        this.sportsClub = sportsClub;
    }
}
