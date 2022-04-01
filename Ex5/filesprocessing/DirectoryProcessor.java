package filesprocessing;
import filesprocessing.filters.AllFilter;
import filesprocessing.filters.Filter;
import filesprocessing.filters.FilterFactory;
import filesprocessing.filecomparators.AbsComparator;
import filesprocessing.filecomparators.FileComparatorFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** This class represents a directory processor - will filter the files in a given directory according to
    various conditions, and order the filenames that passed the filtering according to various properties. */

public class DirectoryProcessor {

    /**
     * The directory to filter files from
     */
    public String sourcedir;

    /**
     * The text file that contains commands about filtering and ordering the files in sourcedir
     */
    public String commandFile;

    /**
     * A list of the files from sourcedir
     */
    private List<File> filesList = new ArrayList<>();

    /**
     * Parses the command file
     */
    private Parser parser;

    /**
     * Array of the lines in the command file
     */
    private String[] commandFileArray;

    /**Represents reverse*/
    private static final String REVERSE = "REVERSE";

    /**Represents square */
    private static final String SQUARE = "#";

    /**The message that should be printed when type1 exception occurs */
    private static final String WARNING = "Warning in line ";

    /** The message that should be printed when num of args is invalid */
    private static final String ARGS_NUM_ERROR_MESSAGE = "ERROR: number of arguments invalid";


    public DirectoryProcessor(String directory, String commandFile) throws IOException {
        this.sourcedir = directory;
        this.commandFile = commandFile;
        this.createFilesList();
        this.commandFileArray = putCommandFileInArray();
        this.parser = new Parser(commandFileArray);
    }

    /**
     * Reads the command file lines into a String array
     * @return a String array of the lines in the command file
     */
    private String[] putCommandFileInArray() throws IOException {
        List<String> allLines = Files.readAllLines(Paths.get(this.commandFile));
        String[] arrayLines = allLines.toArray(new String[0]);
        return arrayLines;
    }

    /**
     * This method creates a list of files from source directory
     */

    private void createFilesList() {
        File dir = new File(this.sourcedir);
        File[] files = dir.listFiles();
        for (File file : files)
            if (file.isFile()) // make sure it is a file (and not a directory)
                this.filesList.add(file);
    }

    /**
     * This method checks validation of format of the Commands File
     * @throws Type2Exception
     */

    private void checkValidFormat() throws Type2Exception {
        while (!this.parser.getDoneParsing())
            this.parser.parseSectionFormat();
    }

    /**
     * This method processes the directory according to commands file
     * @throws IOException
     */

    public void process() throws Type2Exception {
        if (commandFileArray.length == 0) // empty commands file
            return;
        this.checkValidFormat(); // first, check command file format
        this.parser.restart(); // go back to first section

        while (!this.parser.getDoneParsing()) { // if we got here - format is valid
            boolean filterExc = false;
            int currFilterLine = this.parser.parseNextSection();
            Filter filter = null;
            try {
                String[] filterConditionArray = this.parser.getFilterCondition().split(SQUARE);
                filter = FilterFactory.createFilter(filterConditionArray[0], filterConditionArray);
                if (filter == null) // bad name for FILTER
                    filterExc = true;
            } catch (Type1Exception e) {
                filterExc = true;
            }

            if (filterExc) {
                System.err.println(WARNING + (currFilterLine + 2));
                String[] defaultFilterArray = new String[1];
                defaultFilterArray[0] = "all";
                try {
                    filter = new AllFilter(defaultFilterArray); // default filter
                } catch (Type1Exception e) {
                    // not supposed to happen
                }
            }

            List<File> filteredFiles = filter.doFilter(filesList);
            boolean orderExc = false;
            boolean isReversed = false;
            String[] orderConditionArray = this.parser.getOrderCondition().split(SQUARE);
            if (orderConditionArray[orderConditionArray.length - 1].equals(REVERSE)) {
                isReversed = true;
                if (orderConditionArray.length != 2)
                    orderExc = true; // means REVERSE is the only word, or there are more than 2 arguments
            } else if (orderConditionArray.length != 1)
                orderExc = true; // means suffix is not REVERSE

            Comparator<File> comparator = FileComparatorFactory.createFileComparator(orderConditionArray[0],
                    isReversed);

            if (orderExc || comparator == null) {
                comparator = new AbsComparator(false);
                System.err.println(WARNING + (currFilterLine + 4));
            }

            Order order = new Order(filteredFiles, comparator);
            List<File> orderedFiles = order.sort();
            for (File file : orderedFiles) {
                System.out.println(file.getName());
            }
        }
    }

    public static void main(String[] args) {
        try {
            if (args.length != 2)
                throw new Type2Exception(ARGS_NUM_ERROR_MESSAGE);
            DirectoryProcessor directoryProcessor = new DirectoryProcessor(args[0], args[1]);
            directoryProcessor.process();
        }

        catch (Type2Exception | IOException e) {
            System.err.println(e.getMessage());
        }
    }
}