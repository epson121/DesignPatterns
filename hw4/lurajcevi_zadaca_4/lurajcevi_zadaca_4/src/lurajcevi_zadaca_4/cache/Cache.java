/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lurajcevi_zadaca_4.cache;

/**
 * Cache interface
 * @author luka
 */
public interface Cache {
    public void release(LinkDocument document);
    public LinkDocument acquire(String url);
}
