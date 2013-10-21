/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lurajcevi_zadaca_1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import lurajcevi_zadaca_1.composite.AbstractFile;
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
        rootDir = new Directory(dir.getName(), 0, dir.getAbsolutePath());
        populateDirectory(path, rootDir, 0);
    }
    
    private void populateDirectory(String path, Directory parent, int parentId) {
        dir = new File(path);
        dirContent = dir.listFiles();
        for (File f : dirContent) {
            if (f.isDirectory()) {
                Directory newParent = new Directory(f.getName(), parentId, f.getAbsolutePath());
                parent.add(newParent);
                populateDirectory(f.getAbsolutePath(), newParent, newParent.getId());
            } else {
                FileX file = new FileX(f.getName(), parentId, f.getAbsolutePath());
                parent.add(file);
            }
        }
    }
    
    
    
    @Override
    public void listFolder(int id) {
        if (id == 0) {
            rootDir.ls();
        } else {
            AbstractFile af = rootDir.getItem(id);
            if  (af == null) {
                System.out.println("No such item.");
            } else  {
                af.ls();
            }
        }
    }
    
    

    @Override
    public void addFile(int folderId, String filename) {
        //todo add real file to system (save directories somewhere)
        AbstractFile d = rootDir.getItem(folderId);
        if (d.getType() == 0) {
            Directory directory = (Directory) d;
            directory.add(new FileX(filename, 
                                    directory.getId(), 
                                    d.getPath() + File.separator + filename));
        } else {
            System.out.println("Non existing directory.");
        }
        
    }

    @Override
    public void remove(int fileId) {
        AbstractFile itemToDelete = rootDir.getItem(fileId);
        if (itemToDelete == null) {
            System.out.println("No such file or folder.");
            return;
        }
        //System.out.println("OATH: " + itemToDelete.getPath());
        
        File fToDelete = new File(itemToDelete.getPath());
        if (fToDelete.isFile()) {
            System.out.println("TRUE");
            fToDelete.delete();
        } else {
            deleteRecursively(fToDelete.listFiles());
            fToDelete.delete();
        }
        rootDir.remove(fileId);
    }
    
    private void deleteRecursively(File[] files) {
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                //System.out.println("REMOVING: " + files[i].getAbsolutePath() );
                deleteRecursively(files[i].listFiles());
                files[i].delete();
            } else {
                //System.out.println("REMOVING: " + files[i].getAbsolutePath());
                files[i].delete();
            }
        }
    }

    @Override
    public void listFrom(int id) {
        if (id == 0) {
            listFolder(0);
        } else {
            //nothing
            //slat parenta u funkciju i svaki koji ima parent taj printaj ga
            // ili manji id
        }
    }
    
    @Override
    public void listParents(int id) {
        listParentsHelper(id, new ArrayList<String>());
    }
    
    /**
     * recursively lists parents
     * @param id - id of the current Abstract file
     * @param parents - list to store parents into
     */
    public void listParentsHelper(int id, ArrayList<String> parents) {
        if (id == 0) {
            parents.add("/" + rootDir.getName());
            printList(parents);
        } else {
            AbstractFile af = rootDir.getItem(id);
            if (af.getType() == 0) {
                parents.add("/" + af.getName());
            } else {
                parents.add(af.getName());
            }
            listParentsHelper(af.getParentId(), parents);
        }
    }
    
    
    private void printList(ArrayList<String> list) {
        for (int i = list.size() - 1; i >= 0; i--) {
            System.out.println(list.get(i));
        }
    }

    @Override
    public void move(int fileId, int folderId, String name) {
        Directory newDir;
        AbstractFile itemToMove = rootDir.getItem(fileId);
        if (itemToMove == null) {
            System.out.println("No such file or folder.");
            return;
        }
        //System.out.println("OATH: " + itemToDelete.getPath());
        
        File fToMove = new File(itemToMove.getPath());
        Directory parent = (Directory) rootDir.getItem(itemToMove.getParentId());
        parent.remove(itemToMove.getId());
        if (folderId == 0) {
            newDir = rootDir;
        } else {
            newDir = (Directory) rootDir.getItem(folderId);
        }
        String newPath = newDir.getPath() + File.separator + name;
        newDir.add(new FileX(name, 
                             newDir.getId(), 
                             newPath));
        fToMove.renameTo(new File(newPath));
    }
    
    
    @Override
    public void copy(int fileId, int folderId, String name) {
        // really move
        // check if the old file stays, or is deleted
        AbstractFile file = rootDir.getItem(fileId);
        Directory newDir = (Directory) rootDir.getItem(folderId);
        String oldPath = file.getPath();
        String newPath = newDir.getPath() + File.separator + name;
        newDir.add(new FileX(name, 
                             newDir.getId(), 
                             newPath));
        copyFiles(oldPath, newPath);
    }
    
    private void copyFiles(String path1, String path2) {
        InputStream inStream = null;
	OutputStream outStream = null;
 
    	try{
 
    	    File afile =new File(path1);
    	    File bfile =new File(path2);
 
    	    inStream = new FileInputStream(afile);
    	    outStream = new FileOutputStream(bfile);
 
    	    byte[] buffer = new byte[1024];
 
    	    int length;
    	    //copy the file content in bytes 
    	    while ((length = inStream.read(buffer)) > 0){
    	    	outStream.write(buffer, 0, length);
    	    }
 
    	    inStream.close();
    	    outStream.close();
 
    	    System.out.println("File is copied successful!");
 
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }
    
   
}
