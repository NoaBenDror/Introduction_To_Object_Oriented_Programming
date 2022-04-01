import oop.ex2.*;
import java.awt.Image;

/**
 * This class represents a human spaceship, a sub-class of spaceShip class.
 * It behaves according to user's input.
 */

public class Human extends SpaceShip {

    /**
     * Override
     * Does the actions of this human ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        this.shieldOn = false;  // when a round begins - shield is off

        if (game.getGUI().isTeleportPressed())
            teleport();  // super class teleports, if legal

        boolean accelerate = game.getGUI().isUpPressed();
        boolean turnRight = game.getGUI().isRightPressed();
        boolean turnLeft = game.getGUI().isLeftPressed();

        int turnDir = 0;
        if (turnLeft && ! turnRight)
            turnDir = 1;
        if (turnRight && !turnLeft)
            turnDir = -1; // if both true - turnDir stays 0
        this.shipPhysics.move(accelerate, turnDir);  // make move according to user's input

        if (game.getGUI().isShieldsPressed())
            shieldOn();  // super class turns shield on, if legal

        if (game.getGUI().isShotPressed())
            fire(game); // fires, if legal

        updateShipData();
    }

    /**
     * Override
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public Image getImage() {
        if (this.shieldOn)
            return GameGUI.SPACESHIP_IMAGE_SHIELD;
        return GameGUI.SPACESHIP_IMAGE;
    }
}