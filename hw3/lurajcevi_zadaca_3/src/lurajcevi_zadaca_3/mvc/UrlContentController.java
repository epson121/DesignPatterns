
package lurajcevi_zadaca_3.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lurajcevi_zadaca_3.chain_of_responsibility.DivTagHandler;
import lurajcevi_zadaca_3.chain_of_responsibility.PTagHandler;
import lurajcevi_zadaca_3.chain_of_responsibility.ScriptTagHandler;
import lurajcevi_zadaca_3.chain_of_responsibility.SpanTagHandler;
import lurajcevi_zadaca_3.chain_of_responsibility.TagHandler;
import lurajcevi_zadaca_3.chain_of_responsibility.UnknownTagHandler;

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
    public UrlContentController(UrlContentModel model, UrlContentView view, int interval) {
        this.refreshInterval = interval;
        this.model = model;
        this.view = view;
        view.printMenu();
        // save first model to archive so it can be queryed
        addMemento(this.model.saveToMemento(this.model));
        
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
                model.reloadPage(false);
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
                    // print number of <a> tag hrefs
                    case "B": 
                        view.printUrlHrefCount(model.getLinksCount());
                        break;
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
                            // timer needs to be canceled (no reuse available)
                            timer.cancel();
                            timer = new Timer();
                            timer.scheduleAtFixedRate(getTask(), refreshInterval * 1000, 
                                                      refreshInterval * 1000);
                        }
                        break;
                    // reloads the page
                    case "R": 
                        model.reloadPage(true);
                        break;
                    // prints the statistics
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
                    // Starts the cahin of responsibility option (random tag is
                    // selected), and given in chain for handling.
                    case "C":
                        setUpChain().handleRequest(model.getRandomTag());
                        break;
                    // quits the application
                    case "Q": 
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
     * Checks if selected link is already archived, so it can continue to update
     * its old statistics (without the need to create new one)
     * @param next
     * @return 
     */
    public UrlContentModel archivedModel(String next) {
        for (int i = 0; i < savedStates.size(); i++) {
            UrlContentModel tempModel = model.restoreFromMemento(getMemento(i));
            if (tempModel.getUrl().equals(next)) {
                return tempModel;
            }
        }
        return null;
    }
    
    /**
     * Adding a memento (archive) to list
     * @param m 
     */
    private void addMemento(Object m) {
        savedStates.add(m);
    }
    
    /**
     * Get archive item from list
     * @param index
     * @return 
     */
    public Object getMemento(int index) {
        return savedStates.get(index);
    }
    
    /**
     * Set up chain of responsibility, i.e. the way random tag will be handled
     * @return 
     */
    public TagHandler setUpChain() {
        TagHandler divTagHandler = new DivTagHandler();
        TagHandler pTagHandler = new PTagHandler();
        TagHandler scriptTagHandler = new ScriptTagHandler();
        TagHandler spanTagHandler = new SpanTagHandler();
        TagHandler unknownTagHandler = new UnknownTagHandler();
        
        divTagHandler.setSuccessor(pTagHandler);
        pTagHandler.setSuccessor(scriptTagHandler);
        scriptTagHandler.setSuccessor(spanTagHandler);
        spanTagHandler.setSuccessor(unknownTagHandler);
        
        return divTagHandler;
    }


}
