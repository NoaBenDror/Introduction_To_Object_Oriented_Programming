/**
 * This class compares the performances (measures runtime) of the following data structure: OpenHashSet,
 * ClosedHashSet, Java's TreeSet, Java's LinkedList, Java's HashSet.
 */

import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

public class SimpleSetPerformanceAnalyzer {


    /** The data structure we keep the sets in */
    private SimpleSet[] setsArray = new SimpleSet[NUM_OF_SETS];

    /** This array will contain the words from input data file */
    private String[] dataArray;

    /** Represents the number we need to divide in to translate nanoseconds to milliseconds */
    private final static int MAKE_MILLISECONDS = 1000000;

    /** Represents the number of sets we are testing */
    private final static int NUM_OF_SETS = 5;

    /** Represents the name of the file */
    private final String fileName;

    /** The number of iterations to warm up each set, besides LinkedList */
    private static final int WARMUP_SETS = 70000;

    /** The number of iterations to warm up LinkedList */
    private static final int WARMUP_LINKEDLIST = 7000;

    /** A String value to check*/
    private static final String HI = "hi";

    /** A String value to check*/
    private static final String TWENTY_THREE = "23";

    /** A String value to check*/
    private static final String NEGATIVE_NUM = "-13170890158";

    /*----= Constructor =-----*/
    /**
     * Constructs a new analyzer (array of new SimpleSet implementations) that gets as input a file
     * name to read from.
     */

    public SimpleSetPerformanceAnalyzer(String dataFileName){
        this.setsArray[0] = new OpenHashSet();
        this.setsArray[1] = new ClosedHashSet();
        this.setsArray[2] = new CollectionFacadeSet(new TreeSet<>());
        this.setsArray[3] = new CollectionFacadeSet(new LinkedList<>());
        this.setsArray[4] = new CollectionFacadeSet(new HashSet<>());
        this.fileName = dataFileName;
        dataArray = Ex4Utils.file2array(dataFileName); // an array containing the words in dataFileName
    }

    /**
     * This method analyzes performance of adding all String elements from the file to each one of the sets
     */
    public void addToSets() {
        System.out.println("Adding test for file: " + this.fileName);
        for (int i = 0; i < setsArray.length; i++) {
            long timeBefore = System.nanoTime();
            for (int j = 0; j < dataArray.length; j++) {
                setsArray[i].add(dataArray[j]);
            }
            long difference = System.nanoTime() - timeBefore;
            System.out.println("Data structure: "+ setsArray[i].toString());
            System.out.println("Time in milliseconds: " + difference/MAKE_MILLISECONDS);
            System.out.println();
        }
    }

    /**
     * This method analyzes performance of checking if each set contains a given input
     */

    public void analyzeContains(String valToCheck){
        System.out.println("Contains '" + valToCheck + "' test for file: " + this.fileName);
        for (int i = 0; i < setsArray.length; i++) {
            if (i!=3)
                analyzeSetContainsWithWarmUp(valToCheck, setsArray[i]);
            else // LinkedList
                linkedListSetContains(valToCheck, setsArray[i]);
        }
    }

    /**
     * This method analyzes performance of given SimpleSet, checking if contains given input
     * @param valToCheck The String val to check
     * @param curSet The set we are looking in
     */

    public void analyzeSetContainsWithWarmUp(String valToCheck, SimpleSet curSet){
        for (int j=0; j<WARMUP_SETS; j++)
            curSet.contains(valToCheck);
        long timeBefore = System.nanoTime();
        for (int j=0; j<WARMUP_SETS; j++)
            curSet.contains(valToCheck);

        long difference = System.nanoTime() - timeBefore;
        System.out.println("Data structure: "+ curSet.toString());
        System.out.println("Time in nanoseconds: " + difference/WARMUP_SETS);
        System.out.println();
    }

    /**
     * This method analyzes performance of LinkedList, checking if contains given input
     * @param valToCheck The String val to check
     */

    public void linkedListSetContains(String valToCheck, SimpleSet curLInkedList) {
        long timeBefore = System.nanoTime();
        for (int j = 0; j < WARMUP_LINKEDLIST; j++)
            curLInkedList.contains(valToCheck);

        long difference = System.nanoTime() - timeBefore;
        System.out.println("Data structure: " + curLInkedList.toString());
        System.out.println("Time in nanoseconds: " + difference/WARMUP_LINKEDLIST);
        System.out.println();
    }

    /**
     * The main method that runs the analysis.
     */

    public static void main(String[] args){
        SimpleSetPerformanceAnalyzer analyzer1 = new SimpleSetPerformanceAnalyzer("src\\data1.txt");
        SimpleSetPerformanceAnalyzer analyzer2 = new SimpleSetPerformanceAnalyzer("src\\data2.txt");
        analyzer1.addToSets();
        analyzer2.addToSets();
        analyzer1.analyzeContains(HI);
        analyzer2.analyzeContains(HI);
        analyzer2.analyzeContains(TWENTY_THREE);
        analyzer1.analyzeContains(NEGATIVE_NUM);
    }
}