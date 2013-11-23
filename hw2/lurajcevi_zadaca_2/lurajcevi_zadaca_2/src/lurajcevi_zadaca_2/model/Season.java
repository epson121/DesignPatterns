package lurajcevi_zadaca_2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    public static int roundId = 1;

    // TODO dati argument za dretvu
    public Season() {
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void run() {
        List<Result> roundResults;
        Table roundTable;
        Round round;
        long start, duration = 0;
        while (true) {
            try {
                start = System.currentTimeMillis();
                // TODO check if tables have changed and append new one or null
                roundTable = new Table(getSportsClubList());
                //roundTable.printTable();
                round = new Round(Season.roundId,
                                  roundTable,
                                  this.generateRoundResults(Season.roundId));
                round.printResults();
                round.printTable();
                seasonRounds.addRound(round);
                Season.roundId += 1;
                duration = System.currentTimeMillis() - start;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                int interval;
                //TODO take argument from main (make static, or send as parameter)
                interval = (int) ((6 * 1000) - duration);
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
        //System.out.println("RES: " + results);
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

    private boolean tableChanged(int roundId, Table roundTable) {
        //Iterator<Round> iterator = seasonRounds.iterator();
        /*List<Round> roundList = seasonRounds.getSeasonRounds();
        if (roundId == 1) {
            return false;
        }
        while (roundId > 1) {
            if (roundList.get(roundId - 1).getTable() == null) {
                roundId -= 1;
            } else if (roundList.get(- 1).getTable().equals(roundTable)) {
                return true;
            } else {
                return false;
            }
        }
        return false;*/
        System.out.println("THIS:");
        roundTable.printTable();
        return true;
    }
}
