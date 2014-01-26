
package lurajcevi_zadaca_4.cache;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lurajcevi_zadaca_4.Lurajcevi_zadaca_4;
import lurajcevi_zadaca_4.strategy.Strategy;
import org.jsoup.nodes.Document;

/**
 *
 * @author luka
 */
public class DocumentCache implements Cache {
    
    private String logFile = "log.txt";
    private Strategy strategy;
    private int capacity;
    private String capacityType;
    private Log log;
    
    /**
     * Constructor for cache
     * @param strategy - strategy for item removal
     * @param capacity - capacity of the cache
     * @param capacityType - type of capacity (numbers or size)
     */
    public DocumentCache(Strategy strategy, int capacity, String capacityType) {
        this.strategy = strategy;
        this.capacity = capacity;
        this.capacityType = capacityType;
        if (Lurajcevi_zadaca_4.TO_CLEAN) {
            DocumentSerialization.clearStorage(Lurajcevi_zadaca_4.STORAGE);
            System.out.println("STORAGE CLEARED.");
        } else {
            DocumentSerialization.deserializeFromFile(Lurajcevi_zadaca_4.STORAGE
                                                      + File.separator +
                                                      Lurajcevi_zadaca_4.SERIALIZATION_FILE);
        }
        log = new Log(Lurajcevi_zadaca_4.STORAGE + File.separator + logFile);
    }
    
    /**
     * Releases the item from the cache
     * @param document - item to remove
     */
    @Override
    public void release(LinkDocument document) {
        DocumentSerialization.record.remove(document);
        File fToRemove = new File(Lurajcevi_zadaca_4.STORAGE + File.separator + document.getUrl().hashCode());
        if (fToRemove.exists()) {
            fToRemove.delete();
        }
        log.logOnItemChanged(document, "removed");
    }
    
    /**
     * Fetches the item from storage
     * @param url
     * @return 
     */
    @Override
    public LinkDocument acquire(String url) {
        for (LinkDocument d : DocumentSerialization.record) {
            if (d.getUrl().equals(url)) {
                return d;
            }
        }
        return null;
    }
    
    /**
     * Checks if an item is in storage
     * @param url
     * @return 
     */
    public boolean contains(String url) {
        for (LinkDocument d : DocumentSerialization.record) {
            if (d.getUrl().equals(url)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get id of the element in the list (for updating)
     * @param url
     * @return 
     */
    public int getId(String url) {
        int id = 0;
        for (LinkDocument d : DocumentSerialization.record) {
            if (d.getUrl().equals(url)) {
                return id;
            }
            id++;
        }
        return -1;
    }
    
    /**
     * Adds item to the storage
     * @param linkDocument
     * @param doc 
     */
    public void addToStorage(LinkDocument linkDocument, Document doc) {
        // If storage is full, remove element
        if (this.capacityType != null) {
            double size = doc.html().getBytes().length/1024;
            while (DocumentSerialization.getStorageSize() + size > this.capacity) {
                if (DocumentSerialization.record.isEmpty()) {
                    System.out.println("FILE IS TOO BIG TO CACHE.");
                    return;
                }
                release(strategy.getForRemoval());
            }
        } else if (DocumentSerialization.record.size() >= this.capacity){
            release(strategy.getForRemoval());
        }
        
        // add the new one after the removal
        int id = getId(linkDocument.getUrl());
        if (id == -1) {
            DocumentSerialization.record.add(linkDocument);
        } else {
            DocumentSerialization.record.set(id, linkDocument);
        }
        try {
            BufferedWriter fileWriter = null;
            fileWriter = new BufferedWriter(
                            new OutputStreamWriter(
                                new FileOutputStream(Lurajcevi_zadaca_4.STORAGE + File.separator + linkDocument.getUrl().hashCode()), "UTF-8"));
            fileWriter.write(doc.html());
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(DocumentCache.class.getName()).log(Level.SEVERE, null, ex);
        }
        log.logOnItemChanged(linkDocument, "added");
    }
    
    /**
     * Gets the list of items in storage
     * @return 
     */
    public List<LinkDocument> getStorage() {
        return DocumentSerialization.record;
    }

}
