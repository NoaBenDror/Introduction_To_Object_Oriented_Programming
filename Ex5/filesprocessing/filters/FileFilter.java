package filesprocessing.filters;
import java.io.File;

/** This class represents a filter that checks if given value equals the file name (excluding path).
 *  extends Filter */

public class FileFilter extends Filter {

    /** The string to check file name equals to */
    private String strToCheck;

    public FileFilter(String[] conditionArray){
        super(conditionArray);
        this.parseCondition();
    }

    /**
     * Parses the conditionArray
     */
    private void parseCondition(){
        this.strToCheck = this.conditionArray[1];
    }

    /**
     * Checks if a given file passes this filter or not
     * @param file The file to check if passed
     * @return true if the file passes this filter
     */

    public boolean isPassed(File file){
        boolean result = false;
        if (this.strToCheck.equals(file.getName()))
            result = true;
        if (super.negative(this.conditionArray[this.conditionArray.length-1]))
            result = !result;
        return result;
    }
}