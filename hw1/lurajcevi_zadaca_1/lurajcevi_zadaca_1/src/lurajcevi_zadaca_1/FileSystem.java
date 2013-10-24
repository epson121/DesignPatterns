package lurajcevi_zadaca_1;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    /**
     * Creates new file system on given path
     *
     * @param rootPath
     */
    public FileSystem(String rootPath) {
        dir = new File(rootPath);
        rootDir = new Directory(dir.getName(),
                0,
                dir.getAbsolutePath(),
                getPermissions(dir));
        rootDir = new Directory(dir.getName(), 
                                0, 
                                dir.getAbsolutePath(), 
                                getPermissions(dir));
        populateDirectory(rootPath, rootDir, 0);
    }

    /**
     * Populates the directory with files and folders in given path
     *
     * @param path
     * @param parent
     * @param parentId
     */
    private void populateDirectory(String path, Directory parent, int parentId) {
        dir = new File(path);
        dirContent = dir.listFiles();
        for (File f : dirContent) {
            if (f.isDirectory()) {
                Directory newParent = new Directory(f.getName(),
                        parentId,
                        f.getAbsolutePath(),
                        getPermissions(f));
                parent.add(newParent);
                populateDirectory(f.getAbsolutePath(), newParent, newParent.getId());
            } else {
                FileX file = new FileX(f.getName(),
                        parentId,
                        f.getAbsolutePath(),
                        f.length(),
                        getPermissions(f));
                Directory newParent = new Directory(f.getName(), 
                                                    parentId, 
                                                    f.getAbsolutePath(),
                                                    getPermissions(f));
                parent.add(newParent);
                populateDirectory(f.getAbsolutePath(), newParent, newParent.getId());
            } else {
                FileX file = new FileX(f.getName(), 
                                       parentId, 
                                       f.getAbsolutePath(), 
                                       f.length(),
                                       getPermissions(f));
                parent.add(file);
            }
        }
    }

    /**
     * Adds file to given folder
     *
     * @param folderId - id of the folder
     * @param filename - name to be given to the file
     */
    public void addFile(int folderId, String filename) {
        AbstractFile d = rootDir.getItem(folderId);
        if (d.getType() == 0) {
            Directory directory = (Directory) d;
            directory.add(new FileX(filename,
                    directory.getId(),
                    d.getPath() + File.separator + filename, 0, "rwx"));
        } else {
            System.out.println("Non existing directory.");
        }
    }

    /**
     * Adds the folder to the given folder
     *
     * @param folderId - id of the folder
     * @param foldername - name of the new folder
     */
    public void addFolder(int folderId, String foldername) {
        AbstractFile d = rootDir.getItem(folderId);
        if (d.getType() == 0) {
            Directory directory = (Directory) d;
            directory.add(new Directory(foldername,
                    d.getId(),
                    d.getPath() + File.separator + foldername, "rwx"));
        } else {
            System.out.println("Non existing directory.");
        }
    }

    /**
     * Removes file/folder from tree/file system
     *
     * @param fileId
     */
    public void remove(int fileId) {
        AbstractFile itemToDelete = rootDir.getItem(fileId);
        if (itemToDelete == null) {
            System.out.println("No such file or folder.");
            return;
        }
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

    /**
     * Helper function for recursive deletion of files in folder
     *
     * @param files
     */
    private void deleteRecursively(File[] files) {
        if (files == null) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                deleteRecursively(files[i].listFiles());
                files[i].delete();
            } else {
                files[i].delete();
            }
        }
    }

    /**
     * Recursive function for file moving
     *
     * @param f - list of files to move
     * @param path - path to move to
     */
    private void moveRecursively(File[] f, String path) {
        for (File file : f) {
            if (file.isDirectory()) {
                File d = new File(path + File.separator + file.getName());
                d.mkdirs();
                moveRecursively(file.listFiles(), d.getAbsolutePath());
            } else {
                file.renameTo(new File(path + File.separator + file.getName()));
            }
        }
    }

    /**
     * Gets the parents ids given its own and adds it to list of ids
     *
     * @param id
     * @param ids
     * @return
     */
    private ArrayList<Integer> getParentIds(int id, ArrayList<Integer> ids) {
        if (id == 0) {
            return ids;
        } else {
            int temp = rootDir.getItem(id).getParentId();
            ids.add(id);
            return getParentIds(temp, ids);
        }
    }

    /**
     * Moves file/folder from one place to another and changes name of file (not
     * folder)
     *
     * @param fileId - id of the item to move (either file or folder)
     * @param folderId - id of the folder to move the item to
     * @param name - new name (for file, folder gets no new name)
     */
    public void moveFile(int fileId, int folderId, String name) {
        Directory newDir;
        AbstractFile itemToMove = rootDir.getItem(fileId);
        if (itemToMove == null) {
            System.out.println("No such file or folder.");
            return;
        }

        File fToMove = new File(itemToMove.getPath());
        Directory parent = (Directory) rootDir.getItem(itemToMove.getParentId());
        if (folderId == 0) {
            newDir = rootDir;
        } else {
            newDir = (Directory) rootDir.getItem(folderId);
        }
        //System.out.println("ND: " + newDir);
        //System.out.println("ND: " + newDir.getName());
        System.out.println("ND: " + newDir);
        System.out.println("ND: " + newDir.getName());
        ArrayList<Integer> a = getParentIds(newDir.getId(), new ArrayList<Integer>());
        for (int i : a) {
            if (i == itemToMove.getId()) {
                System.out.println("Can not move to child folder.");
                return;
            }
        }
        parent.remove(itemToMove.getId());
        name = ("".equals(name)) ? fToMove.getName() : name;
        System.out.println("NAME: " + name);
        if (checkDuplicateNames(newDir.getChildren(), name)) {
            System.out.println("SAME.");
            name = generateUUID() + name;
                System.out.println("SAME.");
                name = generateUUID() + name;
        }
        System.out.println("Not same.");
        String newPath;
        if (itemToMove.getType() == 0) {

            newPath = newDir.getPath() + File.separator + name + File.separator;
            //System.out.println("New path: " + newPath);
            //updateForMove((Directory) itemToMove, int id, String path);
            itemToMove.setPath(newPath);
            itemToMove.setParentId(newDir.getId());
            updatePaths(itemToMove.getChildren(), newPath);
            newPath = newDir.getPath() + File.separator + name + File.separator;
            System.out.println("New path: " + newPath);
            newDir.add(itemToMove);
            parent.getChildren().remove(itemToMove);
            File newF = new File(newPath);
            newF.mkdir();
            moveRecursively(fToMove.listFiles(), newPath);
            deleteRecursively(fToMove.listFiles());
            fToMove.delete();
        } else {

            newPath = newDir.getPath() + File.separator + name;
            itemToMove.setName(name);
            itemToMove.setParentId(newDir.getId());
            itemToMove.setPath(newDir.getPath() + File.separator + name);
            newPath = newDir.getPath() + File.separator + name;
            itemToMove.setName(name);
            newDir.add(itemToMove);
            parent.getChildren().remove(itemToMove);
            fToMove.renameTo(new File(newPath));
        }
    }
    
    /**
     * Updates the path of folder and all its children.
     * Used for moving of file and folder
     * @param afs
     * @param path 
     */
    public void updatePaths(ArrayList<Object> af, String path) {
        for (Object o : af) {
            AbstractFile a = (AbstractFile) o;
            String np = path + File.separator + a.getName();
            if (a.getType() == 0) {
                a.setPath(np);
                updatePaths(a.getChildren(), np);
            } else {
                a.setPath(np);
            }
        }
    }

    /**
     * Checks for duplicate names in List of objects (Directories and files)
     * USeful for determining name repetition and acting upon it to change name
     * of new files (to avoid duplicates and overwrites)
     *
     * @param list - list of files and directories
     * @param name - name to search
     * @return
     */
    private boolean checkDuplicateNames(ArrayList<Object> list, String name) {
        for (Object o : list) {
            AbstractFile af = (AbstractFile) o;
            if (af.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean checkDuplicateNames(ArrayList<Object> list, String name) {
        for (Object o : list) {
            AbstractFile af = (AbstractFile) o;
            if (af.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    private Directory duplicateItem(Directory src, Directory dest) {
        for (Object o : src.getChildren()) {
            AbstractFile af = (AbstractFile) o;
            if (af.getType() == 0) {
                Directory n = new Directory(af.getName(), 
                                            dest.getParentId(), 
                                            dest.getPath(), 
                                            dest.getPermissions());
                n.add(duplicateItem((Directory) af, n));
                dest.add(n);
            } else {
                dest.add(o);
            }
        }
        return dest;
    }
    

    /**
     * Duplicates one Directory structure to another with new ids
     *
     * @param src - source directory
     * @param dest - destination directory
     * @return
     */
    private Directory duplicateItem(Directory src, Directory dest) {
        for (Object o : src.getChildren()) {
            AbstractFile af = (AbstractFile) o;
            if (af.getType() == 0) {
                Directory n = new Directory(af.getName(),
                        dest.getId(),
                        dest.getPath() + File.separator + af.getName(),
                        af.getPermissions());
                n.add(duplicateItem((Directory) af, n));
                dest.add(n);
            } else {
                dest.add(new FileX(af.getName(), dest.getId(),
                        dest.getPath() + File.separator + af.getName(),
                        af.getSize(), af.getPermissions()));
            }
        }
        return dest;
    }

    /**
     * Copies file/folder to a new folder and gives a file a new name
     *
     * @param fileId - id of file/folder to copy
     * @param folderId - id of folder to copy to
     * @param name
     */
    public void copy(int fileId, int folderId, String name) {
        AbstractFile file = rootDir.getItem(fileId);
        File fToCopy = new File(file.getPath());
        Directory newDir = (Directory) rootDir.getItem(folderId);
        File destination = new File(newDir.getPath() + File.separator + file.getName());
        String oldPath = file.getPath();
        name = ("".equals(name)) ? fToCopy.getName() : name;
        if (file.getType() == 0) {
            Directory d = new Directory(file.getName(),
                    newDir.getId(),
                    newDir.getPath() + File.separator + file.getName(),
                    file.getPermissions());
            duplicateItem((Directory) file, d);
            newDir.add(d);
            try {
                copyFolder(fToCopy, destination);
            } catch (IOException ex) {
                Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String newPath = newDir.getPath() + File.separator + name;
            newDir.add(new FileX(name,
                    newDir.getId(),
                    newPath, fToCopy.length(),
                    file.getPermissions()));
            Directory d = new Directory(file.getName(), 
                                        newDir.getParentId(), 
                                        newDir.getPath(), 
                                        file.getPermissions());
            d = duplicateItem((Directory) file, d);
            newDir.add(d);
        } else {
            String newPath = newDir.getPath() + File.separator + name;
            newDir.add(new FileX(name,
                       newDir.getId(),
                       newPath, fToCopy.length(), 
                       file.getPermissions()));
            copyFiles(oldPath, newPath);
        }
    }

    /**
     * Copies files from one location on disk to another
     *
     * @param path1 - location 1
     * @param path2 - location 2
     */
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
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }
            inStream.close();
            outStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lists parents of an item by its id
     *
     * @param id
     */
    public void listParents(int id) {
        listParentsHelper(id, new ArrayList<String>());
    }

    /**
     * Lists folder (file) by its id
     *
     * @param id
     */

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
    
    public void listItem(int id) {
        if (id == 0) {
            rootDir.lsItem();
        } else {
            AbstractFile af = rootDir.getItem(id);
            if (af == null) {
                System.out.println("No such item.");
            } else {
                af.lsItem();
            }
        }
    }

    /**
     * lists item (not its children) by its id
     *
     * @param id
     */
    public void listItem(int id) {
        if (id == 0) {
            rootDir.lsItem();
        } else {
            AbstractFile af = rootDir.getItem(id);
            if (af == null) {
                System.out.println("No such item.");
            } else {
                af.lsItem();
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

    /**
     * Prints out list in reverse order Used to print out ones parents
     *
     * @param list
     */
    private void printList(ArrayList<String> list) {
        String a = "";
        for (int i = list.size() - 1; i >= 0; i--) {
            System.out.println(a + list.get(i));
            a += "   ";
        }
    }

    /**
     * Generates UUID for dealing with repeated names in folder
     *
     * @return
     */
    private String generateUUID() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy_hhmmss");
        return sdf.format(date);
    }

    /**
     * Checks the permissions for a file/folder
     *
     * @param f
     * @return string with r, w and x letter combination
     */
    private String getPermissions(File f) {
        String result = "";
        result += f.canRead() ? "r-" : "-";
        result += f.canWrite() ? "w" : "-";
        result += f.canExecute() ? "-x" : "-";
        return result;
    }

    /**
     * Copies one folder to another location
     *
     * @param src
     * @param dest
     * @throws IOException
     */
    private static void copyFolder(File src, File dest)
            throws IOException {
        if (src.isDirectory()) {
            if (!dest.exists()) {
                dest.mkdir();
            }
            String files[] = src.list();

            for (String file : files) {
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                copyFolder(srcFile, destFile);
            }

        } else {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();
        }
    }
    
    private String generateUUID() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy_hhmmss");
        return sdf.format(date);
    }
    
    private String getPermissions(File f) {
        String result = "";
        result += f.canRead() ? "r-" : "-";
        result += f.canWrite() ? "w" : "-";
        result += f.canExecute()? "-e" : "-";
        return result;
    }
    
     private static void copyFolder(File src, File dest)
    	throws IOException{
 
    	if(src.isDirectory()){
 
    		//if directory not exists, create it
    		if(!dest.exists()){
    		   dest.mkdir();
    		   System.out.println("Directory copied from " 
                              + src + "  to " + dest);
    		}
 
    		//list all the directory contents
    		String files[] = src.list();
 
    		for (String file : files) {
    		   //construct the src and dest file structure
    		   File srcFile = new File(src, file);
    		   File destFile = new File(dest, file);
    		   //recursive copy
    		   copyFolder(srcFile,destFile);
    		}
 
    	} else{
    		//if file, then copy it
    		//Use bytes stream to support all file types
    		InputStream in = new FileInputStream(src);
    	        OutputStream out = new FileOutputStream(dest); 
 
    	        byte[] buffer = new byte[1024];
 
    	        int length;
    	        //copy the file content in bytes 
    	        while ((length = in.read(buffer)) > 0){
    	    	   out.write(buffer, 0, length);
    	        }
 
    	        in.close();
    	        out.close();
    	        System.out.println("File copied from " + src + " to " + dest);
    	}
    }
}
