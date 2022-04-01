package filesprocessing;


public class Parser {

    /** This class parses a given String array. It knows the logical order of the commands,
     * and divides them into sections */

    /** A string array of the lines in the command file */
    public String[] lines;

    /** Represents the string of the filter condition */
    private String filterCondition;

    /** Represents the string of the order condition */
    private String orderCondition;

    /** Represents the current line in the command file*/
    private int currLine = 0;

    /** Represents whether we are done parsing */
    private boolean doneParsing = false;

    /** Represents first sub-section of new section */
    private static final String FILTER = "FILTER";

    /** Represents second sub-section of new section */
    private static final String ORDER = "ORDER";

    /**Represents default order condition */
    private static final String ABS ="abs";

    /**The message of order error */
    private static final String ORDER_ERROR_MESSAGE = "ERROR: ORDER sub-section missing";

    /** The message of order error */
    private static final String FILTER_ERROR_MESSAGE = "ERROR: FILTER sub-section missing";


    /*----= Constructor =-----*/

    public Parser(String[] lines){
        this.lines = lines;
    }

    /**
     * This method parses the array of lines, and checks if the format is valid
     * @throws Type2Exception
     */

    public void parseSectionFormat() throws Type2Exception{

        if (this.currLine+2 > this.lines.length-1)
            throw new Type2Exception(ORDER_ERROR_MESSAGE);

        if (!this.lines[currLine].equals(FILTER)) // type 2 exception, no FILTER line
            throw new Type2Exception(FILTER_ERROR_MESSAGE);

        if (!this.lines[currLine +2].equals(ORDER)) // type 2 exception, no ORDER line
            throw new Type2Exception(ORDER_ERROR_MESSAGE);

        if (currLine +2 == this.lines.length-1) { // ORDER line is the last line in command file
            this.doneParsing = true;
            return;
        }

        // order condition is the last line in command file, or FILTER followed right after ORDER
        if (this.currLine+3 == this.lines.length-1 && !this.lines[this.currLine+3].equals(FILTER)){
            this.doneParsing = true;
            return;
        }

        if (this.lines[currLine +3].equals(FILTER)) // empty ORDER
            this.currLine = currLine +3;
        else // non-empty ORDER
            this.currLine = currLine +4;
    }

    /**
     * This method parses the next section, and sets the matching conditions (assuming structure is legal)
     */
    public int parseNextSection(){
        int currFilterLine = this.currLine;

        this.filterCondition = this.lines[currLine +1]; // filter condition

        if (currLine +2 == this.lines.length-1) { // this is the last line in command file
            this.orderCondition = ABS; // default order condition
            this.doneParsing = true;
            return currFilterLine;
        }

        if (this.lines[currLine +3].equals(FILTER)) { // empty ORDER, beginning of new section
            this.orderCondition = ABS ; // default order condition
            this.currLine = currLine +3;
            return currFilterLine;
        }

        this.orderCondition = this.lines[currLine +3];  // non-empty ORDER

        if (this.currLine +3 == this.lines.length-1) { // this is the last line in command file
            this.doneParsing = true;
            return currFilterLine;
        }

        this.currLine = currLine +4; // next section
        return currFilterLine;
    }

    /**
     * @return true if done parsing, false otherwise
     */
    public boolean getDoneParsing(){
        return this.doneParsing;
    }

    /**
     * @return the filter condition
     */
    public String getFilterCondition(){
        return this.filterCondition;
    }

    /**
     * @return the order condition
     */

    public String getOrderCondition(){
        return this.orderCondition;
    }

    /**
     * This method restarts the parser
     */

    public void restart(){
        this.currLine = 0;
        this.doneParsing = false;
    }
}