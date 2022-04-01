/**
 * A superclass for implementations of hash-sets implementing the SimpleSet interface.
 */

public abstract class SimpleHashSet implements SimpleSet {

    /** Describes the higher load factor of a newly created hash set */
    protected static final float DEFAULT_HIGHER_CAPACITY = 0.75f;

    /** Describes the lower load factor of a newly created hash set */
    protected static final float DEFAULT_LOWER_CAPACITY = 0.25f;

    /** Describes the capacity of a newly created hash set */
    protected static final int INITIAL_CAPACITY = 16;

    /** The upper load factor of the hash table */
    protected float upperLoadFactor;

    /** The lower load factor of the hash table */
    protected float lowerLoadFactor;

    /** The current capacity (number of cells) of the table */
    protected int currCapacity;

    /** The number of elements currently in the set */
    protected int numOfElements = 0;

    /** The table should increase its size by this number */
    protected static final double INCREASE = 2;

    /** The table should decrease its size by this number */
    protected static final double DECREASE = 0.5;

    /** The capacity can't go below this number */
    protected static final int ONE = 1;

    /** The number of elements we are trying to add (meant to help check if we need to resize)*/
    protected static final int NUM_OF_ADDING = 1;

    /*----= Constructors =-----*/

    /**
     * Constructs a new hash set with the default capacities given in DEFAULT_LOWER_CAPACITY
     * and DEFAULT_HIGHER_CAPACITY
     */

    public SimpleHashSet(){
        this(DEFAULT_HIGHER_CAPACITY, DEFAULT_LOWER_CAPACITY);
    }

    /**
     * Constructs a new hash set with capacity INITIAL_CAPACITY
     * @param upperLoadFactor the upper load factor before rehashing
     * @param lowerLoadFactor the lower load factor before rehashing
     */

    public SimpleHashSet(float upperLoadFactor, float lowerLoadFactor){
        this.upperLoadFactor = upperLoadFactor;
        this.lowerLoadFactor = lowerLoadFactor;
        this.currCapacity = INITIAL_CAPACITY;
    }

    /*----= Instance Methods =-----*/

    /**
     * Checks if we need to re-size the hashtable (if we passed the lower/upper load factors)
     */

    protected void checkAndResize(double changeSize){
        if (changeSize == INCREASE && (float)(numOfElements+NUM_OF_ADDING)/currCapacity > upperLoadFactor)
            resize(INCREASE);

        if (changeSize == DECREASE && (float)numOfElements/currCapacity < lowerLoadFactor)
            resize(DECREASE);
    }

    /**
     * Resizes and rehashes this hash set
     * @param changeSize Represents whether we need to decrease or increase the table size
     */

    protected abstract void resize(double changeSize);

    /**
     * @return The current capacity (number of cells) of the table.
     */

    public int capacity(){
        return this.currCapacity;
    }

    /**
     * Clamps hashing indices to fit within the current table capacity
     * @param index the index before clamping
     * @return an index properly clamped
     */
    protected int clamp(int index){
        return index&(this.currCapacity-ONE);
    }

    /**
     * @return The lower load factor of the table.
     */

    protected float getLowerLoadFactor(){
        return lowerLoadFactor;
    }

    /**
     * @return The higher load factor of the table.
     */

    protected float getUpperLoadFactor(){
        return upperLoadFactor;
    }

    /**
     * @return The number of elements currently in the set
     */

    public int size(){
        return this.numOfElements;
    }
}
