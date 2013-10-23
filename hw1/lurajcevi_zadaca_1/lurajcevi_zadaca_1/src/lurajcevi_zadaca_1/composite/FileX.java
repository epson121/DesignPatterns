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
public class FileX implements AbstractFile {
    
    private String m_name;
    private int m_id;
    private int parent_id;
    private String path;
    private long size;
    private String permissions;
    
    public FileX(String name, int parentId, String path, long size, String permissions) {
        this.m_name = name;
        this.m_id = FileSystem.componentId;
        this.parent_id = parentId;
        this.path = path;
        this.size = size;
        this.permissions = permissions;
        FileSystem.componentId += 1;
    }
    
    @Override
    public void ls() {
        String id = "" + this.m_id;
        //String sz = "  " + this.size;
        System.out.println(FileSystem.g_indent 
                           + id + ": " + m_name 
                           + "  " + permissions);
        //System.out.println(this.getPath());
    }

    @Override
    public int getId() {
        return m_id;
    }
    
    @Override
    public int getType(){
        return 1;
    }

    @Override
    public int getParentId() {
        return this.parent_id;
    }

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
        return this.size;
    }

    @Override
    public ArrayList<Object> getChildren() {
        return new ArrayList<>();
    }

    @Override
    public void setName(String name) {
        this.m_name = name;
    }

    @Override
    public void setId(int id) {
        this.m_id = id;
    }

    @Override
    public void lsItem() {
        ls();
    }

    @Override
    public String getPermissions() {
        return this.permissions;
    }
}
