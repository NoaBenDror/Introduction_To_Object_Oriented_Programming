/** This class holds a String (meant to support delete operation in ClosedHashSet) */

public class StringHolder {

    /** The String value of this object */
    private String val;

    /*----= Constructors =-----*/

    /**
     * Constructs a new StringHolder element - a String value
     * @param val The String value of this object
     */

    public StringHolder(String val){
        this.val = val;
    }

    /*----= Instance Methods =-----*/

    /**
     * @return the String value
     */
    public String getVal(){
        return this.val;
    }
}