package lurajcevi_zadaca_2.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author luka
 */
public class Table {

    private List<SportsClub> sportsClubList;
    private List<TableArchiveItem> tableArchive;
    public static List<TableArchiveItem> oldTableArchive = null;

    public Table(List<SportsClub> sportsClubList) {
        this.sportsClubList = sportsClubList;
        generateTable();
    }
    
    /**
     * Generate table for the new round.
     * Sort the clubs by their points. Update their positions.
     */
    private void generateTable() {
        Collections.sort(this.sportsClubList);
        int currentPoints = this.sportsClubList.get(0).getPoints();
        int currentPosition = 1;
        for (int i = 0; i < this.sportsClubList.size(); i++) {
            int nextClubPoints = this.sportsClubList.get(i).getPoints();
            if (nextClubPoints == currentPoints) {
                this.sportsClubList.get(i).setPosition(currentPosition);
            } else {
                currentPoints = nextClubPoints;
                currentPosition = i+1;
                this.sportsClubList.get(i).setPosition(currentPosition);
            }
        }
    }

    public List<SportsClub> getSportsClubList() {
        return sportsClubList;
    }
    
    /**
     * Create archive of the newly created table
     * @return 
     */
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
    
    /**
     * Print newly created table
     */
    public void printTable() {
        for (SportsClub sc : this.sportsClubList) {
            System.out.println(sc.getPosition() + " " + sc.getSportsClubName()
                    + " " + sc.getPoints());
        }
    }
    
    /**
     * Check if table was changed (from the last round)
     * @return 
     */
    public boolean tableChanged() {
        if (Table.oldTableArchive == null) {
            Table.oldTableArchive = new ArrayList<>(createArchive());
            return true;
        } else {
            for (int i = 0; i < sportsClubList.size(); i++) {
                SportsClub scf = sportsClubList.get(i);
                TableArchiveItem ta = Table.oldTableArchive.get(i);
                if (scf.getSportsClubId() == ta.getId()) {
                    if (scf.getPosition() != ta.getPosition()) {
                        Table.oldTableArchive = new ArrayList<>(createArchive());
                        return true;
                    }
                } else {
                    Table.oldTableArchive = new ArrayList<>(createArchive());
                    return true;
                }
            }
            return false;
        }
    }

}
