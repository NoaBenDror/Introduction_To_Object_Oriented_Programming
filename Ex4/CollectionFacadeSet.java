import java.util.Collection;

/**
 * Wraps an underlying Collection and serves to both simplify its API and give it a common type
 * with the implemented SimpleHashSets.
 */

public class CollectionFacadeSet implements SimpleSet {

    /** The collection that is wrapped by this class */
    protected java.util.Collection<String> collection;

    /*----= Constructor =-----*/

    /**
     * Creates a new facade wrapping the specified collection.
     * @param collection The Collection to wrap.
     */

    public CollectionFacadeSet(Collection<String> collection){
        this.collection = collection;
    }

    /*----= Instance Methods =-----*/

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(String newValue){
        if (!this.collection.contains(newValue))
            return this.collection.add(newValue);
        return false;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */

    public boolean contains(String searchVal){
        return this.collection.contains(searchVal);
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */

    public boolean delete(String toDelete){
        return this.collection.remove(toDelete);
    }

    /**
     * @return The number of elements currently in the set
     */

    public int size(){
        return this.collection.size();
    }

    /**
     * Overrides the toString method with toString of the inner collection
     * @return the String representation of this collection
     */
    public String toString(){
        return this.collection.getClass().toString();
    }
}