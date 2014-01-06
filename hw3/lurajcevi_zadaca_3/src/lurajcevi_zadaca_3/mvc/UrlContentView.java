
package lurajcevi_zadaca_3.mvc;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map;

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
    
    public void printUrlStatistics() {
        
    }
    
    public void printUrlHrefCount(int hrefCount) {
        System.out.println("Number of links is " + hrefCount);
    }
    
    public void printMenu() {
         System.out.println(menu);
    }
    
}
