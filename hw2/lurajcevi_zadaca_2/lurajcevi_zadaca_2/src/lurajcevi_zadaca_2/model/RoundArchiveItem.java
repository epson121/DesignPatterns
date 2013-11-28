package lurajcevi_zadaca_2.model;

import java.util.ArrayList;
import java.util.List;
import lurajcevi_zadaca_2.command.Command;
import lurajcevi_zadaca_2.command.PositionGain;
import lurajcevi_zadaca_2.command.PositionLoss;
import lurajcevi_zadaca_2.command.SamePosition;

/**
 *
 * @author luka
 */
public class RoundArchiveItem {

    int roundId;
    private List<TableArchiveItem> tableList;
    private List<Result> resultList;
    
    /**
     * Creates new RoundArchive item
     * Saves data needed for reconstruction of a round
     * @param roundId
     * @param tableList
     * @param roundList 
     */
    public RoundArchiveItem(int roundId, List<TableArchiveItem> tableList, List<Result> roundList) {
        this.roundId = roundId;
        this.tableList = tableList;
        this.resultList = roundList;
    }

    public int getRoundId() {
        return roundId;
    }

    public List<TableArchiveItem> getTableList() {
        return tableList;
    }

    public List<Result> getResultList() {
        return resultList;
    }
    /**
    * Iterates through new table and the one from the last
    * control round. It creates a list of commands connected
    * to specific club regarding his new position status, i.e.
    * position loss, gain or same position.
    * @param sportsClubList
    * @return 
    */
    public List<Command> tableDifference(List<SportsClub> sportsClubList) {
        List<Command> result = new ArrayList<>();
        for (SportsClub scf : sportsClubList) {
            int firstClubId = scf.getSportsClubId();
            int positionNow = scf.getPosition();
            for (TableArchiveItem tai : this.tableList) {
                if (tai.getId() == firstClubId) {
                    int positionBefore = tai.getPosition();
                    System.out.println(scf.getSportsClubName() + " was " + 
                                           positionBefore + " and now is " + 
                                           positionNow);
                    if (positionBefore > positionNow) {
                        result.add(new PositionGain(scf));
                    } else if (positionBefore < positionNow) {
                        result.add(new PositionLoss(scf));
                    } else {
                        result.add(new SamePosition(scf));
                    }
                    break;
                }
            }
        }
        return result;
    }
    
    /**
     * Prints table from archive
     */
    public void printTable() {
        try {
            System.out.println("ID: " + roundId);
            for (TableArchiveItem ta : tableList) {
                ta.printItem();
            }
        } catch (Exception e) {
            System.out.println("Table was not saved.");
        }
    }
    
    /**
     * Prints results from archive
     */
    public void printResults() {
        for (Result r : resultList) {
            r.printResult();
        }
    }
    
    /**
     * Prints result from specific club in a round
     * @param id 
     */
    public void printResultFromSpecificClub(int id) {
        int fcId;
        int scId;
        boolean found = false;
        for(Result r : resultList) {
            try {
                fcId = r.getFirstClub().getSportsClubId();
                scId = r.getSecondClub().getSportsClubId();
                if (id == fcId || id == scId) {
                    found = true;
                    r.printResult();
                }
            } catch(Exception e) {
                continue;
            }
        }
        if (found == false) {
            System.out.println("Club paused this round.");
        }
    }
    
}
