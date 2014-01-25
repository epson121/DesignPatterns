package lurajcevi_zadaca_4.cache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * implements methods for Log (opening, closing and writing to it)
 * @author Luka Rajcevic
 */
public class Log {
    
    private String fileName;
    File outputFile = null;
    FileOutputStream out = null;
    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    
    /**
     * Constructor for Log class
     * @param fileName - name of the log file
     */
    public Log(String fileName) {
        this.fileName = fileName;
        outputFile = new File (this.fileName);
    }
    
    /**
     * 
     * @return true if Log successfully opened, false otherwise
     */
    public boolean openLog(){
        try {
            out = new FileOutputStream(outputFile, true);
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * 
     * @param zapis - string to be written to log
     * @return - true if successfully written, false otherwise
     */
    public boolean writeToLog(String zapis){
        byte[] z = zapis.getBytes();
        try {
            out.write(z);
        } catch (IOException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    /**
     * @return true if successfully closed false otherwise
     * 
     */
    public boolean closeLog(){
       try {
           if (out != null)
               out.close();
       } 
       catch (IOException ex) {
           Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
       }
       return true;
   }
    
    /**
     * Write to log when item is added or removed to/from the storage.
     * @param document
     * @param action 
     */
    public void logOnItemChanged(LinkDocument document, String action) {
        Date dateAdded = new Date(document.getTimeOfStoring());
        Date dateLastUsed = new Date(document.getLastTimeUsed());
        String record = "Item " + action + ". TIME: \n" + df.format(new Date()) + "\n" +
                        "URL: " + document.getUrl() + "\n" +
                        "Time added: " + df.format(dateAdded) + "\n" +
                        "Last used: " + df.format(dateLastUsed) + "\n" +
                        "Total time in storage: " + document.getTotalTimeInStorage(System.currentTimeMillis()) + "\n" +
                        "Number of uses: " + document.getNumOfUses();
        openLog();
        writeToLog(record);
        closeLog();
    }

}
