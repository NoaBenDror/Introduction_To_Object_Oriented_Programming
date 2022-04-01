package filesprocessing.filters;
import filesprocessing.Type1Exception;
import java.io.File;

/** This class represents a filter that checks if file size is between (inclusive) the given numbers
 *  (in k-bytes) */

public class BetweenFilter extends Filter {

    /** number to check if file size is larger than */
    private Double firstNum;

    /** number to check if file size is smaller than */
    private Double secondNum;

    /** Conversion between bytes and k-bytes */
    private static final int B_TO_KB = 1024;


    public BetweenFilter(String[] conditionArray) throws Type1Exception {
        super(conditionArray);
        this.parseCondition();
    }

    /**
     * Parses the conditionArray
     * @throws Type1Exception
     */

    private void parseCondition() throws Type1Exception{
        this.firstNum = Double.parseDouble(this.conditionArray[1]);
        this.secondNum = Double.parseDouble(this.conditionArray[2]);
        if (firstNum > secondNum || firstNum < 0 || secondNum < 0)
            throw new Type1Exception();
    }

    /**
     * Checks if a given file passes this filter or not
     * @param file The file to check if passed
     * @return true if the file passes this filter
     */

    public boolean isPassed(File file){
        double sizeInKB = file.length()/(double)B_TO_KB;
        boolean result = false;
        if (sizeInKB >= this.firstNum &&
                sizeInKB <= this.secondNum)
            result = true;
        if (super.negative(this.conditionArray[this.conditionArray.length-1]))
            result = !result;
        return result;
    }
}