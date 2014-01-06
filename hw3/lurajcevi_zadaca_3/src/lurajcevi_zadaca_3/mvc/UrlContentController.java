
package lurajcevi_zadaca_3.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import lurajcevi_zadaca_3.model.UrlStatistics;

/**
 *
 * @author luka
 */
public class UrlContentController {

    private int refreshInterval;
    private UrlContentModel model;
    private UrlContentView view;
    private long time;
    private List<Object> urlArchive;
    
    public UrlContentController(UrlContentModel model, UrlContentView view, int interval) {
        urlArchive = new ArrayList<>();
        this.refreshInterval = interval;
        this.model = model;
        this.view = view;
        view.printMenu();
        control();
    }
    
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            model.reloadPage();
            model.updateAutomaticReloadCount();
        }
    };
    
    private void control() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, refreshInterval * 1000, refreshInterval * 1000);
        time = System.currentTimeMillis();
        while(true) {
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            switch (s.trim().substring(0, 1)) {
                case "B": 
                    view.printUrlHrefCount(model.getLinksCount());
                    break;
                case "I": 
                    view.printUrlDetails(model.getUrlDetails());
                    break;
                case "J":
                    int t = (int) ((System.currentTimeMillis() - time) / 1000);
                    UrlStatistics stats 
                            = new UrlStatistics(model.getUrl(), t, 
                                model.getManualReloadCount(), model.getAutomaticReloadCount(),
                                model.getContentChangeCount());
                    model.saveToMemento(stats);
                    time = System.currentTimeMillis();
                    // start a new timer
                    break;
                case "R": 
                    model.updateManualReloadCount();
                    model.reloadPage();
                    break;
                case "S": break;
                case "Q": 
                    timer.cancel();
                    return;
                default: System.out.println("Wrong input.");
            }
        }
    }
    
    private List<Object> savedStates = new ArrayList<Object>();
    public void addMemento(Object m) {
        savedStates.add(m);
    }
    public Object getMemento(int index) {
        return savedStates.get(index);
    }


}
