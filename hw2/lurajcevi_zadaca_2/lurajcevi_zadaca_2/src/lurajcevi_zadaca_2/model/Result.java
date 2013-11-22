
package lurajcevi_zadaca_2.model;

import java.util.Random;

/**
 *
 * @author luka
 */
public class Result {
    
    private SportsClub firstClub;
    private SportsClub secondClub;
    private int winner;
    private Random rand = new Random();
    
    public Result(){}
    
    public Result(SportsClub firstClub, SportsClub secondClub) {
        this.firstClub = firstClub;
        this.secondClub = secondClub;
        this.playMatch();
    }
    
    /**
     * Generate scores for two clubs and update the points
     * accordingly.
     */
    private void playMatch() {
        int firstClubScore = rand.nextInt(5);
        int secondClubScore = rand.nextInt(5);
        if (firstClubScore > secondClubScore) {
            this.winner = 1;
            this.firstClub.updatePoints(3);
        } else if (firstClubScore < secondClubScore) {
            this.winner = 2;
            this.secondClub.updatePoints(3);
        } else {
            this.winner = 0;
            this.firstClub.updatePoints(1);
            this.secondClub.updatePoints(1);
        }
    }

    public SportsClub getFirstClub() {
        return firstClub;
    }

    public SportsClub getSecondClub() {
        return secondClub;
    }

    public int getWinner() {
        return winner;
    }
    
}
