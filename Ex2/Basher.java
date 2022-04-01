import oop.ex2.*;

/**
 * This class represents a basher spaceship, a sub-class of spaceShip class.
 * This ship attempts to collide with other ships.
 */

public class Basher extends SpaceShip {


    /** The distance from the closest ship from which the basher ship will try to do something */
    final double SMALL_DISTANCE = 0.19;

    /**
     * Override
     * Does the actions of this basher ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        this.shieldOn = false;  // when a round begins - shield is off

        SpaceShipPhysics closestShipPhysics = game.getClosestShipTo(this).getPhysics();
        double angle = this.shipPhysics.angleTo(closestShipPhysics);

        int turnDir; // the direction the basher should turn
        if (angle==0)
            turnDir = 0; // basher is exactly facing closest ship, shouldn't turn
        else if (angle<0)
            turnDir = -1;
        else
            turnDir = 1;

        this.shipPhysics.move(true, turnDir); // always accelerating

        if (this.shipPhysics.distanceFrom(closestShipPhysics) <= SMALL_DISTANCE) // close to another ship
            shieldOn(); // super class turns on shield, if legal

        updateShipData();
    }
}
