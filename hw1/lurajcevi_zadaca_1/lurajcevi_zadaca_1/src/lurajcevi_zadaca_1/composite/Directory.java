/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lurajcevi_zadaca_1.composite;

import java.util.ArrayList;
import lurajcevi_zadaca_1.FileSystem;

/**
 *
 * @author luka
 */
public class Directory implements AbstractFile {
    
    private String m_name;
    private int m_id;
    private int parent_id;
    private ArrayList m_fs = new ArrayList();
    private String path;
    
    public Directory(String name, int parentId, String path) {
        this.m_name = name;
        this.m_id = FileSystem.componentId;
        this.parent_id = parentId;
        this.path = path;
        FileSystem.componentId += 1;
    }

    public void add(Object obj) {
        m_fs.add(obj);
    }

    @Override
    public void ls() {
        String id = "" + m_id;
        System.out.println(FileSystem.g_indent + id + ": " + m_name);
        //System.out.println(this.getPath());
        FileSystem.g_indent.append("   ");
        for (int i = 0; i < m_fs.size(); i++) {
            AbstractFile obj = (AbstractFile) m_fs.get(i);
            obj.ls();
        }
        FileSystem.g_indent.setLength(FileSystem.g_indent.length() - 3);
    }
    
    
    public AbstractFile getItem(int id) {
        for (int i = 0; i < this.m_fs.size(); i++) {
            AbstractFile obj = (AbstractFile) m_fs.get(i);
            //System.out.println("This name: " + obj.getName());
            if (obj.getId() == id) {
                return obj;
            } else if (obj.getType() == 0) {
                Directory d = (Directory) obj;
                AbstractFile af = d.getItem(id);
                if (af != null) {
                    return af;
                }
            }
            
        }
        return null;
    }
    
     public boolean remove(int id) {
        for (int i = 0; i < this.m_fs.size(); i++) {
            AbstractFile obj = (AbstractFile) m_fs.get(i);
            if (obj.getId() == id) {
                m_fs.remove(obj);
                return true;
            } else if (obj.getType() == 0) {
                Directory d = (Directory) obj;
                boolean r = d.remove(id);
                if (r) {
                    return r;
                }
            }
        }
        return false;
    }

    @Override
    public int getId() {
        return this.m_id;
    }
    
    @Override
    public int getType(){
        return 0;
    }

    @Override
    public int getParentId() {
        return this.parent_id;
    }
    /*
    @Override
    public void getParents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    */
    
    @Override
    public String getName() {
        return this.m_name;
    }

    @Override
    public String getPath() {
        return this.path;
    }
    
}
