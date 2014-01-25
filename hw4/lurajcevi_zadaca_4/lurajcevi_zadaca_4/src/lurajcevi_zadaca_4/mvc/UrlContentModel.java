
package lurajcevi_zadaca_4.mvc;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import lurajcevi_zadaca_4.Lurajcevi_zadaca_4;
import lurajcevi_zadaca_4.cache.LinkDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author luka
 */
public class UrlContentModel extends Observable {
    
    private Document doc;
    private Elements urlLinks;
    private LinkDocument linkDocument;
    // model data
    private String url;

    /**
     * Load a page from string URL, set data to zero, and grab the <a> tags
     * @param url 
     */
    public UrlContentModel(String url, int interval) {
        System.out.println(url);
        this.url = url;
        try {
            if (Lurajcevi_zadaca_4.CACHE.contains(url)) {
                linkDocument = Lurajcevi_zadaca_4.CACHE.acquire(this.url);
                linkDocument.updateNumOfUses();
                linkDocument.setLastTimeUsed(System.currentTimeMillis());
                File input = new File(Lurajcevi_zadaca_4.STORAGE + File.separator + linkDocument.getUrl().hashCode());
                doc = Jsoup.parse(input, "UTF-8", "");
                String msg = "READING FROM CACHE.";
                setChanged();
                notifyObservers(msg);
            } else {
                linkDocument = new LinkDocument(url, System.currentTimeMillis());
                linkDocument.updateNumOfUses();
                doc = Jsoup.connect(url).get();
                Lurajcevi_zadaca_4.CACHE.addToStorage(linkDocument, doc);
                String msg = "FETCHING FROM WEB.";
                setChanged();
                notifyObservers(msg);
            }
        } catch (IOException ex) {
                System.out.println("Unable to parse webpage.");
        }
        parseLinks();
    }
    
    /**
     * Parse links using jSoup library
     */
    private void parseLinks() {
        urlLinks = doc.select("a[href]");
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
     * Reload page. Update variables accordingly
     */
    public void reloadPage() {
        try {
            Document tempDoc = Jsoup.connect(url).get();
            Elements tempElements = tempDoc.select("a[href]");
            if (checkIfEqual(tempElements, urlLinks)) {
                // TODO send to view (oberver)
                System.out.println("No changes.");
            } else {
                System.out.println("Change has occured.");
                Lurajcevi_zadaca_4.CACHE.addToStorage(this.linkDocument, tempDoc);
            }
            this.urlLinks = tempElements;
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
    * GETTERS 
    *    AND
    *      SETTERS
    */
    
    
    public List<LinkDocument> getStorage() {
        return Lurajcevi_zadaca_4.CACHE.getStorage();
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
