/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lurajcevi_zadaca_1;

import java.util.regex.Matcher;

/**
 * Helper class for simpler handling of regex matching
 * in main class.
 * @author luka
 */
public class Command {
    private int id;
    private Matcher m;

    public Command(int id, Matcher m) {
        this.id = id;
        this.m = m;
    }

    public int getId() {
        return id;
    }

    public Matcher getM() {
        return m;
    }
    
    
}
