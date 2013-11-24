package lurajcevi_zadaca_2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lurajcevi_zadaca_2.command.Command;
import lurajcevi_zadaca_2.observer.Observer;
import lurajcevi_zadaca_2.observer.Subject;

/**
 *
 * @author luka
 */
public class Season extends Thread implements Subject {

    private List<Observer> observerList = new ArrayList<>();
    private Random rand = new Random();
    private SeasonRounds seasonRounds = new SeasonRounds();
    private SeasonRounds controlSeasonRounds = new SeasonRounds();
    public static int roundId = 1;
    private int sleepInterval, controlInterval, threshold;
    
    public static enum SportsClubTableStatus {
        SAME_POSITION, POSITION_GAIN, POSITION_LOSS
    }
    

    // TODO dati argument za dretvu
    public Season(int sleepInterval, int controlInterval, int threshold) {
        this.sleepInterval = sleepInterval;
        this.controlInterval = controlInterval;
        this.threshold = threshold;
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void run() {
        Table roundTable;
        Round round;
        int controlIntervalCounter = 0;
        long start, duration = 0;
        while (true) {
            try {
                start = System.currentTimeMillis();
                System.out.println("ROUND " + Season.roundId);
                roundTable = new Table(getSportsClubList());
                round = new Round(Season.roundId,
                                  roundTable,
                                  this.generateRoundResults(Season.roundId));
                round.printResults();
                round.printTable();
                notifyForEfficiency();
                if (controlSeasonRounds.getRoundCount() == 0) {
                    controlSeasonRounds.addRound(round);
                }
                seasonRounds.addRound(round);
                Season.roundId += 1;
                controlIntervalCounter += 1;
                if (controlIntervalCounter == controlInterval) {
                    controlIntervalCounter = 0;
                    controlSeasonRounds.addRound(round);
                    //TODO check status of every club and change states accordingly
                    updateClubStatus();
                }
                duration = System.currentTimeMillis() - start;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                int interval;
                //TODO take argument from main (make static, or send as parameter)
                interval = (int) ((sleepInterval * 1000) - duration);
                System.out.println("Spavanje: " + interval);
                sleep(interval);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }

    public List<Result> generateRoundResults(int roundId) {
        // only from the competitors
        ArrayList<Observer> sc = getCompetitors();
        /*for (Observer o : sc) {
         System.out.println("EL");
         }*/
        List<Result> results = new ArrayList<>();
        while (sc.size() > 1) {
            SportsClub first = (SportsClub) sc.get(rand.nextInt(sc.size()));
            sc.remove(first);
            SportsClub second = (SportsClub) sc.get(rand.nextInt(sc.size()));
            sc.remove(second);
            Result r = new Result(first, second, roundId);
            results.add(r);
        }
        if (!sc.isEmpty()) {
            results.add(new SingleClubResult((SportsClub) sc.get(0)));
        }
        return results;
    }

    public Table generateTable() {
        return null;
    }

    @Override
    public void registerObserver(Observer o) {
        this.observerList.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        this.observerList.remove(o);
    }

    @Override
    public void notifyObserver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private ArrayList<Observer> getCompetitors() {
        ArrayList<Observer> result = new ArrayList<>();
        for (Observer o : observerList) {
            SportsClub s = (SportsClub) o;
            if (s.canPlay()) {
                result.add(o);
            }
            //System.out.println(s.getSportsClubName());
        }
        return result;
    }

    private List<SportsClub> getSportsClubList() {
        ArrayList<SportsClub> result = new ArrayList<>();
        for (Observer o : observerList) {
            SportsClub s = (SportsClub) o;
            result.add(s);
        }
        return result;
    }

    private void notifyForEfficiency() {
        for (Observer o : observerList) {
            SportsClub s = (SportsClub) o;
            int points = s.getPoints();
            int rounds = s.getRoundsPlayedList().size();
            if (rounds != 0)
                s.notifyForEfficiency((double) points/rounds);
        }
    }

    private void updateClubStatus() {
        int length = controlSeasonRounds.getRoundCount();
        Table lastControlTable, newControlTable;
        List<Command> tableChanges = null;
        if (length >= 2) {
            lastControlTable = controlSeasonRounds.getSeasonRound(length-1).getTable();
            newControlTable = controlSeasonRounds.getSeasonRound(length-2).getTable();
            tableChanges = lastControlTable.tableDifference(newControlTable);
        }
        for (Command c : tableChanges) {
            c.execute();
        }
    }
    
}
