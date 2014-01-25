
package lurajcevi_zadaca_4.mvc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import lurajcevi_zadaca_4.cache.LinkDocument;

/**
 *
 * @author luka
 */
public class UrlContentView implements Observer {
    /**
     * Menu String
     */
    private String menu = "\nCommands:\n" + 
                          "I - ispis adresa poveznica s rednim brojem\n" +
                          "J n - prijelaz na poveznicu s rednim brojem n\n" +
                          "S - ispis spremišta\n" +
                          "C - brisanje spremišta\n" +
                          "Q - prekid rada programa.\n";
    
    /**
     * prins url in format: id + name
     * @param hrefs 
     */
    public void printUrlDetails(TreeMap<Integer, String> hrefs) {
        Iterator it = hrefs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println(pairs.getKey() + " " + pairs.getValue());
            it.remove();
        }
    }
    
    /**
     * Prints menu
     */
    public void printMenu() {
         System.out.println(menu);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        System.out.println(arg);
    }
    
    /**
     * Prints data from storage
     * @param storage 
     */
    public void printStorage(List<LinkDocument> storage) {
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        for (LinkDocument ld : storage) {
            System.out.println("URL: " + ld.getUrl());
            Date dateAdded = new Date(ld.getTimeOfStoring());
            Date dateLastUsed = new Date(ld.getLastTimeUsed());
            System.out.println("Time added: " + df.format(dateAdded));
            System.out.println("Last used: " + df.format(dateLastUsed));
            System.out.println("Number of uses: " + ld.getNumOfUses());
        }
    }
    
}
