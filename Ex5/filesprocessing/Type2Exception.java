package filesprocessing;

/** Represents a type2 exception - should be thrown for one of these cases: 1.A bad sub-section name
 * 2. Bad format of the commends file */

public class Type2Exception extends Exception {
    private static final long serialVersionUID = 1L;

    /*----= Constructor =-----*/

    /**
     * Constructs a new Type2Exception
     * @param message The informative message of the exception
     */

    public Type2Exception(String message){
        super(message);
    }
}