
package lurajcevi_zadaca_3.chain_of_responsibility;

import java.util.HashMap;
import java.util.Map;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author luka
 * <p> tag handler
 *  Class that handles p tags (prints their number and themselves)
 */
public class PTagHandler extends TagHandler{
    
    @Override
    public void handleRequest(Map<String, Elements> tag) {
         HashMap m = (HashMap) tag;
        if (m.get("p") != null) {
            Elements elements = (Elements) m.get("p");
            System.out.println("Handling <p> tag.");
            System.out.println("Number of tags: " + elements.size());
            for (Element elem : elements) {
                System.out.println(elem);
            }
        } else {
            if (successor != null) {
                successor.handleRequest(tag);
            }
        }
    }
    
}
