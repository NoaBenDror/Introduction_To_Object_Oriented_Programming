/**
 * This class represents a hash-set based on chaining. Extends SimpleHashSet.
 */

public class OpenHashSet extends SimpleHashSet {

    /** The data structure the elements are stored in */
    private LinkedListHolder[] hashArray;

    /** The string representation of the name of this class*/
    private final static  String className = "OpenHashSet";

    /*----= Constructors =-----*/

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */

    public OpenHashSet(){
        this(DEFAULT_HIGHER_CAPACITY, DEFAULT_LOWER_CAPACITY);
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16)
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor){
        super(upperLoadFactor, lowerLoadFactor);
        hashArray = new LinkedListHolder[INITIAL_CAPACITY];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values
     * should be ignored. The new table has the default values of initial capacity (16), upper load
     * factor (0.75), and lower load factor (0.25)
     * @param data Values to add to the set.
     */

    public OpenHashSet(java.lang.String[] data){
        this();
        for(int i=0; i<data.length; i++)
            add(data[i]); // add each element in data to the set
    }

    /*----= Instance Methods =-----*/

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(java.lang.String newValue){
        if (!contains(newValue)) {
            checkAndResize(INCREASE); //check if we need to resize the table before adding.if yes-will resize
            int clampedIndex = clamp(newValue.hashCode());
            if (hashArray[clampedIndex] == null) //no items in this cell, initialize a LinkedListHolder
                hashArray[clampedIndex] = new LinkedListHolder();
            hashArray[clampedIndex].getLinkedList().add(newValue);
            this.numOfElements++;
            return true;
            }
        return false; // newValue already exists in the set
    }

    /**
     * Resizes and rehashes this hash set
     * @param changeSize Represents whether we need to decrease or increase the table size
     */

    protected void resize(double changeSize) {
        LinkedListHolder[] tempHashArray = this.hashArray;
        int newCapacity = (int)(this.currCapacity*changeSize);
        if (newCapacity < ONE) // might happen only when deleting
            return; // no need to resize
        this.currCapacity = newCapacity;
        this.hashArray = new LinkedListHolder[this.currCapacity];
        this.numOfElements = 0 ; // will be taken care of in "add" method
        for (int i=0; i<tempHashArray.length;i++){ // traverse the original array
            if (tempHashArray[i]!=null){
                // add all the elements from all the LinkedLists
                for (int j=0; j<tempHashArray[i].getLinkedList().size();j++)
                    this.add(tempHashArray[i].getLinkedList().get(j));
            }
        }
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */

    public boolean contains(java.lang.String searchVal){
        int clampedIndex = clamp(searchVal.hashCode());
        if (this.hashArray[clampedIndex]!= null &&
                this.hashArray[clampedIndex].getLinkedList().contains(searchVal))
            return true;

        return false; // searchVal not found
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */

    public boolean delete(java.lang.String toDelete){
        if (!contains(toDelete))
            return false; // toDelete not found
        int clampedIndex = clamp(toDelete.hashCode());
        this.hashArray[clampedIndex].getLinkedList().remove(toDelete);
        if (this.hashArray[clampedIndex].getLinkedList().size()== 0) // no String elements in this cell
            this.hashArray[clampedIndex] = null; // define as  null (empty)
        this.numOfElements--;
        checkAndResize(DECREASE); //check if we need to resize the table after deleting. if yes - will resize
        return true;
    }

    /**
     * Overrides the toString method with toString of the inner collection
     * @return the String representation of this collection
     */
    public String toString(){
        return className;
    }
}