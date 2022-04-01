package filesprocessing.filecomparators;
import java.io.File;
import java.util.Comparator;

/** This class represents a file comparator factory. It is responsible for creating all file comparators */

public class FileComparatorFactory {

    /**
     * Creates a file comparator
     * @param fileComparatorName The name of the file comparator
     * @param isReversed whether the comparator should be reversed
     * @return the file comparator type that matches the given file comparator name
     */

    public static Comparator<File> createFileComparator(String fileComparatorName, boolean isReversed) {
        switch (fileComparatorName) {
            case "abs":
                return new AbsComparator(isReversed);
            case "type":
                return new TypeComparator(isReversed);
            case "size":
                return new SizeComparator(isReversed);
            default:
                return null;
        }
    }
}