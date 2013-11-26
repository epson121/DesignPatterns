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
        List<Command> result = new ArrayList<>();
        for (SportsClub scf : sportsClubList) {
            int firstClubId = scf.getSportsClubId();
            int positionNow = scf.getPosition();
            for (TableArchiveItem tai : this.tableList) {
                int positionBefore = tai.getPosition();
                if (tai.getId() == firstClubId) {
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
        if (tableList == null){
            System.out.println("Table was not saved.");
        }
        System.out.println("ID: " + roundId);
        for (TableArchiveItem ta : tableList) {
            ta.printItem();
        }
    }
    
    public void printResults() {
        for (Result r : resultList) {
            r.printResult();
        }
    }
    
    public void printResultFromSpecificClub(int id) {
        for(Result r : resultList) {
            int fcId = r.getFirstClub().getSportsClubId();
            int scId = r.getSecondClub().getSportsClubId();
            if (id == fcId || id == scId) {
                r.printResult();
            }
        }
    }
    
}
