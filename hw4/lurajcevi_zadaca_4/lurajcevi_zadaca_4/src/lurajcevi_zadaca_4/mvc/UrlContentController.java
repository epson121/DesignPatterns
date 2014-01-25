
package lurajcevi_zadaca_4.mvc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lurajcevi_zadaca_4.Lurajcevi_zadaca_4;
import lurajcevi_zadaca_4.cache.DocumentSerialization;

/**
 *
 * @author luka
 */
public class UrlContentController {
    
    private int refreshInterval;
    private long time;
    
    private UrlContentModel model;
    private UrlContentView view;
    private List<Object> savedStates = new ArrayList<Object>();
    private String jRegex = "J ([0-9]+)$";
    private Matcher m;
    private Pattern p;
    
    /**
     * CONSTRUCTOR
     * 
     * @param model - this controllers model
     * @param view - this controllers view
     * @param interval - interval of web page reloading
     */
    public UrlContentController(UrlContentModel model, UrlContentView view, int refreshInterval) {
        this.refreshInterval = refreshInterval;
        this.model = model;
        this.view = view;
        view.printMenu();
        // save first model to cache
        
        control();
    }
    
    /**
     * Creates an unique task (reusing of the tasks was not possible,
     * so this was one of the workarounds).
     * @return 
     */
    private TimerTask getTask() {
        return new TimerTask() {
            @Override
            public void run() {
                model.reloadPage();
                time = System.currentTimeMillis();
            }
        };
    }
    
    /**
     * Handles commands from user, pulls data from model and displays it with controller.
     * Also, updates data in model, reloads the page, etc.
     */
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
                    // print all <a> tags
                    case "I": 
                        view.printUrlDetails(model.getUrlDetails());
                        break;
                    // changes the url, and keeps working with the new one, while
                    // keeping the old one in archive for querying
                    case "J":
                        p = Pattern.compile(jRegex);
                        m = p.matcher(s);
                        if (m.matches()) {
                            String next = model.getElement(Integer.parseInt(m.group(1)));
                            model = new UrlContentModel(next, refreshInterval);
                            model.addObserver(view);
                            // timer needs to be canceled (no reuse available)
                            timer.cancel();
                            timer = new Timer();
                            timer.scheduleAtFixedRate(getTask(), refreshInterval * 1000, 
                                                      refreshInterval * 1000);
                        }
                        break;
                    case "S":
                        view.printStorage(model.getStorage());
                        break;
                    case "C":
                        DocumentSerialization.clearStorage(Lurajcevi_zadaca_4.STORAGE);
                        break;
                    case "Q":
                        DocumentSerialization.serializeToFile(Lurajcevi_zadaca_4.STORAGE + 
                                                                File.separator + 
                                                                Lurajcevi_zadaca_4.SERIALIZATION_FILE);
                        timer.cancel();
                        return;
                    default: System.out.println("Wrong input.");
                }
            } catch(Exception e) {
                // nothing
            }
        }
    }
    
    /**
     * Get archive item from list
     * @param index
     * @return 
     */
    public Object getMemento(int index) {
        return savedStates.get(index);
    }

}
