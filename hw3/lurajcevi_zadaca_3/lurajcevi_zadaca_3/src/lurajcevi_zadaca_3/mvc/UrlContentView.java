
package lurajcevi_zadaca_3.mvc;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map;

/**
 *
 * @author luka
 */
public class UrlContentView {
    /**
     * Menu String
     */
    private String menu = "\nCommands:\n" + 
                          "B -  ispis ukupnog broja poveznica\n" +
                          "I - ispis adresa poveznica s rednim brojem\n" +
                          "J n - prijelaz na poveznicu s rednim brojem n\n" +
                          "R - obnovi važeću web stranicu\n" +
                          "S - ispis statistike\n" +
                          "C - chain of responsibility\n" +
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
     * Prints number of a tags
     * @param hrefCount 
     */
    public void printUrlHrefCount(int hrefCount) {
        System.out.println("Number of links is " + hrefCount);
    }
    
    /**
     * Prints menu
     */
    public void printMenu() {
         System.out.println(menu);
    }
    
    /**
     * Prints url data
     * @param urlLink - url link
     * @param totalTimeElapsed - total time elapsed while querying this link
     * @param manualReloadsCount - number of manual reloads
     * @param automatedReloadsCount - number of automated (timed-out) reloads
     * @param contentChangedCount - number of times content has changed
     */
    public void printUrlDetails(String urlLink, int totalTimeElapsed, int manualReloadsCount,
                                int automatedReloadsCount, int contentChangedCount) {
        System.out.println("Url: " + urlLink);
        System.out.println("Total time: " + totalTimeElapsed);
        System.out.println("Number of manual reloads: " + manualReloadsCount);
        System.out.println("Number of automatic reloads: " + automatedReloadsCount);
        System.out.println("Number of content changes: " + contentChangedCount);
        System.out.println("-------------------------------------------");
    }
    
}
