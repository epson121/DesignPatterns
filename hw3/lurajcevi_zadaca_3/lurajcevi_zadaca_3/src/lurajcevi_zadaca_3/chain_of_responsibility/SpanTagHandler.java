
package lurajcevi_zadaca_3.chain_of_responsibility;

import java.util.HashMap;
import java.util.Map;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author luka
 * <span> tag handler
 *  Class that handles span tags (prints their number and themselves)
 */
public class SpanTagHandler extends TagHandler {
    
    @Override
    public void handleRequest(Map<String, Elements> tag) {
         HashMap m = (HashMap) tag;
        if (m.get("span") != null) {
            Elements elements = (Elements) m.get("span");
            System.out.println("Handling <span> tag.");
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
