
package lurajcevi_zadaca_3.mvc;

import java.io.IOException;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import lurajcevi_zadaca_3.model.UrlStatistics;
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
    private UrlStatistics urlStatistics;
    
    // model data
    private String url;
    private int manualReloadCount;
    private int automaticReloadCount;
    private int contentChangeCount;
    /**
     * Load a page from string URL
     * @param url 
     */
    public UrlContentModel(String url) {
        System.out.println(url);
        this.url = url;
        try {
            doc = Jsoup.connect(url).get();
            parseLinks();
            manualReloadCount = 0;
            automaticReloadCount = 0;
            contentChangeCount = 0;
        } catch (IOException ex) {
            Logger.getLogger(UrlContentModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void parseLinks() {
        urlLinks = doc.select("a[href]");
    }
    
    public int getLinksCount() {
        return urlLinks.size();
    }
    
    public Element getElement(int id) {
        return urlLinks.get(id);
    }
    
    public void reloadPage() {
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
            
        } catch (IOException ex) {
            Logger.getLogger(UrlContentModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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
    
    public TreeMap<Integer, String> getUrlDetails() {
        TreeMap<Integer, String> urlDetails = new TreeMap<>();
        for (Element e : urlLinks) {
            urlDetails.put(urlLinks.indexOf(e), e.attr("href"));
        }
        return urlDetails;
    }
    
    public Object saveToMemento(UrlStatistics statistics) {
        System.out.println("Originator: Saving to Memento.");
        nullifyValues();
        return new Memento(statistics);
    }
    
    private void nullifyValues() {
        setAutomaticReloadCount(0);
        setContentChangeCount(0);
        setManualReloadCount(0);
    }
    
    public void restoreFromMemento(Object m) {
        if (m instanceof Memento) {
            Memento memento = (Memento) m;
            urlStatistics = memento.getSavedState();
            System.out.println("Originator: State after restoring from Memento: " + urlStatistics);
        }
    }
    
    private static class Memento {

        private UrlStatistics statistics;
            public Memento(UrlStatistics stateToSave) {
            statistics = stateToSave;
        }
            
        public UrlStatistics getSavedState() {
            return statistics;
        }
    }

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
    
}
