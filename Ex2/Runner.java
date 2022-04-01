import oop.ex2.*;

/**
 * This class represents a runner spaceship, a sub-class of spaceShip class.
 * This spaceship attempts to run away from the fight
 */

public class Runner extends SpaceShip {

    /** The angle to the closest ship from which the runner ship will try to do something */
    final double SMALL_ANGLE = 0.21;

    /** The distance to the closest ship from which the runner ship will try to do something */
    final double SMALL_DISTANCE = 0.25;

    /**
     * Override
     * Does the actions of this runner ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        this.shieldOn = false;  // when a round begins - shield is off

        SpaceShipPhysics closestShipPhysics = game.getClosestShipTo(this).getPhysics();
        double angle = this.shipPhysics.angleTo(closestShipPhysics);

        if (this.shipPhysics.distanceFrom(closestShipPhysics) < SMALL_DISTANCE &&
                Math.abs(closestShipPhysics.angleTo(this.shipPhysics)) < SMALL_ANGLE) { // too threatening

            teleport(); // super class teleports, if legal
            closestShipPhysics = game.getClosestShipTo(this).getPhysics();
            angle = this.shipPhysics.angleTo(closestShipPhysics);
        }

        int turnDir; // the direction the runner should turn
        if (angle == Math.PI || angle == -Math.PI)  // already running to wanted direction, shouldn't turn
            turnDir = 0;
        else if (angle == 0)
            turnDir = 1; // runner is exactly facing closest ship, could either turn left or right
        else if (angle < 0)
            turnDir = 1;
        else
            turnDir = -1;

        this.shipPhysics.move(true, turnDir); // always accelerating

        updateShipData();
    }
}