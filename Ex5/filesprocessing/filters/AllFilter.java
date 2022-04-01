package filesprocessing.filters;
import filesprocessing.Type1Exception;
import java.io.File;

/** This class represents a filter where all files are matched or all files are unmatched. extends filter */

public class AllFilter extends Filter {

    /** The suffix of the condition */
    private String suffix;

    /** Represents NOT suffix*/
    private static final String NOT = "NOT";

    /*----= Constructor =-----*/

    public AllFilter(String[] conditionArray) throws Type1Exception {
        super(conditionArray);
        this.parseCondition();
    }

    /**
     * Parses the conditionArray
     * @throws Type1Exception
     */

    private void parseCondition() throws Type1Exception{
        this.suffix = this.conditionArray[conditionArray.length-1];
        if (this.conditionArray.length == 2)
            if (!suffix.equals(NOT))
                throw new Type1Exception();
    }

    /**
     * Checks if a given file passes this filter or not
     * @param file The file to check if passed
     * @return true if the file passes this filter
     */

    public boolean isPassed(File file){
        if (super.negative(this.suffix))
            return false;
        return true;
    }
}