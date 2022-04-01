import oop.ex3.spaceship.Item;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a stockroom. Each stockroom has a capacity, which is the total amount of storage
 * units it can hold. A stockroom can contain different types of items. All items of the same type take up
 * the same amount of storage units
 */

public class Stockroom {

    /** The total capacity of the stockroom */
    private final int capacity;

    /** The available capacity of the stockroom */
    protected int availableCapacity;

    /** The inventory in the stockroom */
    private Map<String, Integer> inventory;

    /** General error message */
    protected final static String GEN_ERROR = "Error: Your request cannot be completed at this time.";

    /** Negative number error */
    protected final static String NEG_NUM_ERROR = "Error: Your request cannot be completed at this time." +
            " Problem: cannot remove a negative number of items of type ";

    /** The locker doesn't contain n items error */
    protected final static String DOESNT_CONTAIN_ERROR = "Error: Your request cannot be completed at" +
            " this time. Problem: the locker does not contain ";

    /** Moved items to LTS message */
    protected final static String MOVED_TO_LTS_MESS = "Warning: Action successful, but has caused items" +
            " to be moved to storage";

    /** Error message part 1 */
    protected final static String ERROR_1 = "Error: Your request cannot be completed at this time." +
            " Problem: no room for ";

    /** Error message part 2 */
    protected final static String ERROR_2 = " items of type ";

    /** did not succeed to add new item*/
    protected final static int UNSUCCESSFUL = -1;

    /** did succeed to add new item*/
    protected final static int SUCCESSFUL = 0;

    /** did succeed to add new item, but moved items to LTS*/
    protected final static int SUCCESSFUL_AND_LTS = 1;

    /*----= Constructor =-----*/

    /**
     * initializes a stockroom
     * @param capacity The total capacity of the stockroom
     */

    public Stockroom(int capacity){
        this.capacity = capacity;
        this.availableCapacity = capacity;
        this.inventory = new HashMap<>();
    }

    /*----= Instance Methods =-----*/

    /**
     * This method adds n Items of the given type to the stockroom. If the action is SUCCESSFUL, this method
     * should return 0. If n Items cannot be added to the locker at this time, no Items should be added,
     * the method should return -1.
     * @param item type to be added
     * @param n num of items of type item to be added
     * @return 0 if adding was SUCCESSFUL, -1 if UNSUCCESSFUL
     */

    public int addItem(Item item, int n){
        if (n<0) {
            System.out.println(GEN_ERROR);
            return UNSUCCESSFUL;
        }

        if (getAvailableCapacity() < n*item.getVolume()){ // no room
            System.out.println(ERROR_1 + n + ERROR_2 + item.getType());
            return UNSUCCESSFUL;
        }

        getInventory().put(item.getType(),getItemCount(item.getType())+n); // add item
        this.availableCapacity -= n*item.getVolume();
        return SUCCESSFUL;
    }

    /**
     * @return the number of Items of type type the stockroom contains
     */

    public int getItemCount(String type){
        if (this.inventory.containsKey(type))
            return this.inventory.get(type);
        return 0; // no Items of type type in the stockroom
    }

    /**
     * @return a map of all the Items contained in the stockroom, and their respective quantities.
     */

    public Map<String, Integer> getInventory(){
        return this.inventory;
    }

    /**
     * @return the total capacity of the stockroom
     */

    public int getCapacity(){
        return this.capacity;
    }

    /**
     * @return the available capacity of the stockroom
     */

    public int getAvailableCapacity(){
        return this.availableCapacity;
    }
}