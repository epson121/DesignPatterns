
package lurajcevi_zadaca_4.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lurajcevi_zadaca_4.Lurajcevi_zadaca_4;

/**
 * Implements static methods serialization handling (writing, reading, cleaning,
 * converting to text files)
 * @author Luka Rajcevic
 */
public class DocumentSerialization implements Serializable {
    
    /**
     * stores list of Record type object for serialization
     */
    public static List<LinkDocument> record = new ArrayList<>();

    /**
     * Serializes List<Record> to a file
     * @param filename  - writes serialized data to this file
     */
    public static void serializeToFile(String filename){
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        
        try {
            fileOutputStream = new FileOutputStream(filename);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(record);
        } catch (IOException ex) {
            System.out.println("Failed to serialize.");
            return;
        }
        finally{
            try {
                if (objectOutputStream != null)
                    objectOutputStream.close();
                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(DocumentSerialization.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Serialized.");
    }
    /**
     * Reads from serialized file into the List<Record> type variable
     * @param filename - reads from this file
     */
    public static void deserializeFromFile(String filename){
        List<LinkDocument> records = new ArrayList<>();
        FileInputStream fileInputStream = null;
        ObjectInputStream in = null;
        try {
            fileInputStream = new FileInputStream(filename);
            in = new ObjectInputStream(fileInputStream);
            records =  (List<LinkDocument>) in.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException ex) {
            System.out.println("Error while loading " + filename + ". No file, or the file empty.");
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
                if (in != null)
                    in.close();
            } catch (IOException ex) {
                Logger.getLogger(DocumentSerialization.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (records != null)
            DocumentSerialization.record = records;
        else{
            System.out.println("File is empty.");
        }
    }
    
    /**
     * Clears serialized file. It is empty afterwards.
     * @param filename - file to be cleaned
     */
    public static void clearSerializationFile(String filename){
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        
        try {
            fileOutputStream = new FileOutputStream(filename);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(null);
        } catch (IOException ex) {
            System.out.println("ERROR: Failed to clean.");
            return;
        }
        finally{
            try {
                if (fileOutputStream != null)
                    fileOutputStream.close();
                if (objectOutputStream != null)
                    objectOutputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(DocumentSerialization.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        DocumentSerialization.record.clear();
        System.out.println("Cleaned.");
    }
    
    /**
     * Clears the storage folder
     * @param storagePath 
     */
    public static void clearStorage(String storagePath) {
        File path = new File(storagePath);
        if (path.exists()) {
            for (File f : path.listFiles()) {
                f.delete();
            }
            record.clear();
        } else {
            path.mkdir();
        }
    }
    
    /**
     * Returns the storage size in KB
     * @return 
     */
    public static double getStorageSize() {
        double result = 0.0;
        for (LinkDocument d : DocumentSerialization.record) {
            File f = new File(Lurajcevi_zadaca_4.STORAGE + File.separator + d.getUrl().hashCode());
            if (!f.getName().equals("log.txt"))
                result += f.length();
        }
        return result/1024;
    }
    
    /**
     * returns the storage size in number of items
     * @return 
     */
    public static int getStorageCount() {
        return DocumentSerialization.record.size();
    }

}