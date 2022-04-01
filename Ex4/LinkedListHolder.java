import java.util.LinkedList;

/** This class holds a Linked List of String type. It is a wrapper-class that has a LinkedList<String>
 *  and delegates methods to it.
 */

public class LinkedListHolder {


    /** The linked list that is wrapped by this class */
    private LinkedList<String> linkedList;

    /*----= Constructors =-----*/

    /**
     * Constructs a new LinkedListHolder, with a new linked list of String type
     */

    public LinkedListHolder(){
        linkedList = new LinkedList<>();
    }

    /*----= Instance Methods =-----*/

    /**
     * @return the linked list that is wrapped by this class
     */
    public LinkedList<String> getLinkedList(){
        return linkedList;
    }
}