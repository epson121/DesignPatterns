
package lurajcevi_zadaca_2.model;

/**
 *
 * @author luka
 */
public class SingleClubResult extends Result {
    private SportsClub firstClub;
    
    public SingleClubResult(SportsClub sc) {
        this.firstClub = sc;
    }

    @Override
    public void printResult() {
        System.out.println(firstClub.getSportsClubName() + " paused this round.");
    }
    
    
    
}
