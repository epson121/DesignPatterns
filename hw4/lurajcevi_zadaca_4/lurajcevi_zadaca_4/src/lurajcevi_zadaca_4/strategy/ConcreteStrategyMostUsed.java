
package lurajcevi_zadaca_4.strategy;

import lurajcevi_zadaca_4.cache.DocumentSerialization;
import lurajcevi_zadaca_4.cache.LinkDocument;

/**
 *
 * @author luka
 */
public class ConcreteStrategyMostUsed implements Strategy {

    @Override
    public LinkDocument getForRemoval() {
        LinkDocument mostUsedDocument = null;
        int mostUsedCount = -1;
        for(LinkDocument ld : DocumentSerialization.record) {
            if (ld.getNumOfUses() > mostUsedCount || mostUsedCount == -1) {
                mostUsedCount = ld.getNumOfUses();
                mostUsedDocument = ld;
            }
        }
        return mostUsedDocument;
    }

}
