
package lurajcevi_zadaca_3.chain_of_responsibility;

import java.util.HashMap;
import java.util.Map;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author luka
 *  Class that handles script tags (prints their number and themselves)
 */
public class ScriptTagHandler extends TagHandler{
    
    @Override
    public void handleRequest(Map<String, Elements> tag) {
        HashMap m = (HashMap) tag;
        if (m.get("script") != null) {
            Elements elements = (Elements) m.get("script");
            System.out.println("Handling <script> tag.");
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
