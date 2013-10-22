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
    private long size;
    
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
        String id = "" + this.m_id;
        String sizeAndElements = "  " + this.size + " #el: " + this.m_fs.size();
        System.out.println(FileSystem.g_indent + id + ":" + m_name + sizeAndElements);
        //System.out.println(this.getPath());
        FileSystem.g_indent.append("   ");
        for (int i = 0; i < m_fs.size(); i++) {
            AbstractFile obj = (AbstractFile) m_fs.get(i);
            obj.ls();
        }
        FileSystem.g_indent.setLength(FileSystem.g_indent.length() - 3);
    }
    
    
    public AbstractFile getItem(int id) {
        if (id == 0)
            return this;
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

    @Override
    public long getSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Object> getChildren() {
        return m_fs;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }
    
        @Override
    public void setName(String name) {
        this.m_name = name;
    }

    @Override
    public void setId(int id) {
        this.m_id = id;
    }
    
    
    
}
