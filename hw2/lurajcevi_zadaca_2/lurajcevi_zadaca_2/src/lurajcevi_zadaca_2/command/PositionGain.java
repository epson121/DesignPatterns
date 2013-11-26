
package lurajcevi_zadaca_2.command;

import lurajcevi_zadaca_2.model.SportsClub;

/**
 *
 * @author luka
 */
public class PositionGain implements Command {
    
    private SportsClub sportsClub;

    public PositionGain(SportsClub sportsClub) {
        this.sportsClub = sportsClub;
    }
    
    @Override
    public void execute() {
        sportsClub.positionGain();
    }
    
}