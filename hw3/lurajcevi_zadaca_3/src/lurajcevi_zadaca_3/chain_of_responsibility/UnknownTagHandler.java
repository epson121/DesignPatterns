
package lurajcevi_zadaca_3.chain_of_responsibility;

import java.util.Map;
import org.jsoup.select.Elements;

/**
 *
 * @author luka
 *  Class that handles all tags that do not have their designated handler
 */
public class UnknownTagHandler extends TagHandler{

    @Override
    public void handleRequest(Map<String, Elements> tag) {
        for (String s : tag.keySet())
            System.out.println("Unfortunately, tag <" + s + "> does not have a handler..");
    }
    
}
