/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lurajcevi_zadaca_4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lurajcevi_zadaca_4.cache.DocumentCache;
import lurajcevi_zadaca_4.mvc.UrlContentController;
import lurajcevi_zadaca_4.mvc.UrlContentModel;
import lurajcevi_zadaca_4.mvc.UrlContentView;
import lurajcevi_zadaca_4.strategy.ConcreteStrategyMostUsed;
import lurajcevi_zadaca_4.strategy.ConcreteStrategyOldest;
import lurajcevi_zadaca_4.strategy.Strategy;

/**
 *
 * @author luka
 */
public class Lurajcevi_zadaca_4 {
    
    private static String argRegex = "([0-9]+)\\s([0-9]+)\\s(KB\\s)?(NS|NK)(\\sclean)?$";
    public static String STORAGE;
    public static boolean TO_CLEAN;
    public static String SERIALIZATION_FILE = "file.dat";
    public static DocumentCache CACHE;
    /**
     * @param args the command linie arguments
     */
    public static void main(String[] args) {
        Strategy strategy;
        Pattern p = Pattern.compile(argRegex);
        
        String res = "";
        for (int i = 2; i < args.length; i++) {
            res += args[i] + " ";
        }
        Matcher m = p.matcher(res.trim());
        if (m.matches()) {
            String url = args[0];
            Lurajcevi_zadaca_4.STORAGE = args[1];
            int interval = Integer.parseInt(m.group(1));
            int capacity = Integer.parseInt(m.group(2));
            String capacityType = m.group(3);
            String removalStrategyType = m.group(4);
            Lurajcevi_zadaca_4.TO_CLEAN = (m.group(5) == null) ? false : true;
            
            if (removalStrategyType.equals("NS")) {
                strategy = new ConcreteStrategyOldest();
            } else {
                strategy = new ConcreteStrategyMostUsed();
            }
            
            CACHE = new DocumentCache(strategy, capacity, capacityType);
            UrlContentModel model = new UrlContentModel(url, interval);
            UrlContentView view = new UrlContentView();
            model.addObserver(view);
            UrlContentController controller = new UrlContentController( model, 
                                                                        view,
                                                                        interval);
        } else {
            System.out.println("Wrong parameters.");
        }
        
        
        
    }
}
