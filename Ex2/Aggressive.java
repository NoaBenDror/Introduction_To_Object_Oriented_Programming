import oop.ex2.*;

/**
 * This class represents an aggressive spaceship, a sub-class of spaceShip class.
 * This ship pursues other ships and tries to fire at them.
 */

public class Aggressive extends SpaceShip {

    /** The angle to the closest ship from which the runner ship will try to do something */
    final double SMALL_ANGLE = 0.21;

    /**
     * Override
     * Does the actions of this aggressive ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        this.shieldOn = false;  // when a round begins - shield is off

        SpaceShipPhysics closestShipPhysics = game.getClosestShipTo(this).getPhysics();
        double angle = this.shipPhysics.angleTo(closestShipPhysics);

        int turnDir; // the direction the aggressive should turn
        if (angle == 0)
            turnDir = 0; // aggressive is exactly facing closest ship, shouldn't turn
        else if (angle < 0)
            turnDir = -1;
        else
            turnDir = 1;

        this.shipPhysics.move(true, turnDir); // always accelerating

        if (Math.abs(angle) < SMALL_ANGLE) // small angle to closest ship
            fire(game); // super class fires, if legal

        updateShipData();
    }
}
