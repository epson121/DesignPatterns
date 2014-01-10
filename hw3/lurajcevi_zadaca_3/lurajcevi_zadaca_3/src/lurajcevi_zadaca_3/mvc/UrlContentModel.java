
package lurajcevi_zadaca_3.mvc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author luka
 */
public class UrlContentModel {
    
    private Document doc;
    private Elements urlLinks;
    
    // model data
    private String url;
    private int manualReloadCount;
    private int automaticReloadCount;
    private int contentChangeCount;
    private int totalTimeElapsed;
    private int interval;
    
    /**
     * Load a page from string URL, set data to zero, and grab the <a> tags
     * @param url 
     */
    public UrlContentModel(String url, int interval) {
        System.out.println(url);
        this.url = url;
        try {
            doc = Jsoup.connect(url).get();
            parseLinks();
            manualReloadCount = 0;
            automaticReloadCount = 0;
            contentChangeCount = 0;
            this.interval = interval;
            this.totalTimeElapsed = 0;
        } catch (IOException ex) {
            System.out.println("Unable to parse webpage.");
        }
    }
    
    /**
     * Parse links using jSoup library
     */
    private void parseLinks() {
        urlLinks = doc.select("a[href]");
    }
    
    /**
     * Select a random tag and parse the page for that tag
     * @return 
     */
    public Map<String, Elements> getRandomTag() {
        String[] randomTags = new String[] {"div", "p", "script", 
                                            "span", "h", "body"};
        Random r = new Random();
        int randomNumber = r.nextInt(randomTags.length);
        Map<String, Elements> result = new HashMap<>();
        result.put(randomTags[randomNumber], doc.select(randomTags[randomNumber]));
        return result;
    }
    
    /**
     * @return number of a tags in page 
     */
    public int getLinksCount() {
        return urlLinks.size();
    }
    
    /**
     * 
     * @param id - index of the element
     * @return  Element with the given index
     */
    public String getElement(int id) {
        return urlLinks.get(id).attr("href");
    }
    
    /**
     * Reload page. Update variables accordingly (time, number of reloads, etc)
     * @param isManual - was reload forced (not timed out one)
     */
    public void reloadPage(boolean isManual) {
        try {
            Document tempDoc = Jsoup.connect(url).get();
            Elements tempElements = tempDoc.select("a[href]");
            if (checkIfEqual(tempElements, urlLinks)) {
                System.out.println("No changes.");
            } else {
                System.out.println("Change has occured.");
                updateContentChangeCount();
            }
            this.urlLinks = tempElements;
            if (isManual) {
                updateManualReloadCount();
            } else {
                updateAutomaticReloadCount();
                updateTotalTimeElapsed(interval);
            }
        } catch (IOException ex) {
            Logger.getLogger(UrlContentModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Checks if web page has changed (i.e. if its a tags have changed)
     * @param newElements
     * @param oldElements
     * @return 
     */
    private boolean checkIfEqual(Elements newElements, Elements oldElements) {
        if (newElements.size() != oldElements.size()) {
            return false;
        } else {
            for (int i = 0; i < oldElements.size(); i++) {
                if (!oldElements.get(i).attr("href").equals(newElements.get(i).attr("href"))) {
                    return false;
                }
            }
            return true;
        }
    }
    
    /**
     *
     * @return url details (index + name) for printing 
     */
    public TreeMap<Integer, String> getUrlDetails() {
        TreeMap<Integer, String> urlDetails = new TreeMap<>();
        for (Element e : urlLinks) {
            urlDetails.put(urlLinks.indexOf(e), e.attr("href"));
        }
        return urlDetails;
    }
    
    /**
     * Save to archive
     * @param model
     * @return 
     */
    public Object saveToMemento(UrlContentModel model) {
        return new Memento(model);
    }
    
    /**
     * Restore from archive
     * @param m
     * @return 
     */
    public UrlContentModel restoreFromMemento(Object m) {
        UrlContentModel model = null;
        if (m instanceof Memento) {
            Memento memento = (Memento) m;
            model = memento.getSavedState();
        }
        return model;
    }

    /*
     * 
     * GETTERS
     *      AND
     *         SETTERS
     * 
     */
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getManualReloadCount() {
        return manualReloadCount;
    }

    public void setManualReloadCount(int manualReloadCount) {
        this.manualReloadCount = manualReloadCount;
    }

    public void updateManualReloadCount() {
        this.manualReloadCount += 1;
    }

    public int getAutomaticReloadCount() {
        return automaticReloadCount;
    }

    public void setAutomaticReloadCount(int automaticReloadCount) {
        this.automaticReloadCount = automaticReloadCount;
    }
    
    public void updateAutomaticReloadCount() {
        this.automaticReloadCount += 1;
    }

    public int getContentChangeCount() {
        return contentChangeCount;
    }

    public void setContentChangeCount(int contentChangeCount) {
        this.contentChangeCount = contentChangeCount;
    }
    
    public void updateContentChangeCount() {
        this.contentChangeCount += 1;
    }

    public int getTotalTimeElapsed() {
        return totalTimeElapsed;
    }

    public void setTotalTimeElapsed(int totalTimeElapsed) {
        this.totalTimeElapsed = totalTimeElapsed;
    }
    
    public void updateTotalTimeElapsed(int amount) {
        this.totalTimeElapsed += amount;
    }
    
    /**
     * Memento class
     */
    private static class Memento {

        private UrlContentModel model;
        
        /**
         * Constructor
         * @param stateToSave 
         */
        public Memento(UrlContentModel stateToSave) {
            model = stateToSave;
        }

        /**
         * Returns saved state
         * @return 
         */
        public UrlContentModel getSavedState() {
            return model;
        }
    }
    
}
