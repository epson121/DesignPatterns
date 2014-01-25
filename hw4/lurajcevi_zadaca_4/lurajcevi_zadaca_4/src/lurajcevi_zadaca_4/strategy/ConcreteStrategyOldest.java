
package lurajcevi_zadaca_4.strategy;

import lurajcevi_zadaca_4.cache.DocumentSerialization;
import lurajcevi_zadaca_4.cache.LinkDocument;

/**
 *
 * @author luka
 */
public class ConcreteStrategyOldest implements Strategy {

    @Override
    public LinkDocument getForRemoval() {
        LinkDocument oldestDocument = null;
        long oldestTime = System.currentTimeMillis();
        for(LinkDocument ld : DocumentSerialization.record) {
            if (ld.getTimeOfStoring() < oldestTime) {
                oldestTime = ld.getTimeOfStoring();
                oldestDocument = ld;
            }
        }
        return oldestDocument;
    }

}
