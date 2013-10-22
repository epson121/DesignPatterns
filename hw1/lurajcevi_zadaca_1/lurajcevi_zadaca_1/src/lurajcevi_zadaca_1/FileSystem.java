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
public abstract class FileSystem {

    public static StringBuffer g_indent = new StringBuffer();
    public static int componentId = 0;
    public Directory rootDir;
    private File dir;
    private File[] dirContent;

    public FileSystem(String rootPath) {
        dir = new File(rootPath);
        rootDir = new Directory(dir.getName(), 0, dir.getAbsolutePath());
        populateDirectory(rootPath, rootDir, 0);
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
                FileX file = new FileX(f.getName(), parentId, f.getAbsolutePath(), f.length());
                parent.add(file);
            }
        }
    }

    public void addFile(int folderId, String filename) {
        AbstractFile d = rootDir.getItem(folderId);
        if (d.getType() == 0) {
            Directory directory = (Directory) d;
            directory.add(new FileX(filename,
                    directory.getId(),
                    d.getPath() + File.separator + filename, 0));
        } else {
            System.out.println("Non existing directory.");
        }
    }

    public void addFolder(int folderId, String foldername) {
        AbstractFile d = rootDir.getItem(folderId);
        if (d.getType() == 0) {
            Directory directory = (Directory) d;
            directory.add(new Directory(foldername,
                    d.getId(),
                    d.getPath() + File.separator + foldername));
        } else {
            System.out.println("Non existing directory.");
        }
    }

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
        if (files == null) {
            return;
        }
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
    
    public void moveRecursively(File[] f, String path) {
        for (File file : f) {
            if (file.isDirectory()) {
                File dir = new File(path + File.separator + file.getName());
                dir.mkdirs();
                moveRecursively(file.listFiles(), dir.getAbsolutePath());
            } else {
                file.renameTo(new File(path + File.separator + file.getName()));
            }
        }
    }
    
    public void moveFolder(int f1Id, int f2Id) {
        Directory newDir;
        AbstractFile itemToMove = rootDir.getItem(f1Id);
        
        if (itemToMove == null) {
            System.out.println("No such file or folder.");
            return;
        }
        //System.out.println("OATH: " + itemToDelete.getPath());
        File fToMove = new File(itemToMove.getPath());
        
        Directory parent = (Directory) rootDir.getItem(itemToMove.getParentId());
        /*System.out.println("PARENT1: " + parent);
        System.out.println("PARENT ID: " + parent.getId());*/
        if (f2Id == 0) {
            newDir = rootDir;
        } else {
            newDir = (Directory) rootDir.getItem(f2Id);
        }
        String newPath = newDir.getPath() + File.separator + itemToMove.getName();
        /*Directory newest = new Directory(name, newDir.getId(), newPath);
        
        for (Object o : itemToMove.getChildren()) {
            newest.add(o);
        }*/
        newDir.add(itemToMove);
        /*System.out.println("TO MOVE: " + itemToMove);
        System.out.println("TO MOVE ID: " + itemToMove.getId());
        System.out.println("PARENT: " + parent);
        System.out.println("PARENT ID: " + parent.getId());*/
        /*ArrayList ch = parent.getChildren();
        for (int i = 0; i < ch.size(); i++) {
            if (ch.get(i) == itemToMove) {
                parent.getChildren().remove(0);
            }
        }*/
        parent.getChildren().remove(itemToMove);
        moveRecursively(fToMove.listFiles(), newPath);
        //fToMove.renameTo(new File(newPath));
        deleteRecursively(fToMove.listFiles());
        fToMove.delete();
    }
    
    public void moveFile(int fileId, int folderId, String name) {
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
                newPath, fToMove.length()));
        fToMove.renameTo(new File(newPath));
    }

    public void copy(int fileId, int folderId, String name) {
        AbstractFile file = rootDir.getItem(fileId);
        File fToCopy = new File(file.getPath());
        Directory newDir = (Directory) rootDir.getItem(folderId);
        String oldPath = file.getPath();
        String newPath = newDir.getPath() + File.separator + name;
        newDir.add(new FileX(name,
                newDir.getId(),
                newPath, fToCopy.length()));
        copyFiles(oldPath, newPath);
    }

    private void copyFiles(String path1, String path2) {
        InputStream inStream = null;
        OutputStream outStream = null;

        try {

            File afile = new File(path1);
            File bfile = new File(path2);

            inStream = new FileInputStream(afile);
            outStream = new FileOutputStream(bfile);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes 
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }

            inStream.close();
            outStream.close();

            System.out.println("File is copied successful!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listFrom(int id) {
        if (id == 0) {
            listFolder(0);
        } else {
            //nothing
            //slat parenta u funkciju i svaki koji ima parent taj printaj ga
            // ili manji id
        }
    }

    public void listParents(int id) {
        listParentsHelper(id, new ArrayList<String>());
    }

    public void listFolder(int id) {
        if (id == 0) {
            rootDir.ls();
        } else {
            AbstractFile af = rootDir.getItem(id);
            if (af == null) {
                System.out.println("No such item.");
            } else {
                af.ls();
            }
        }
    }

    /**
     * recursively lists parents
     *
     * @param id - id of the current Abstract file
     * @param parents - list to store parents into
     */
    private void listParentsHelper(int id, ArrayList<String> parents) {
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
}
