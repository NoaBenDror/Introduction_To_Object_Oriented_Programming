package filesprocessing.filters;
import filesprocessing.Type1Exception;

import java.io.File;

/** This class represents a filter that checks if files have or don't have execution permission.
 *  extends filter */

public class ExecutableFilter extends Filter {

    /** Represents the given parameter to the filter */
    private String yesOrNo;

    public ExecutableFilter(String[] conditionArray) throws Type1Exception {
        super(conditionArray);
        this.parseCondition();
    }

    /**
     * Parses the conditionArray
     * @throws Type1Exception
     */
    private void parseCondition() throws Type1Exception{
        if (this.conditionArray.length<2)
            throw new Type1Exception();
        yesOrNo = this.conditionArray[1];
        if (!(yesOrNo.equals("YES") || yesOrNo.equals("NO")))
            throw new Type1Exception();
    }

    /**
     * Checks if a given file passes this filter or not
     * @param file The file to check if passed
     * @return true if the file passes this filter
     */

    public boolean isPassed(File file){
        boolean result = file.canExecute();
        if (this.yesOrNo.equals("NO"))
            result = !result;
        if (super.negative(this.conditionArray[this.conditionArray.length-1]))
            result = !result;
        return result;
    }
}