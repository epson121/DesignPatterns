/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lurajcevi_zadaca_1;

/**
 *
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

    @Override
    public void addFile(int folderId, String filename) {
        if (checkConstraints(filename)) {
            super.addFile(folderId, filename);
        } else {
            System.out.println("Bad filename.");
        }
    }

    @Override
    public void moveFile(int fileId, int folderId, String name) {
        if (checkConstraints(name)) {
            super.moveFile(fileId, folderId, name);
        } else {
            System.out.println("Bad filename.");
        }
    }

    @Override
    public void copy(int fileId, int folderId, String name) {
        if (checkConstraints(name)) {
            super.copy(fileId, folderId, name);
        } else {
            System.out.println("Bad filename.");
        }
    }
    
    private boolean checkConstraints(String name) {
        return checkAllowedLength(name)
                && checkAllowedCharacters(name)
                && checkAllowedFilenames(name);
    }

    public boolean checkAllowedLength(String name) {
        if (name.length() > MAX_FILENAME_LENGTH) {
            return false;
        }
        return true;
    }

    public boolean checkAllowedCharacters(String name) {
        for (String s : DISALLOWED_CHARACTERS) {
            if (name.contains(s)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean checkAllowedFilenames(String name) {
        for (String s : DISALLOWED_FILENAMES) {
            if (name.toUpperCase().equals(s)) {
                return false;
            }
        }
        return true;
    }
}
