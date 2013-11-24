/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lurajcevi_zadaca_2.state;

/**
 *
 * @author luka
 */
public interface SportsClubState {
    
    public void positionLoss();
    public void positionGain();
    public void samePosition();
    public boolean canPlay();
}
