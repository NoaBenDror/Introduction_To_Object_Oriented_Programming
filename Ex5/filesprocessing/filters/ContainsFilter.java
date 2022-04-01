package filesprocessing.filters;
import java.io.File;

/** This class represents a filter that checks if given value is contained in the file name
 *  (excluding path) */

public class ContainsFilter extends Filter {

    /** The string to check if is contained in the file name */
    private String strToCheck;

    public ContainsFilter(String[] conditionArray){
        super(conditionArray);
        this.parseCondition();
    }

    /**
     * Parses the conditionArray
     */
    private void parseCondition(){
        this.strToCheck = conditionArray[1];
    }

    /**
     * Checks if a given file passes this filter or not
     * @param file The file to check if passed
     * @return true if the file passes this filter
     */

    public boolean isPassed(File file){
        boolean result = false;
        if (file.getName().contains(this.strToCheck))
            result = true;
        if (super.negative(this.conditionArray[this.conditionArray.length-1]))
            result = !result;
        return result;
    }
}