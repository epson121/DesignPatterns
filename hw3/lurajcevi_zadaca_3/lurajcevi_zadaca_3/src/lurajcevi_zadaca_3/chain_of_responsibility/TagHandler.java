
package lurajcevi_zadaca_3.chain_of_responsibility;

import java.util.Map;
import org.jsoup.select.Elements;

/**
 *
 * @author luka
 * Abstract handler (part of chain of responsibility design pattern)
 */
public abstract class TagHandler {
    TagHandler successor;
    
    /**
     * Set a successor for argument handling, if your class is not responsible for
     * it.
     * @param successor 
     */
    public void setSuccessor(TagHandler successor) {
            this.successor = successor;
    }
    
    /**
     * Abstract method, overridden. Every class implements its own way of handling
     * the arguments.
     * @param tag 
     */
    public abstract void handleRequest(Map<String, Elements> tag);
}
