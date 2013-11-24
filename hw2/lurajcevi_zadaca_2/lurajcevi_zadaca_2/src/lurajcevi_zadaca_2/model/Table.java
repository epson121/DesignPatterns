package lurajcevi_zadaca_2.model;

import lurajcevi_zadaca_2.archive.TableArchiveItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author luka
 */
public class Table {

    private List<SportsClub> sportsClubList;
    private static List<SportsClub> oldTable = null;
    private List<TableArchiveItem> tableArchive;

    public Table(List<SportsClub> sportsClubList) {
        this.sportsClubList = sportsClubList;
        generateTable();
    }

    private void generateTable() {
        // change the way positions are generated
        // 1, 1, 3 
        // instead of
        // 1, 1, 2
        Collections.sort(this.sportsClubList);
        int currentPoints = this.sportsClubList.get(0).getPoints();
        int currentPosition = 1;
        for (int i = 0; i < this.sportsClubList.size(); i++) {
            int nextClubPoints = this.sportsClubList.get(i).getPoints();
            if (nextClubPoints == currentPoints) {
                this.sportsClubList.get(i).setPosition(currentPosition);
            } else {
                currentPoints = nextClubPoints;
                currentPosition += 1;
                this.sportsClubList.get(i).setPosition(currentPosition);
            }
        }
        if (!tableChanged()) {
            this.sportsClubList = null;
        }
    }

    public List<SportsClub> getSportsClubList() {
        return sportsClubList;
    }
    
    public List<TableArchiveItem> createArchive() {
        if (sportsClubList == null) {
            tableArchive = null;
        }
        tableArchive = new ArrayList<>();
        for (SportsClub sc : sportsClubList) {
            tableArchive.add(new TableArchiveItem(sc.getPosition(), sc.getPoints(),
                                         sc.getSportsClubId(), sc.getSportsClubName()));
        }
        return tableArchive;
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
            if (Table.oldTable.equals(sportsClubList)) {
                for (SportsClub scf : this.getSportsClubList()) {
                    for (SportsClub scs : Table.oldTable) {
                        if (scs.getSportsClubId() == scs.getSportsClubId()) {
                            if (scf.getPosition() != scs.getPosition()) {
                                return true;
                            } else {
                                break;
                            }
                        }
                    }
                }
                return false;
            } else {
                Table.oldTable = new ArrayList<>(sportsClubList);
                return true;
            }
        }
    }

    
    
    

}
