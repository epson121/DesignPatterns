package lurajcevi_zadaca_2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lurajcevi_zadaca_2.observer.Observer;
import lurajcevi_zadaca_2.observer.Subject;
import lurajcevi_zadaca_2.state.Competitor;

/**
 *
 * @author luka
 */
public class Season extends Thread implements Subject {
    
    private List<Observer> observerList = new ArrayList<>();
    private Random rand = new Random();
    
    // TODO dati argument za dretvu
    public Season() {}
    
    @Override
    public synchronized void start() {
        super.start(); 
    }

    @Override
    public void run() {
        List<Result> roundResults;
        Table roundTable;
        long start, duration = 0;
        while (true) {
            try {
                start = System.currentTimeMillis();
                roundResults = this.generateRoundResults();
                for (Result r : roundResults) {
                    if (r.getSecondClub() == null) {
                        SingleClubResult scr = (SingleClubResult) r;
                        System.out.println(scr.getSc().getSportsClubName() + " paused this round.");
                    } else {
                        System.out.println(r.getFirstClub().getSportsClubName()
                                           + ":" + r.getSecondClub().getSportsClubName()
                                           + " " + r.getFirstClubScore() + " : " 
                                           + r.getSecondClubScore());
                    }
                }
                roundTable = new Table(getSportsClubList());
                roundTable.generateTable();
                roundTable.printTable();
                duration = System.currentTimeMillis() - start;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                int interval;
                //TODO take argument from main (make static, or send as parameter)
                interval = (int) ((10 * 1000) - duration);
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

    public List<Result> generateRoundResults() {
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
            Result r = new Result(first, second);
            results.add(r);
        }
        if (!sc.isEmpty())
            results.add(new SingleClubResult((SportsClub) sc.get(0)));
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
}
