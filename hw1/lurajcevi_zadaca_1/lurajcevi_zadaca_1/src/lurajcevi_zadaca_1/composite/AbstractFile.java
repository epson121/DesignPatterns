/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lurajcevi_zadaca_1.composite;

import java.util.ArrayList;

/**
 *
 * @author luka
 */
public interface AbstractFile {
    
    public void ls();
    public int getType();
    public int getId();
    public void setId(int id);
    public int getParentId();
    public String getName();
    public void setName(String name);
    public String getPath();
    public long getSize();
    public ArrayList<Object> getChildren();    
}
