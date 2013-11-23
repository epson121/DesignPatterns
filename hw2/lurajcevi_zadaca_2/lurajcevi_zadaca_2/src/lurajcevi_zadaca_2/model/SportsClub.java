
package lurajcevi_zadaca_2.model;

import java.util.Comparator;
import lurajcevi_zadaca_2.observer.Observer;
import lurajcevi_zadaca_2.state.Competitor;
import lurajcevi_zadaca_2.state.Disqualified;
import lurajcevi_zadaca_2.state.SportsClubState;
import lurajcevi_zadaca_2.state.WeakCompetitor;

/**
 *
 * @author luka
 */
public class SportsClub implements Comparable<SportsClub>, Observer {
    
    private int sportsClubId;
    private int points;
    private int position;
    private String sportsClubName;
    private SportsClubState competitor;
    private SportsClubState weakCompetitor;
    private SportsClubState disqualified;
    private Season season;
    
    // set starting state
    private SportsClubState state;

    public SportsClub(int sportsClubId, String sportsClubName, Season season) {
        // instantiate states
        this.competitor = new Competitor(this);
        this.disqualified = new Disqualified(this);
        this.weakCompetitor = new WeakCompetitor(this);
        this.state = competitor;
        this.season = season;
        season.registerObserver(this);
        
        this.sportsClubId = sportsClubId;
        this.sportsClubName = sportsClubName;
        this.points = 0;
        this.position = 1;
        
    }

    public int getSportsClubId() {
        return sportsClubId;
    }

    public String getSportsClubName() {
        return sportsClubName;
    }

    public SportsClubState getState() {
        return state;
    }

    public int getPoints() {
        return points;
    }

    public void updatePoints(int points) {
        this.points += points;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    
    /**
     * needed for comparable interface
     * if it returns negative number, this SportsCLub is smaller than the other
     * if it returns 0, they are the same
     * if it returns positive number, the SportsClub is bigger than the other
     * @param o
     * @return 
     */
    @Override
    public int compareTo(SportsClub o) {
        int comparePoints = o.getPoints();
        return comparePoints - this.points;
    }
    
    public static Comparator<SportsClub> SportsClubComparator 
                          = new Comparator<SportsClub>() {
 
	    public int compare(SportsClub sc1, SportsClub sc2) {
 
	      //ascending order
	      return sc1.compareTo(sc2);
 
	      //descending order
	      //return fruitName2.compareTo(fruitName1);
	    }
 
	};

    public void setState(SportsClubState state) {
        this.state = state;
    }

    public SportsClubState getCompetitor() {
        return competitor;
    }

    public SportsClubState getWeakCompetitor() {
        return weakCompetitor;
    }

    public SportsClubState getDisqualified() {
        return disqualified;
    }
    
    public boolean canPlay() {
        return this.state.canPlay();
    }
    
}
