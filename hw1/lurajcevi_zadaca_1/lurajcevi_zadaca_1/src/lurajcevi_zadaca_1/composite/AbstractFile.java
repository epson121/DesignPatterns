/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lurajcevi_zadaca_1.composite;

/**
 *
 * @author luka
 */
public interface AbstractFile {
    
    public void ls();
    public int getType();
    public int getId();
    public int getParentId();
    public String getName();
    public String getPath();
    
}
