package filesprocessing.filecomparators;
import java.io.File;
import java.util.Comparator;

/** This class represents a file comparator by absolute path (from ’a’ to ’z’).
 *  It implements Comparator<File> */

public class AbsComparator implements Comparator<File> {

    /** Represents whether this comparator should be reversed (according to given input) */
    private boolean isReversed;

    /** Used when need to reverse */
    private static final int MINUS_ONE = -1;

    /*----= Constructor =-----*/

    public AbsComparator(boolean isReversed){
        this.isReversed = isReversed;
    }

    /**
     *  This method compares between absolute paths of two given files
     * @param file1 first file to compare
     * @param file2 second file to compare
     * @return
     */
    public int compare(File file1, File file2){
        int result = file1.getName().compareTo(file2.getName());
        if (isReversed)
            result = result*MINUS_ONE;  // if reversed - switch the order
        return result;
    }
}