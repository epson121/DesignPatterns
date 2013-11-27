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
    private static List<SportsClub> oldTable = null;
    private List<TableArchiveItem> tableArchive;
    public static List<TableArchiveItem> oldTableArchive = null;

    public Table(List<SportsClub> sportsClubList) {
        this.sportsClubList = sportsClubList;
        generateTable();
    }

    private void generateTable() {
        Collections.sort(this.sportsClubList);
        int currentPoints = this.sportsClubList.get(0).getPoints();
        int currentPosition = 1;
        for (int i = 0; i < this.sportsClubList.size(); i++) {
            int nextClubPoints = this.sportsClubList.get(i).getPoints();
            if (nextClubPoints == currentPoints) {
                System.out.println("Postavi " + this.sportsClubList.get(i).getSportsClubName() + 
                        " na " + currentPosition);
                this.sportsClubList.get(i).setPosition(currentPosition);
            } else {
                currentPoints = nextClubPoints;
                currentPosition = i+1;
                System.out.println("Postavi " + this.sportsClubList.get(i).getSportsClubName() + 
                        " na " + currentPosition);
                this.sportsClubList.get(i).setPosition(currentPosition);
            }
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
            /*oldTableArchive.add(new TableArchiveItem(sc.getPosition(), sc.getPoints(),
                                         sc.getSportsClubId(), sc.getSportsClubName()));*/
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
        if (Table.oldTableArchive == null) {
            Table.oldTableArchive = new ArrayList<>(createArchive());
            return true;
        } else {
            for (int i = 0; i < sportsClubList.size(); i++) {
                SportsClub scf = sportsClubList.get(i);
                TableArchiveItem ta = Table.oldTableArchive.get(i);
                System.out.println("First: " + scf.getSportsClubName() + " " + scf.getPosition());
                System.out.println("Second : " + ta.getName() + " " + ta.getPosition());
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
