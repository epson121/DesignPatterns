
package lurajcevi_zadaca_3.model;

/**
 *
 * @author luka
 */
public class UrlStatistics {
    private String urlLink;
    private int totalTimeElapsed;
    private int manualReloadsCount;
    private int automatedReloadsCount;
    private int contentChangedCount;

    public UrlStatistics(String urlLink, int totalTimeElapsed, 
                         int manualReloadsCount, int automatedReloadsCount, 
                         int contentChangedCount) {
        this.urlLink = urlLink;
        this.totalTimeElapsed = totalTimeElapsed;
        this.manualReloadsCount = manualReloadsCount;
        this.automatedReloadsCount = automatedReloadsCount;
        this.contentChangedCount = contentChangedCount;
    }
    
    
}
