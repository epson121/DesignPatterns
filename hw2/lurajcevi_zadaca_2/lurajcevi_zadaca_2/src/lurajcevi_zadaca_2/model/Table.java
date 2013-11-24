
package lurajcevi_zadaca_2.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lurajcevi_zadaca_2.command.Command;
import lurajcevi_zadaca_2.command.PositionGain;
import lurajcevi_zadaca_2.command.PositionLoss;
import lurajcevi_zadaca_2.command.SamePosition;

/**
 *
 * @author luka
 */
public class Table {
    private List<SportsClub> sportsClubList;
    private static List<SportsClub> oldTable = null;

    public Table(List<SportsClub> sportsClubList) {
        this.sportsClubList = sportsClubList;
        generateTable();
    }
    
    private void generateTable() {
        Collections.sort(this.sportsClubList);
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
        System.out.println("INITIAL: ");
        printTable();
        if (!tableChanged()) {
            this.sportsClubList = null;
        }
    }

    public List<SportsClub> getSportsClubList() {
        return sportsClubList;
    }
    
    public void printTable() {
        for (SportsClub sc : this.sportsClubList) {
            System.out.println(sc.getPosition() + " " + sc.getSportsClubName()
                               + " " + sc.getPoints());
        }
    }
    
    public boolean tableChanged() {
        if (Table.oldTable == null) {
            Table.oldTable = new ArrayList<>(sportsClubList);
            return true;
        } else {
            System.out.println("OLD TABLE: ");
            for (SportsClub sc : oldTable) {
            System.out.println(sc.getPosition() + " " + sc.getSportsClubName()
                               + " " + sc.getPoints());
            }
            System.out.println("THIS TABLE: ");
            printTable();
            if (Table.oldTable.equals(sportsClubList)) {
                return false;
            } else {
                Table.oldTable = new ArrayList<>(sportsClubList);
                return true;
            }
        }
    }
    
     public List<Command> tableDifference(Table secondTable) {
        List<Command> result = new ArrayList<>();
        for (SportsClub scf : this.getSportsClubList()) {
            int firstClubId = scf.getSportsClubId();
            int positionBefore = scf.getPosition();
            for (SportsClub scs : secondTable.getSportsClubList()) {
                if (scs.getSportsClubId() == firstClubId){
                    int positionAfter = scs.getPosition();
                    if (positionBefore > positionAfter) {
                        result.add(new PositionLoss(scs));
                    } else if (positionBefore < positionAfter) {
                        result.add(new PositionGain(scs));
                    } else {
                        result.add(new SamePosition(scs));
                    }
                    break;
                }
            }
        }
        return result;
    }
    
}
