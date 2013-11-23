
package lurajcevi_zadaca_2.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author luka
 */
public class Table {
    private List<SportsClub> sportsClubList;

    public Table(List<SportsClub> sportsClubList) {
        this.sportsClubList = sportsClubList;
    }
    
    public void generateTable() {
        //TODO check if table place changed and notify
        Collections.sort(this.sportsClubList);
        /*for (SportsClub s : this.sportsClubList) {
            System.out.println(s.getSportsClubName());
        }*/
        int currentPoints = this.sportsClubList.get(0).getPoints();
        int currentPosition = 1;
        for (int i = 0; i < this.sportsClubList.size(); i++){
            int nextClubPoints = this.sportsClubList.get(i).getPoints();
            if (nextClubPoints == currentPoints) {
                this.sportsClubList.get(i).setPosition(currentPosition);
            } else {
              currentPoints = nextClubPoints;
              currentPosition += 1;
              this.sportsClubList.get(i).setPosition(currentPosition);
            }
        }
    }

    public List<SportsClub> getSportsClubList() {
        return sportsClubList;
    }
    
    public void printTable() {
        for (SportsClub sc : sportsClubList) {
            System.out.println(sc.getPosition() + " " + sc.getSportsClubName()
                               + " " + sc.getPoints());
        }
    }
    
}
