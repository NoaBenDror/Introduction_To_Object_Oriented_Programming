import oop.ex3.searchengine.Hotel;
import java.util.Comparator;

/**
 *  * This class implements Comparator of Hotel type. It compares between hotels according to proximity.
 */

public class ProximityComparator implements Comparator<Hotel> {

    /** The latitude of given location */
    private double latitude;

    /** The longitude of given location */
    private double longitude;

    /** Zero */
    private static final int ZERO = 0;

    /** One */
    private static final int ONE = 1;

    /** Minus one */
    private static final int MINUS_ONE = -1;


    public ProximityComparator(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    /**
     * This method compares between proximity of 2 hotels to given given geographic location.
     * @param hotel1 hotel number 1 for comparison
     * @param hotel2 hotel number 2 for comparison
     * @return if the proximity of hotel1 to given point is lower than proximity of hotel2 to given location-
     * return -1. if the proximity of hotel2 to given location is lower than the proximity of hotel1 to
     * given location - return 1. otherwise, compares between points-of-interest, and return 0.
     */

    public int compare(Hotel hotel1, Hotel hotel2){
        double calculate1 = Math.sqrt(Math.pow(hotel1.getLatitude()-this.latitude, 2)
                + Math.pow(hotel1.getLongitude()-this.longitude,2));
        double calculate2 = Math.sqrt(Math.pow(hotel2.getLatitude()-this.latitude, 2)
                + Math.pow(hotel2.getLongitude()-this.longitude,2));
        if (calculate1 < calculate2)
            return MINUS_ONE;
        if (calculate1 > calculate2)
            return ONE;

        // if we got here - both have the same distance - compare POI
        if (hotel1.getNumPOI() < hotel2.getNumPOI())
            return ONE;
        if (hotel1.getNumPOI() > hotel2.getNumPOI())
            return MINUS_ONE;
        return ZERO; // both have the same POI
    }
}