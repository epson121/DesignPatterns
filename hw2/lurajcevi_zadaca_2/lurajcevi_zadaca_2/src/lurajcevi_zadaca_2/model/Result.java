
package lurajcevi_zadaca_2.model;

import java.util.Random;

/**
 *
 * @author luka
 */
public class Result {
    
    private SportsClub firstClub;
    private SportsClub secondClub;
    private int winner, firstClubScore, secondClubScore;
    private int roundId;
    private Random rand = new Random();
    
    public Result(){}
    
    public Result(SportsClub firstClub, SportsClub secondClub, int roundId) {
        this.firstClub = firstClub;
        this.secondClub = secondClub;
        this.roundId = roundId;
        playMatch();
    }
    
    /**
     * Generate scores for two clubs and update the points
     * accordingly.
     */
    private void playMatch() {
        firstClubScore = rand.nextInt(5);
        secondClubScore = rand.nextInt(5);
        if (firstClubScore > secondClubScore) {
            winner = 1;
            firstClub.updatePoints(3);
        } else if (firstClubScore < secondClubScore) {
            winner = 2;
            secondClub.updatePoints(3);
        } else {
            winner = 0;
            firstClub.updatePoints(1);
            secondClub.updatePoints(1);
        }
        firstClub.addRoundPlayed(roundId);
        secondClub.addRoundPlayed(roundId);
    }
    
    /**
     * Print out the result
     */
    public void printResult() {
        System.out.println(firstClub.getSportsClubName() + " : " 
                           + secondClub.getSportsClubName() + " " 
                           + firstClubScore + " : " + secondClubScore + "  "
                           + "(" + winner + ")");
    }
    
    /*
     * 
     * GETTERS AND SETTERS
     * 
     */
    
    public SportsClub getFirstClub() {
        return firstClub;
    }

    public SportsClub getSecondClub() {
        return secondClub;
    }

    public int getWinner() {
        return winner;
    }

    public int getFirstClubScore() {
        return firstClubScore;
    }

    public int getSecondClubScore() {
        return secondClubScore;
    }
    
}
