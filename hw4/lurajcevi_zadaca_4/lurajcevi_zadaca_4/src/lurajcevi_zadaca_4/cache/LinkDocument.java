
package lurajcevi_zadaca_4.cache;

import java.io.Serializable;

/**
 * Represents the model of the web site
 * User for serialization
 * @author luka
 */
public class LinkDocument implements Serializable {
    
    private String url;
    private long timeOfStoring;
    private int numOfUses;
    private long lastTimeUsed;
    
    /**
     * Constructor
     * @param url - web site url
     * @param timeOfStoring - time of adding this web site to storage
     */
    public LinkDocument(String url, long timeOfStoring) {
            this.url = url;
            this.timeOfStoring = timeOfStoring;
            this.lastTimeUsed = timeOfStoring;
            this.numOfUses = 0;
    }
    
    /**
     * Returns the total time of an item being in storage
     * @param timeOfRemoval - time of removal from storage
     * @return  - total time
     */
    public int getTotalTimeInStorage(long timeOfRemoval) {
        long time = timeOfRemoval - timeOfStoring;
        return (int) time / 1000;
    }
    
    /**
     * GETTERS 
     *    AND
     *      SETTERS
     */
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTimeOfStoring() {
        return timeOfStoring;
    }

    public void setTimeOfStoring(long timeOfStoring) {
        this.timeOfStoring = timeOfStoring;
    }

    public int getNumOfUses() {
        return numOfUses;
    }
    
    public void updateNumOfUses() {
        this.numOfUses += 1;
    }

    public long getLastTimeUsed() {
        return lastTimeUsed;
    }

    public void setLastTimeUsed(long lastTimeUsed) {
        this.lastTimeUsed = lastTimeUsed;
    }
    
}
