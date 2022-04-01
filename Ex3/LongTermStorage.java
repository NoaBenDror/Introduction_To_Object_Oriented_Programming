/**
 * This class represents a centralized long-term storage (extends Stockroom), which has a capacity of 1000
 * storage units. Items from lockers will be moved to here, if they take more than 50% of the storage units
 * of that locker.
 */
public class LongTermStorage extends Stockroom {

    /** the capacity of a long-term storage */
    private final static int longTermCapacity = 1000;

    /*----= Constructor =-----*/

    /**
     * initializes a long-term storage
     */

    public LongTermStorage(){
        super(longTermCapacity);
    }

    /*----= Instance Methods =-----*/

    /**
     * resets the long-term storage’s inventory
     */

    public void resetInventory(){
        getInventory().clear();
        this.availableCapacity = getCapacity();
    }
}