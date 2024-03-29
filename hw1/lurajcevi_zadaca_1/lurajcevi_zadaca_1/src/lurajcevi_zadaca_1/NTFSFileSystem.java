
package lurajcevi_zadaca_1;

/**
 *
 * @author luka
 */
public class NTFSFileSystem extends FileSystem {    
    
    private int MAX_FILENAME_LENGTH = 255;
    private String[] DISALLOWED_CHARACTERS = {"?",  "\"",  "/",  "\\",
                                              "<",  ">",  "*", "|", ":" };
    private String[] DISALLOWED_FILENAMES = {"CON", "AUX", "COM1", "COM2", "COM3",
                                           "COM4", "LPT1", "LPT2", "LPT3", "PRN",
                                           "NUL"};
    
    /**
     *
     * @param path
     */
    public NTFSFileSystem(String path) {
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
    
    private boolean checkAllowedLength(String name) {
        if (name.length() > MAX_FILENAME_LENGTH) {
            return false;
        }
        return true;
    }

    private boolean checkAllowedCharacters(String name) {
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
