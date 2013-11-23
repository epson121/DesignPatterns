
package lurajcevi_zadaca_2.model;

/**
 *
 * @author luka
 */
public class SingleClubResult extends Result {
    private final SportsClub sc;
    
    public SingleClubResult(SportsClub sc) {
        this.sc = sc;
    }

    public SportsClub getSc() {
        return sc;
    }
    
}
