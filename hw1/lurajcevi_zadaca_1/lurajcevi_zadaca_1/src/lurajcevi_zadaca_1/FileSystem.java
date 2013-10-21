/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lurajcevi_zadaca_1;

/**
 *
 * @author luka
 */
public abstract class FileSystem {
    public static StringBuffer g_indent = new StringBuffer();
    public static int componentId = 0;
    
    
    
    public abstract void addFile(int folderId, String filename);
    public abstract void remove(int fileId);
    public abstract void move(int fileId, int folderId, String name);
    public abstract void copy(int fileId, int folderId, String name);
    
    public abstract void listFrom(int id);
    public abstract void listParents(int id);
    public abstract void listFolder(int folderId);
}
