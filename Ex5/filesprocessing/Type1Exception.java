package filesprocessing;

/** Represents a type2 exception - should be thrown for one of these cases: 1.A bad FILTER/ORDER name
 * 2. Bad parameters to the filters */


public class Type1Exception extends Exception {
    private static final long serialVersionUID = 1L;

    /*----= Constructor =-----*/

    /**
     * Constructs a new Type1Exception
     */

    public Type1Exception(){
        super();
    }
}
