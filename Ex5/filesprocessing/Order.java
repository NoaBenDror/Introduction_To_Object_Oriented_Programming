package filesprocessing;
import java.io.File;
import java.util.Comparator;
import java.util.List;

/** This class represents an order condition to sort given files by */

public class Order {

    /** A list of files to order */
    List<File> filteredFiles;

    /** The comparator the files should be sorted by */
    Comparator<File> fileComparator;

    /*----= Constructor =-----*/

    /**
     * Creates a new Order
     * @param filteredFiles A list of files to order
     * @param fileComparator The comparator the files should be sorted by
     */

    public Order(List<File> filteredFiles, Comparator<File> fileComparator) {
        this.filteredFiles = filteredFiles;
        this.fileComparator = fileComparator;
    }

    /**
     * Sorts the files
     * @return List<File> of the ordered files (after being sorted)
     */

    public List<File> sort(){
        return sortFiles(this.filteredFiles);
    }

    /**
     * Helper method - sorts the files
     * @param list List of files
     * @return
     */

    private List<File> sortFiles(List<File> list) {
        sortFiles(list, 0, list.size() - 1);
        return this.filteredFiles; // after being sorted
    }

    /**
     * sorts the files recursively
     * @param list List of files
     * @param from
     * @param to
     */

    private void sortFiles(List<File> list, int from, int to) {
        if (from < to) {
            int pivot = from;
            int left = from + 1;
            int right = to;
            File pivotValue = list.get(pivot);
            while (left <= right) {
                while (left <= to && this.fileComparator.compare(pivotValue,list.get(left)) >=0){
                    left++;
                }
                while (right > from && this.fileComparator.compare(pivotValue, list.get(right)) < 0){
                    right--;
                }
                if (left < right) {
                    this.swap(list, left, right);
                }
            }
            this.swap(list, pivot, left - 1);
            sortFiles(list, from, right - 1);
            sortFiles(list, right + 1, to);
        }
    }

    /**
     * Swaps between two files in the list
     * @param filteredFiles The list of files
     * @param a first index
     * @param b second index
     * @return
     * */

    private List<File> swap(List<File> filteredFiles, int a, int b) {
        File temp = filteredFiles.get(a);
        this.filteredFiles.set(a, filteredFiles.get(b));
        this.filteredFiles.set(b, temp);
        return filteredFiles;
    }
}