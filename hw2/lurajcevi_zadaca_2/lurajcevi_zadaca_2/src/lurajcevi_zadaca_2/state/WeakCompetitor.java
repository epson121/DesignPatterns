
package lurajcevi_zadaca_2.state;

import lurajcevi_zadaca_2.model.SportsClub;

/**
 *
 * @author luka
 */
public class WeakCompetitor implements SportsClubState {
    SportsClub sportsClub;
    
    public WeakCompetitor(SportsClub sportsClub) {
        this.sportsClub = sportsClub;
    }
}
