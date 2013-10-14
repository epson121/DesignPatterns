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
    
    public FileX(String name) {
        m_name = name;
        m_id = FileSystem.componentId;
        FileSystem.componentId += 1;
    }
    
    @Override
    public void ls() {
        String id = "" + m_id;
        System.out.println(FileSystem.g_indent + id + ": " + m_name);
    }
        
}
