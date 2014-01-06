
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
    
    public void print() {
        System.out.println("Url: " + urlLink);
        System.out.println("Total time: " + totalTimeElapsed);
        System.out.println("Number of manual reloads: " + manualReloadsCount);
        System.out.println("Number of automatic reloads: " + automatedReloadsCount);
        System.out.println("Number of content changes: " + contentChangedCount);
    }
    
    
}
