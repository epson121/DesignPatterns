
package lurajcevi_zadaca_2.command;

import lurajcevi_zadaca_2.model.SportsClub;

/**
 *
 * @author luka
 */
public class PositionLoss implements Command {
    private SportsClub sportsClub;

    public PositionLoss(SportsClub sportsClub) {
        this.sportsClub = sportsClub;
    }
    
    /**
     * Execute command on a sportsclub when it loses a position
     */
    @Override
    public void execute() {
        sportsClub.positionLoss();
    }
    
}
