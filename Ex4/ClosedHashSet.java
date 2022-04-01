/**
 * This class represents a hash-set based on closed-hashing with quadratic probing. Extends SimpleHashSet
 */

public class ClosedHashSet extends SimpleHashSet {

    /** The data structure the elements are stored in */
    private StringHolder[] hashArray;

    /** Represents the fact that a specific String is not in the hash set */
    private final static int NOT_FOUND = -1;

    /** The string representation of the name of this class*/
    private final static  String className = "ClosedHashSet";

    /*----= Constructors =-----*/

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public ClosedHashSet(){
        this(DEFAULT_HIGHER_CAPACITY, DEFAULT_LOWER_CAPACITY);
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16)
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */

    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor){
        super(upperLoadFactor, lowerLoadFactor);
        hashArray = new StringHolder[INITIAL_CAPACITY];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values
     * should be ignored. The new table has the default values of initial capacity (16), upper load
     * factor (0.75), and lower load factor (0.25).
     * @param data Values to add to the set.
     */

    public ClosedHashSet(java.lang.String[] data){
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
        if (!contains(newValue)){
            checkAndResize(INCREASE); //check if we need to resize the table before adding.if yes-will resize
            for (int i=0; i<this.currCapacity; i++){ // loop until empty cell is found
                int clampedIndex = clamp((newValue.hashCode())+(i+(i*i))/2);
                if (hashArray[clampedIndex] == null || hashArray[clampedIndex].getVal() == null){
                    hashArray[clampedIndex] = new StringHolder(newValue);
                    this.numOfElements++;
                    break; // found an empty cell to get in
                }
            }
            return true;  // adding was successful
        }
        return false; // newValue already exists in the set
    }


    /**
     * Resizes and rehashes this hash set
     * @param changeSize Represents whether we need to decrease or increase the table size
     */

    protected void resize(double changeSize){
        StringHolder[] tempHashArray = hashArray; // temporary array that holds original array
        int newCapacity = (int)(this.currCapacity*changeSize);
        if (newCapacity < ONE) // might happen only when deleting
            return; // no need to resize
        this.currCapacity = newCapacity;
        this.hashArray = new StringHolder[this.currCapacity];
        this.numOfElements = 0; // will be taken care of in "add" method
        for (int i=0; i<tempHashArray.length;i++) // traverse the original array
            if (tempHashArray[i]!=null && tempHashArray[i].getVal()!=null)
                this.add(tempHashArray[i].getVal()); // add this element to the new array
        }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */

    public boolean contains(java.lang.String searchVal){
        if (findInTable(searchVal) == NOT_FOUND)
            return false;
        return true;
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */

    public boolean delete(java.lang.String toDelete){
        if (!contains(toDelete))
            return false; // toDelete not found
        int indexOfVal = findInTable(toDelete);
        // define deleted cell as a StringHolder type while its val equals null
        hashArray[indexOfVal] = new StringHolder(null);
        this.numOfElements--;
        checkAndResize(DECREASE); // check if we need to resize the table after deleting.if yes- will resize
        return true;
    }

    /**
     * Finds the index of the input element in the hashArray, if it exists.
     * @param valToFind
     * @return the index of valToFind in the hashArray. if valToFind not found in hashArray- return -1
     */

    private int findInTable(java.lang.String valToFind) {
        for (int i = 0; i < this.currCapacity; i++) {
            int clampedIndex = clamp((valToFind.hashCode()) + (i + (i * i)) / 2);
            if (this.hashArray[clampedIndex] == null) // valToFind not in hashArray
                return NOT_FOUND;
            if (this.hashArray[clampedIndex].getVal()!=null)
                if (this.hashArray[clampedIndex].getVal().equals(valToFind)) // found the value
                    return clampedIndex;
        }
        return NOT_FOUND; // normally we won't get here
    }

    /**
     * Overrides the toString method with toString of the inner collection
     * @return the String representation of this collection
     */
    public String toString(){
        return className;
    }
}