
package lurajcevi_zadaca_2.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
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
    private double efficiency;
    private String sportsClubName;
    private SportsClubState competitor;
    private SportsClubState weakCompetitor;
    private SportsClubState disqualified;
    private Season season;
    private List<Integer> roundsPlayedList;
    
    // set starting state
    private SportsClubState state;

    public SportsClub(int sportsClubId, String sportsClubName, Season season) {
        // instantiate states
        this.competitor = new Competitor(this);
        this.disqualified = new Disqualified(this);
        this.weakCompetitor = new WeakCompetitor(this);
        this.state = competitor;
        this.season = season;
        
        this.sportsClubId = sportsClubId;
        this.sportsClubName = sportsClubName;
        this.points = 0;
        this.position = 1;
        this.roundsPlayedList = new ArrayList<>();
        this.efficiency = 1.0;
        season.registerObserver(this);
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

    public List<Integer> getRoundsPlayedList() {
        return roundsPlayedList;
    }

    public void addRoundPlayed(int roundPlayedId) {
        this.roundsPlayedList.add(roundPlayedId);
    }

    public double getEfficiency() {
        return efficiency;
    }
    
    public void notifyForEfficiency(double efficiency) {
        if (this.efficiency != efficiency) {
            DecimalFormat df = new DecimalFormat("#.##");
            System.out.println("Change in efficiency:");
            System.out.println(this.sportsClubName + " went from " 
                               + df.format(this.efficiency) + " to " 
                               + df.format(efficiency));
            this.efficiency = efficiency;
        }
    }
    
    public void positionLoss() {
        state.positionLoss();
    }
    
    public void positionGain() {
        state.positionGain();
    }
    
    public void samePosition() {
        state.samePosition();
    }
}
