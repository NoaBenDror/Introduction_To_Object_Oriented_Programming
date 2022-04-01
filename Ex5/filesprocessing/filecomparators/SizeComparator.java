package filesprocessing.filecomparators;
import java.io.File;
import java.util.Comparator;

/** This class represents a file comparator by file size (smallest to largest).
 *  It implements Comparator<File> */

public class SizeComparator implements Comparator<File> {

    /** Represents whether the comparator should be reversed (according to given input)*/
    private boolean isReversed;

    /** Used when need to reverse */
    private static final int MINUS_ONE = -1;

    /** Means file1 size is smaller than file2 size */
    private static final int POS = 1;

    /** Means file2 size is smaller than file1 size */
    private static final int NEG = -1;


    /*----= Constructor =-----*/

    public SizeComparator(boolean isReversed){
        this.isReversed = isReversed;
    }

    /**
     * This method compares between file sizes of two given files
     * @param file1 first file to compare
     * @param file2 second file to compare
     * @return
     */
    public int compare(File file1, File file2){
        int result;
        long file1Size = file1.length();
        long file2Size = file2.length();
        if (file1Size > file2Size)
            result = POS;
        else if (file1Size < file2Size)
            result = NEG;
        else // same size , order by abs
            result = file1.getName().compareTo(file2.getName());

        if (isReversed)
            result = result*MINUS_ONE; // if reversed - switch the order

        return result;
    }
}