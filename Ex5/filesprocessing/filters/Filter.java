package filesprocessing.filters;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/** This abstract class represents a filter of files according to some criteria. */

public abstract class Filter {

    /** Array of Strings representing the filtering conditions */
    public final String[] conditionArray;

    /** Represents whether there is NOT suffix*/
    private static final String NOT = "NOT";


    public Filter(String[] conditionArray){
        this.conditionArray = conditionArray;
    }

    /**
     * Filters a given ArrayList of files, according to given instructions
     * @param filesList ArrayList of files
     * @return
     */
    public List<File> doFilter(List<File> filesList){
        List<File> filteredList = new ArrayList<>();
        for (File file: filesList){ // traverse the files
            if (isPassed(file)) // check if passed
                filteredList.add(file); // if yes - add to filtered list
        }
        return filteredList;
    }

    /**
     * Abstract method, checks if a given file passes this filter or not
     * @param file The file to check if passed
     * @return true if the file passes this filter
     */

    public abstract boolean isPassed(File file);

    /**
     * Checks if the last suffix equals "NOT"
     * @param suffix Suffix of the instructions array
     * @return true if the suffix equals "NOT"
     */

    public boolean negative(String suffix){
        return suffix.equals(NOT);
    }
}