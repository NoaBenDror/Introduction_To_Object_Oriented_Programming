import oop.ex2.*;

/**
 * This class represents a special spaceship, a sub-class of spaceShip class.
 * On the first round, this ship finds the closest ship, and makes it its victim for the rest of the game.
 */
public class Special extends SpaceShip{

    /** The closest ship on the first round */
    private SpaceShip victimShip;

    /** The angle to the closest ship from which the special ship will try to do something */
    final double SMALL_ANGLE = 0.21;

    /**
     * Constructor - initializes a special spaceship
     */

    public Special(){
        victimShip = null;
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

        if (victimShip == null)  // means it's the first round
            victimShip = game.getClosestShipTo(this);

        double angle = this.shipPhysics.angleTo(victimShip.getPhysics());

        int turnDir; // the direction the aggressive should turn
        if (angle == 0)
            turnDir = 0; // aggressive is exactly facing closest ship, shouldn't turn
        else if (angle < 0)
            turnDir = -1;
        else
            turnDir = 1;

        this.shipPhysics.move(true, turnDir);

        if (Math.abs(angle) < SMALL_ANGLE) // small angle to closest ship
            fire(game); // super class fires, if legal

        updateShipData();
    }
}