import oop.ex3.searchengine.Hotel;
import java.util.Comparator;

/**
 * This class implements Comparator of Hotel type. It compares between hotels according to star-rating.
 */

public class RatingComparator implements Comparator<Hotel> {

    /** One */
    private static final int ONE = 1;

    /** Minus one */
    private static final int MINUS_ONE = -1;

    @Override
    /**
     * This method compares between star-rating of 2 hotels.
     * @param hotel1 hotel number 1 for comparison
     * @param hotel2 hotel number 2 for comparison
     * @return if star-rating of hotel2 is higher than star-rating of hotel1 - return 1. if star-rating
     * of hotel1 is higher than star-rating of hotel2 - return -1. otherwise, compares between the
     * alphabet order of the hotel’s (property) name, and return 0.
     */

    public int compare(Hotel hotel1, Hotel hotel2){
        if (hotel1.getStarRating() < hotel2.getStarRating())
            return ONE;
        if (hotel1.getStarRating() > hotel2.getStarRating())
            return MINUS_ONE;
        return hotel1.getPropertyName().compareTo(hotel2.getPropertyName());
    }
}
