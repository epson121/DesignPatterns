
package lurajcevi_zadaca_3.mvc;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map;
import lurajcevi_zadaca_3.model.UrlStatistics;

/**
 *
 * @author luka
 */
public class UrlContentView {
    
    private String menu = "\nCommands:\n" + 
                          "B -  ispis ukupnog broja poveznica\n" +
                          "I - ispis adresa poveznica s rednim brojem\n" +
                          "J n - prijelaz na poveznicu s rednim brojem n\n" +
                          "R - obnovi važeću web stranicu\n" +
                          "S - ispis statistike\n" +
                          "Q - prekid rada programa.\n";
    
    public void printUrlDetails(TreeMap<Integer, String> hrefs) {
        Iterator it = hrefs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println(pairs.getKey() + " " + pairs.getValue());
            it.remove();
        }
    }
    
    public void printUrlStatistics(UrlStatistics us) {
        us.print();
    }
    
    public void printUrlHrefCount(int hrefCount) {
        System.out.println("Number of links is " + hrefCount);
    }
    
    public void printMenu() {
         System.out.println(menu);
    }
    
    public void printUrlDetails(String urlLink, int totalTimeElapsed, int manualReloadsCount,
                                int automatedReloadsCount, int contentChangedCount) {

        System.out.println("Url: " + urlLink);
        System.out.println("Total time: " + totalTimeElapsed);
        System.out.println("Number of manual reloads: " + manualReloadsCount);
        System.out.println("Number of automatic reloads: " + automatedReloadsCount);
        System.out.println("Number of content changes: " + contentChangedCount);
    }
    
    
}
