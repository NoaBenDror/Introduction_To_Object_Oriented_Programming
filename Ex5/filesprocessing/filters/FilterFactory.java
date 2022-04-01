package filesprocessing.filters;
import filesprocessing.Type1Exception;

/** This class represents a filter factory. It is responsible for creating all filters. */

public class FilterFactory {

    /**
     * Creates a filter
     * @param filterName The name of the filter
     * @param conditionArray A string array of the condition
     * @return the filter type that matches the given filter name
     * @throws Type1Exception
     */

    public static Filter createFilter(String filterName, String[] conditionArray) throws Type1Exception{
        switch (filterName) {
            case "greater_than":
                return new GreaterThanFilter(conditionArray);
            case "between":
                return new BetweenFilter(conditionArray);
            case "smaller_than":
                return new SmallerThanFilter(conditionArray);
            case "file":
                return new FileFilter(conditionArray);
            case "contains":
                return new ContainsFilter(conditionArray);
            case "prefix":
                return new PrefixFilter(conditionArray);
            case "suffix":
                return new SuffixFilter(conditionArray);
            case "writable":
                return new WritableFilter(conditionArray);
            case "executable":
                return new ExecutableFilter(conditionArray);
            case "hidden":
                return new HiddenFilter(conditionArray);
            case "all":
                return new AllFilter(conditionArray);
            default:
                return null;
        }
    }
}