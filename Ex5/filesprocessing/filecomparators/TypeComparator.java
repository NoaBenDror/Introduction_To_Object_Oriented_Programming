package filesprocessing.filecomparators;
import java.io.File;
import java.util.Comparator;

/** This class represents a file comparator by file type (from ’a’ to ’z’).
 *  It implements Comparator<File> */

public class TypeComparator implements Comparator<File> {

    /** Represents whether the comparator should be reversed (according to given input) */
    private boolean isReversed;

    /** Used when need to reverse */
    private static final int MINUS_ONE = -1;

    /** Used to check if two file types are equal */
    private static final int ZERO = 0;

    /** Represents an empty string*/
    private static final String EMPTY_STRING = "";

    /** Represents a period string */
    private static final String PERIOD = ".";

    /*----= Constructor =-----*/

    public TypeComparator(boolean isReversed){
        this.isReversed = isReversed;
    }

    private String getFileType (File file){
        String fileType;
        String[] fileNoDots = file.getName().split("\\.");
        if (fileNoDots.length == 1) // means the file name has 1 period at the end; or 1 period at the
            // beginning; or no periods at all
            fileType = EMPTY_STRING; // file’s type is the empty string
        else if (file.getName().endsWith(PERIOD)) // file name has more than 1 period, but ends with a period
            fileType = EMPTY_STRING;
        else // take the String after last period
            fileType = fileNoDots[fileNoDots.length-1];
        return fileType;
    }

    /**
     * This method compares between file types of two given files
     * @param file1 first file to compare
     * @param file2 second file to compare
     * @return
     */
    public int compare(File file1, File file2){
        String file1Type = this.getFileType(file1);
        String file2Type = this.getFileType(file2);
        int result = file1Type.compareTo(file2Type);

        if (result == ZERO) // same type, order by abs
            result = file1.getName().compareTo(file2.getName());

        if (isReversed)
            result = result*MINUS_ONE; // if reversed - switch the order
        return result;
    }
}