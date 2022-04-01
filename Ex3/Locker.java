import oop . ex3 . spaceship .*;

/**
 * This class represents a locker (extends Stockroom). Some items from this locker will be moved
 * to a centralized long-term storage, if they take more than 50% of the storage units of this locker.
 */

public class Locker extends Stockroom {

    /** The long-term storage, of all lockers */
    static LongTermStorage LTS = new LongTermStorage();

    /** Percentage of limitation of items from the same type in the locker, before deciding whether
     *  to move them to LTS */
    private final static double LIMIT_BEFORE_MOVE = 0.5;

    /** Percentage of limitation of items from the same type in the locker, after moving them to LTS*/
    private final static double LIMIT_AFTER_MOVE = 0.2;

    /** did not succeed to add new item. The reason - football and baseball bat can't reside in the same
     *  locker */
    final static int CONSTRAINT_UNSUCCESSFUL = -2;

    /** Error constraint part 1*/
    private final static String ERROR_CONSTRAINT_1 = "Error: Your request cannot be completed at this time."+
            " Problem: the locker cannot contain items of type ";

    /** Error constraint part 2 */
    private final static String ERROR_CONSTRAINT_2 = " as it contains a contradicting item";

    /** A football string */
    private final static String FOOTBALL_STR = "football";

    /** A baseball bat string */
    private final static String BASEBALLBAT_STR = "baseball bat";

    /*----= Constructor =-----*/

    /**
     * Initializes a locker
     * @param capacity The total capacity of the locker
     */

    public Locker(int capacity){
        super(capacity);
    }

    /*----= Instance Methods =-----*/

    /**
     * This method adds n Items of the given type to the locker. If the addition is SUCCESSFUL and doesn’t
     * cause Items to be moved to long-term storage, this method should return 0.
     * If n Items cannot be added to the locker at this time, no Items should be added,
     * the method should return -1.
     * If this action causes n* Items to be moved to long-term storage and it can accommodate all n* Items,
     * the method should return 1.
     * If this action requires Items to be moved to long-term storage, but it doesn’t have room to
     * accommodate all n* Items, then no Items should be added, the method should return -1,
     * @param item type to be added
     * @param n num of items of type item to be added
     * @return 0 if adding was SUCCESSFUL, -1 if UNSUCCESSFUL
     */

    public int addItem(Item item, int n){

        if ((item.getType().equals(FOOTBALL_STR) && getInventory().containsKey(BASEBALLBAT_STR) ||
                (item.getType().equals(BASEBALLBAT_STR) && getInventory().containsKey(FOOTBALL_STR)))){
            System.out.println(ERROR_CONSTRAINT_1 + item.getType() + ERROR_CONSTRAINT_2);
            return CONSTRAINT_UNSUCCESSFUL;
        }

        if (n<0) {
            System.out.println(GEN_ERROR);
            return UNSUCCESSFUL;
        }

        if (getAvailableCapacity() < n*item.getVolume()){ // no room in locker
            System.out.println(ERROR_1 + n + ERROR_2 + item.getType());
            return UNSUCCESSFUL;
        }

        getInventory().put(item.getType(),getItemCount(item.getType())+n);
        this.availableCapacity-=n*item.getVolume();

        if (getCapacity()*LIMIT_BEFORE_MOVE >= getItemCount(item.getType())*item.getVolume())
            return SUCCESSFUL;

        // take up more than 50%
        double diff = getItemCount(item.getType())*item.getVolume() - getCapacity()* LIMIT_AFTER_MOVE;
        int moveToLTS = (int)Math.ceil(diff/item.getVolume());

        if (LTS.addItem(item, moveToLTS) == 0) { // there is room in long-term storage
            getInventory().put(item.getType(), getItemCount(item.getType()) - moveToLTS);
            System.out.println(MOVED_TO_LTS_MESS);
            this.availableCapacity+=moveToLTS*item.getVolume();
            return SUCCESSFUL_AND_LTS;
        }

        // no room in long-term storage
        getInventory().put(item.getType(),getItemCount(item.getType())-n);
        this.availableCapacity+=n*item.getVolume();
        return UNSUCCESSFUL;
    }

    /**
     * This method removes n Items of the type type from the locker. If the action is SUCCESSFUL, this
     * method should return 0. In case there are less than n Items of this type in the locker, no Items
     * should be removed, the method should return -1. In case n is negative, no Items should be removed,
     * the method should return -1.
     * @param item type to be removed
     * @param n num of items of type item to be removed
     * @return 0 if removing was SUCCESSFUL, -1 if UNSUCCESSFUL
     */

    public int removeItem(Item item, int n){
        if (n<0) {
            System.out.println(NEG_NUM_ERROR + item.getType());
            return UNSUCCESSFUL;
        }

        if (!getInventory().containsKey(item.getType())) {
            System.out.println(DOESNT_CONTAIN_ERROR + n + ERROR_2 + item.getType());
            return UNSUCCESSFUL;
        }

        if (getItemCount(item.getType()) < n){
            System.out.println(DOESNT_CONTAIN_ERROR + n + ERROR_2 + item.getType());
            return UNSUCCESSFUL;
            }

        // if we got here - it means we can remove n items
        getInventory().put(item.getType(), getItemCount(item.getType())-n);
        if (getInventory().get(item.getType())==0)
            getInventory().remove(item.getType()); // no items of type type, remove from inventory

        this.availableCapacity+=n*item.getVolume();
        return SUCCESSFUL;
    }
}