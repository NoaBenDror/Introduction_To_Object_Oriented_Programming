import java.awt.Image;
import oop.ex2.*;

/**
 * This class represents a spaceship. This is a super class of all kinds of spaceships.
 */

public class SpaceShip{

    /** The final health level of the ship */
    final int HEALTH_LEVEL = 22;

    /** The final maximal energy level of the ship */
    final int MAX_ENERGY_LEVEL = 210;

    /** The final maximal energy level of the ship */
    final int CURR_ENERGY_LEVEL = 190;

    /** The final cost of teleporting */
    final int COST_TELEPORT = 140;

    /** The final cost of firing a shot */
    final int COST_FIRE = 19;

    /** The final cost of putting a shield on */
    final int COST_SHIELD_ON = 3;

    /** The final cost of getting hit */
    final int COST_GETTING_HIT = 10;

    /** The final gain of bashing another ship while shield is on */
    final int GAIN_BASHING = 18;

    /** The position, direction and velocity of the ship */
    protected SpaceShipPhysics shipPhysics;

    /** The maximal energy level of the ship */
    protected int maxEnergyLevel;

    /** The current energy level of the ship */
    protected int currEnergyLevel;

    /** The health level of the ship */
    private int healthLevel;

    /** State of the ship's shield - on or off */
    protected boolean shieldOn;

    /** A counter that counts rounds after shooting until shooting is legal again */
    protected int legalShotCounter;

    /**
     * Constructor - initializes a spaceship
     */

    public SpaceShip(){
        reset(); // calls reset method, which is also called at death
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * 
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
       this.shieldOn = false;  // when a round begins - shield is off

       this.shipPhysics.move(false,0); // make move

       updateShipData();
    }

    /**
     * This method is called after each move the ship has made, and updates it's data
     */

    public void updateShipData(){

        if (this.legalShotCounter >0 && this.legalShotCounter < 8) // means the spaceship can't fire
            this.legalShotCounter++; // count this round as illegal shot round
        else
            this.legalShotCounter = 0; // means the spaceship can fire

        this.currEnergyLevel++; // constantly charging by 1
        if (this.maxEnergyLevel < this.currEnergyLevel) // up to maxEnergyLevel
            this.currEnergyLevel = this.maxEnergyLevel;
    }

    /**
     * This method is called every time a collision with this ship occurs
     */
    public void collidedWithAnotherShip() {
        if (!this.shieldOn) {  // shield off
            this.healthLevel--; // healthLevel down by 1
            this.maxEnergyLevel -= COST_GETTING_HIT;  // maxEnergyLevel down by 10
            if (this.maxEnergyLevel < 0) // make sure energy level is non-negative
                this.maxEnergyLevel = 0;
            if (this.maxEnergyLevel < this.currEnergyLevel)
                this.currEnergyLevel = this.maxEnergyLevel;
        }
        else {  // shield on
            this.maxEnergyLevel +=GAIN_BASHING;
            this.currEnergyLevel+=GAIN_BASHING;
            }
        }

    /** 
     * This method is called whenever a ship has died. It resets the ship's 
     * attributes, and starts it at a new random position.
     */
    public void reset(){
        this.shipPhysics = new SpaceShipPhysics();
        this.maxEnergyLevel = MAX_ENERGY_LEVEL;
        this.currEnergyLevel = CURR_ENERGY_LEVEL;
        this.healthLevel = HEALTH_LEVEL;
        this.shieldOn = false;
        this.legalShotCounter = 0;
    }

    /**
     * Checks if this ship is dead.
     * 
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        if (this.healthLevel == 0)
            return true;
        return false;
    }

    /**
     * Gets the physics object that controls this ship.
     * 
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return this.shipPhysics;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        if (!this.shieldOn) {  // shield off
            this.healthLevel--;
            this.maxEnergyLevel -= COST_GETTING_HIT;
            if (this.maxEnergyLevel < 0)
                this.maxEnergyLevel = 0;
            if (this.maxEnergyLevel < this.currEnergyLevel)
                this.currEnergyLevel = this.maxEnergyLevel;
        }
    }


    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     * 
     * @return the image of this ship.
     */
    public Image getImage(){
        if (this.shieldOn)
            return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
        return GameGUI.ENEMY_SPACESHIP_IMAGE;
    }

    /**
     * Attempts to fire a shot.
     * 
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        if (this.legalShotCounter ==0 && this.currEnergyLevel >= COST_FIRE) { // shooting is legal
            legalShotCounter++; // the ship fired - count rounds from now
            game.addShot(this.shipPhysics);
            this.currEnergyLevel-=COST_FIRE;
        }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if (this.currEnergyLevel >= COST_SHIELD_ON){
            shieldOn = true; // succeeded to turn on the shield
            this.currEnergyLevel-=COST_SHIELD_ON;
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if (this.currEnergyLevel >= COST_TELEPORT){ // succeeded to teleport
            this.shipPhysics = new SpaceShipPhysics();
            this.currEnergyLevel-=COST_TELEPORT;
        }
    }
}