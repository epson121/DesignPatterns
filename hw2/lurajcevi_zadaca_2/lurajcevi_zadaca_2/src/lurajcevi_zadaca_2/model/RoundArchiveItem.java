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
    
    public List<Command> tableDifference(List<SportsClub> sportsClubList) {
        List<TableArchiveItem> tl;
        if (this.tableList == null) {
            
        } else {
            
        }
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
    
    public void printResults() {
        for (Result r : resultList) {
            r.printResult();
        }
    }
    
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
        //System.out.println(found);
        if (found == false) {
            System.out.println("Club paused this round.");
        }
    }
    
}
