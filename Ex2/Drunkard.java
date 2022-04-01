import oop.ex2.*;

import java.util.Random;

/**
 * This class represents a drunkard spaceship, a sub-class of spaceShip class.
 * This ship does random stuff.
 */

public class Drunkard extends SpaceShip {

    /** Counts round up to 100, then zeros and counts rounds up to 100 again */
    private int roundsCounter;

    /** Represents which random action should be taken*/
    private int currRandomAction;

    /**
     * Constructor - initializes a drunkard spaceship
     */

    public Drunkard(){
        roundsCounter = 100;
        currRandomAction = 0;
    }

    /**
     * Override
     * Does the actions of this random ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        this.shieldOn = false;  // when a round begins - shield is off

        if (roundsCounter == 100) {
            Random randomAction = new Random();
            currRandomAction = randomAction.nextInt(5);
            roundsCounter = 0; // zeros the counter
        }

        int turn;
        switch (currRandomAction){
            case 0:
                fire(game);
                turn = 0;
                break;
            case 1:
                turn = 1;
                break;
            default:
                teleport();
                turn = 0;
            }

        this.shipPhysics.move(false, turn);

        roundsCounter++;  // count each round

        updateShipData();
    }
}