
package lurajcevi_zadaca_3.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author luka
 */
public class UrlContentController {

    private int refreshInterval;
    private UrlContentModel model;
    private UrlContentView view;
    private long time;
    private List<Object> savedStates = new ArrayList<Object>();
    private String jRegex = "J ([0-9]+)$";
    private Matcher m;
    private Pattern p;
    private List<UrlContentModel> allModels = new ArrayList<>();
    
    public UrlContentController(UrlContentModel model, UrlContentView view, int interval) {
        this.refreshInterval = interval;
        this.model = model;
        this.view = view;
        view.printMenu();
        addMemento(this.model.saveToMemento(this.model));
        control();
    }
    
    private TimerTask getTask() {
        return new TimerTask() {

            @Override
            public void run() {
                model.reloadPage(false);
                time = System.currentTimeMillis();
            }
        };
    }
    
    private void control() {
        Timer timer = new Timer();
        TimerTask timerTask = getTask();
        timer.scheduleAtFixedRate(timerTask, refreshInterval * 1000, refreshInterval * 1000);
        time = System.currentTimeMillis();
        while(true) {
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            try{
                switch (s.trim().substring(0, 1)) {
                    case "B": 
                        view.printUrlHrefCount(model.getLinksCount());
                        break;
                    case "I": 
                        view.printUrlDetails(model.getUrlDetails());
                        break;
                    case "J":
                        p = Pattern.compile(jRegex);
                        m = p.matcher(s);
                        if (m.matches()) {
                            int t = (int) ((System.currentTimeMillis() - time) / 1000);
                            model.updateTotalTimeElapsed(t);
                            String next = model.getElement(Integer.parseInt(m.group(1)));
                            if (archivedModel(next) == null) {
                                this.model = new UrlContentModel(next,
                                                             refreshInterval);
                                addMemento(model.saveToMemento(model));   
                            } else {
                                this.model = archivedModel(next);
                            }
                            
                            
                            time = System.currentTimeMillis();
                            // start a new timer
                            timer.cancel();
                            timer = new Timer();
                            timer.scheduleAtFixedRate(getTask(), refreshInterval * 1000, 
                                                      refreshInterval * 1000);
                        }
                        break;
                    case "R": 
                        model.reloadPage(true);
                        break;
                    case "S": 
                        for (int i = 0; i < savedStates.size(); i++) {
                            UrlContentModel tempModel = model.restoreFromMemento(getMemento(i));
                            view.printUrlDetails(tempModel.getUrl(), 
                                                 tempModel.getTotalTimeElapsed(),
                                                 tempModel.getManualReloadCount(), 
                                                 tempModel.getAutomaticReloadCount(),
                                                 tempModel.getContentChangeCount());
                        }
                        break;
                    case "Q": 
                        timer.cancel();
                        return;
                    default: System.out.println("Wrong input.");
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public UrlContentModel archivedModel(String next) {
        for (int i = 0; i < savedStates.size(); i++) {
            UrlContentModel tempModel = model.restoreFromMemento(getMemento(i));
            if (tempModel.getUrl().equals(next)) {
                return tempModel;
            }
        }
        return null;
    }
    
    private void addMemento(Object m) {
        savedStates.add(m);
    }
    public Object getMemento(int index) {
        return savedStates.get(index);
    }


}
