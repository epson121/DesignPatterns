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
    
    private static ArrayList<Directory> ds = new ArrayList<>();
    private String m_name;
    private int m_id;
    private ArrayList m_fs = new ArrayList();
    private ArrayList m_ids = new ArrayList();

    public Directory(String name) {
        m_name = name;
        m_id = FileSystem.componentId;
        FileSystem.componentId += 1;
    }

    public void add(Object obj) {
        m_fs.add(obj);
        m_ids.add(m_id);
    }

    @Override
    public void ls() {
        String id = "" + m_id;
        System.out.println(FileSystem.g_indent + id + ": " + m_name);
        FileSystem.g_indent.append("   ");
        for (int i = 0; i < m_fs.size(); i++) {
            AbstractFile obj = (AbstractFile) m_fs.get(i);
            obj.ls();
        }
        FileSystem.g_indent.setLength(FileSystem.g_indent.length() - 3);
    }
    
    public void printDir(int id) {
        AbstractFile obj = (AbstractFile) m_fs.get(id);
        obj.ls();
    }
    
    public Directory getDir(int id) {
        return (Directory) m_fs.get(id);
    }
}
