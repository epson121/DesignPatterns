/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lurajcevi_zadaca_1;

import java.io.File;
import lurajcevi_zadaca_1.composite.Directory;
import lurajcevi_zadaca_1.composite.FileX;

/**
 *
 * @author luka
 */
public class NTFSFileSystem extends FileSystem {

    private Directory rootDir;
    private File dir;
    private File[] dirContent;
    
    public NTFSFileSystem(String path) {
        dir = new File(path);
        rootDir = new Directory(dir.getName());
        populateDirectory(path, rootDir);
        //rootDir.ls();
    }
    
    private void populateDirectory(String path, Directory parent) {
        dir = new File(path);
        dirContent = dir.listFiles();
        for (File f : dirContent) {
            if (f.isDirectory()) {
                Directory newParent = new Directory(f.getName());
                parent.add(newParent);
                populateDirectory(f.getAbsolutePath(), newParent);
            } else {
                FileX file = new FileX(f.getName());
                parent.add(file);
            }
        }
    }
    
    @Override
    public void getFolder(int folderId) {
        rootDir.printDir(folderId);
    }

    @Override
    public void addFile(int folderId, String filename) {
        Directory d = rootDir.getDir(folderId);
        d.add(new FileX(filename));
        rootDir.ls();
    }
    
    
    
   
}
