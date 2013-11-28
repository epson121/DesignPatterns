
package lurajcevi_zadaca_2.command;

import lurajcevi_zadaca_2.model.SportsClub;

/**
 *
 * @author luka
 */
public class SamePosition implements Command {
    private SportsClub sportsClub;

    public SamePosition(SportsClub sportsClub) {
        this.sportsClub = sportsClub;
    }
    
    /**
     * Execute command on a sportsclub when its position stays the same
     */
    @Override
    public void execute() {
        sportsClub.samePosition();
    }
    
    
}
