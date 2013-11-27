package lurajcevi_zadaca_2.model;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import lurajcevi_zadaca_2.command.Command;
import lurajcevi_zadaca_2.observer.Observer;
import lurajcevi_zadaca_2.observer.Subject;

/**
 *
 * @author luka
 */
public class Season extends Thread implements Subject, KeyListener {
    
    public static List<SportsClub> allClubs;
    private List<Observer> observerList = new ArrayList<>();
    private Random rand = new Random();
    private SeasonRounds seasonRounds = new SeasonRounds();
    private SeasonRounds controlSeasonRounds = new SeasonRounds();
    public static int roundId = 1;
    private int sleepInterval, controlInterval;
    public static int threshold;
    private boolean stopSeason = false;
    
    @Override
    public void keyTyped(KeyEvent e) {
        // do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 's') {
            stopSeason = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // do nothing
    }

    public Season(int sleepInterval, int controlInterval, int threshold) {
        this.sleepInterval = sleepInterval;
        this.controlInterval = controlInterval;
        Season.threshold = threshold;
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void run() {
        int controlIntervalCounter = 0;
        long start, duration = 0;
        while (getSportsClubList().size() > 2 && (stopSeason == false)) {
            try {
                String s = new String();
                BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                try {
                    if (bufferRead.ready()) {
                        s = bufferRead.readLine();
                    }
                } catch (IOException ex) {
                    System.out.println("Error occured.");
                }

                if (s.startsWith("s")) {
                    break;
                }
                // TODO does not print when club is disqualified
                start = System.currentTimeMillis();
                System.out.println("ROUND " + Season.roundId);
                // TODO tu kreirat tablicu!!
                // add initial table to control tables
                
                List<Result> roundResults = this.generateRoundResults(Season.roundId);
                Table t = new Table(getSportsClubList());
                if (controlSeasonRounds.getRoundCount() == 0) {
                    controlSeasonRounds.saveRound(
                            new RoundArchiveItem(0, t.createArchive(), null));
                }
                Round round = new Round(Season.roundId,
                        t,
                        roundResults);
                round.printResults();
                round.printTable();
                seasonRounds.saveRound(round.getArchivedRound());
                //notifyObserver();
                controlIntervalCounter += 1;
                if (controlIntervalCounter == controlInterval) {
                    controlIntervalCounter = 0;
                    controlSeasonRounds.saveRound(new RoundArchiveItem(roundId, t.createArchive(), roundResults));
                    updateClubStatus();
                }
                Season.roundId += 1;
                duration = System.currentTimeMillis() - start;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                int interval;
                interval = (int) ((sleepInterval * 1000) - duration);
                System.out.println("Spavanje: " + interval);
                sleep(interval);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }
        seasonRounds.getSeasonRound(seasonRounds.getRoundCount() - 1).printTable();
        printMenu();
        String input = "";
        String inputId = "";
        Scanner sc = new Scanner(System.in);

        while (!input.equals("q")) {
            System.out.print(">> ");
            input = sc.nextLine();
            if ("q".equals(input)) {
                break;
            }
            switch (Integer.parseInt(input)) {
                case 1:
                    printAllArchivedTables();
                    break;
                case 2:
                    System.out.println("Archive id: ");
                    System.out.print(">> ");
                    inputId = sc.nextLine();
                    printSpecificTable(Integer.parseInt(inputId));
                    break;
                case 3:
                    System.out.println("Archive id: ");
                    System.out.print(">> ");
                    inputId = sc.nextLine();
                    printResultsOfSpecificTable(Integer.parseInt(inputId));
                    break;
                case 4:
                    System.out.println("Club id: ");
                    System.out.print(">> ");
                    inputId = sc.nextLine();
                    printResultsOfSpecificClub(Integer.parseInt(inputId));
                    break;
                case 5:
                    printMenu();
                    break;
                default:
                    System.out.println("Wrong number.");
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
         for (Observer o : observerList) {
            SportsClub s = (SportsClub) o;
            int points = s.getPoints();
            int rounds = s.getRoundsPlayedList().size();
            double efficiency = (double) points / rounds;
            // notify only on change
            if (efficiency != s.getEfficiency() && rounds != 0) {
                o.updateEfficiency(efficiency);
            }
        }
    }

    private ArrayList<Observer> getCompetitors() {
        ArrayList<Observer> result = new ArrayList<>();
        for (Observer o : observerList) {
            result.add(o);
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

    private void updateClubStatus() {
        int length = controlSeasonRounds.getRoundCount();
        RoundArchiveItem newControlTable;
        List<Command> tableChanges = null;
        if (length >= 2) {
            System.out.println("LENGTH: " + length);
            newControlTable = controlSeasonRounds.getSeasonRound(length - 2);
            // newControlTable.printTable();
            tableChanges = newControlTable.tableDifference(getSportsClubList());
        }
        for (Command c : tableChanges) {
            c.execute();
        }
    }

    public void printAllArchivedTables() {
        Iterator<RoundArchiveItem> i = seasonRounds.iterator();
        while (i.hasNext()) {
            RoundArchiveItem r = i.next();
            if (r.getTableList() != null) {
                r.printTable();
            }
        }
    }

    public void printSpecificTable(int id) {
        Iterator<RoundArchiveItem> i = seasonRounds.iterator();
        while (i.hasNext()) {
            RoundArchiveItem r = i.next();
            if (r.getRoundId() == id) {
                r.printTable();
                return;
            }
        }
    }

    public void printResultsOfSpecificTable(int id) {
        Iterator<RoundArchiveItem> i = seasonRounds.iterator();
        while (i.hasNext()) {
            RoundArchiveItem r = i.next();
            if (r.getRoundId() == id) {
                r.printResults();
                return;
            }
        }
    }
    
    public void printResultsOfSpecificClub(int id) {
        SportsClub selectedSportsClub = null;
        //get reference to sports club
        for (SportsClub sc : Season.allClubs) {
            if (sc.getSportsClubId() == id) {
                selectedSportsClub = sc;
                break;
            }
        }
        // iterate through archive and print results from
        // specific club
        Iterator<RoundArchiveItem> i = seasonRounds.iterator();
        while (i.hasNext()) {
            RoundArchiveItem r = i.next();
            System.out.println("ROUND: " + r.getRoundId());
            if (selectedSportsClub.getRoundsPlayedList().contains(r.getRoundId())) {
                r.printResultFromSpecificClub(id);
            }
        }
    }
    
    public void printMenu() {
        System.out.println("SEASON IS OVER!\n PLEASE CHOOSE ONE OF THE FOLLOWING");
        System.out.println("\n1.Print all archived tables");
        System.out.println("\n2.Print a specific table (by id)");
        System.out.println("\n3.Print results connected to the specific table");
        System.out.println("\n4.Print results of a specific sports club");
        System.out.println("\nq to exit");
        System.out.println("\nChoose wisely!");
    }
}
