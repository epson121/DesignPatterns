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
    
    private Table table;
    private List<Observer> observerList;
    private List<SportsClub> sportsClubList;
    private Random rand = new Random();
    private List<Round> rounds;

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
                roundTable = new Table(sportsClubList);
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
        List<SportsClub> sc = new ArrayList<>(this.sportsClubList);
        List<Result> results = null;
        while (sc.size() > 1) {
            SportsClub first = sc.get(rand.nextInt(sc.size()));
            sc.remove(first);
            SportsClub second = sc.get(rand.nextInt(sc.size()));
            sc.remove(second);
            Result r = new Result(first, second);
        }
        if (!sc.isEmpty())
            results.add(new SingleClubResult(sc.get(0)));
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
}
