/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lurajcevi_zadaca_1.composite;

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
    
    public FileX(String name, int parentId, String path) {
        this.m_name = name;
        this.m_id = FileSystem.componentId;
        this.parent_id = parentId;
        this.path = path;
        FileSystem.componentId += 1;
    }
    
    @Override
    public void ls() {
        String id = "" + m_id;
        System.out.println(FileSystem.g_indent + id + ": " + m_name);
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
    
}
