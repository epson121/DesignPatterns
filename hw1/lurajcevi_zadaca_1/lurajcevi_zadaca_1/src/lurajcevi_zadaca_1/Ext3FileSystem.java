/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lurajcevi_zadaca_1;

/**
 * Subclass of FileSystem.
 * Represents specifics of the Ext3 file system implementation.
 * @author luka
 */
public class Ext3FileSystem extends FileSystem {

    private int MAX_FILENAME_LENGTH = 255;
    private String[] DISALLOWED_CHARACTERS = {"/", ";"};
    private String[] DISALLOWED_FILENAMES = {"/", "."};

    /**
     *
     * @param path
     */
    public Ext3FileSystem(String path) {
        super(path);
    }
    
    /**
     * If filename meets the requirements then calls super method
     * @param folderId - folder to add file to
     * @param filename - name of the new file
     */
    @Override
    public void addFile(int folderId, String filename) {
        if (checkConstraints(filename)) {
            super.addFile(folderId, filename);
        } else {
            System.out.println("Bad filename.");
        }
    }
    
    /**
     * Moving file/folder
     * @param fileId - id of file/folder to move
     * @param folderId - folder to move file to
     * @param name - name of new file (none for folder)
     */
    @Override
    public void moveFile(int fileId, int folderId, String name) {
        if (checkConstraints(name)) {
            super.moveFile(fileId, folderId, name);
        } else {
            System.out.println("Bad filename.");
        }
    }
    
    /**
     * Copying file/folder
     * @param fileId
     * @param folderId
     * @param name 
     */
    @Override
    public void copy(int fileId, int folderId, String name) {
        if (checkConstraints(name)) {
            super.copy(fileId, folderId, name);
        } else {
            System.out.println("Bad filename.");
        }
    }
    
    /**
     * Checks contraints for this type of file system
     * @param name
     * @return 
     */
    private boolean checkConstraints(String name) {
        return checkAllowedLength(name)
                && checkAllowedCharacters(name)
                && checkAllowedFilenames(name);
    }
    
    /**
     * Checks length
     * @param name
     * @return 
     */
    public boolean checkAllowedLength(String name) {
        if (name.length() > MAX_FILENAME_LENGTH) {
            return false;
        }
        return true;
    }
    
    /**
     * Checks allowed characters
     * @param name
     * @return 
     */
    public boolean checkAllowedCharacters(String name) {
        for (String s : DISALLOWED_CHARACTERS) {
            if (name.contains(s)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Checks allowed filenames
     * @param name
     * @return 
     */
    private boolean checkAllowedFilenames(String name) {
        for (String s : DISALLOWED_FILENAMES) {
            if (name.toUpperCase().equals(s)) {
                return false;
            }
        }
        return true;
    }
}
