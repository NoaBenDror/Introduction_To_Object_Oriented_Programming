import oop.ex3.searchengine.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class represents a hotel booking site, that allows for personalized search methodologies.
 */

public class BoopingSite{

    /** An array of hotels */
    private Hotel[] hotelsArray;

    /** Range of latitude - negative */
    private final static int ILLEGAL_LATITUDE_NEG = -90;

    /** Range of latitude - positive */
    private final static int ILLEGAL_LATITUDE_POS = 90;

    /** Range of longitude - negative*/
    private final static int ILLEGAL_LONGITUDE_NEG = -180;

    /** Range of longitude - positive*/
    private final static int ILLEGAL_LONGITUDE_POS = 180;

    /*----= Constructor =-----*/

    /**
     * Initializes a Booping site.
     * @param name of the dataset
     */

    public BoopingSite(String name){
        this.hotelsArray = HotelDataset.getHotels(name);
    }

    /*----= Instance Methods =-----*/

    /**
     * Returns array of hotels located in the given city, sorted from the highest star-rating to the lowest.
     * Hotels that have the same rating will be organized according to the alphabet order of the hotel’s
     * (property) name. In case there are no hotels in the given city, this method returns an empty array.
     * @param city the city to look for hotels in
     * @return array of hotels located in the given city.
     */

    public Hotel[] getHotelsInCityByRating(String city){
        Hotel[] hotelsInCity = getHotelsInCity(city);
        Arrays.sort(hotelsInCity, new RatingComparator()); // sorts the array
        return hotelsInCity;
    }

    /**
     * Returns an ArrayList of hotels in the given city
     * @param city the city to look for hotels in
     * @return ArrayList of hotels in the given city
     */

    private Hotel[] getHotelsInCity(String city){
        ArrayList<Hotel> hotelsInCity = new ArrayList<Hotel>();
        for (int i=0; i<this.hotelsArray.length; i++){ // builds an ArrayList of hotels in the given city
            if (this.hotelsArray[i].getCity().equals(city))
                hotelsInCity.add(this.hotelsArray[i]);
        }
        Hotel[] newHotelsInCity = new Hotel[hotelsInCity.size()];
        for (int i=0; i<newHotelsInCity.length; i++){ // copy the ArrayList to an array
            newHotelsInCity[i] = hotelsInCity.get(i);
        }
        return newHotelsInCity;
    }

    /**
     * Returns an array of hotels, sorted according to their (euclidean) distance from the given geographic
     * location, in ascending order. Hotels that are at the same distance from the given location are
     * organized according to the number of points-of-interest for which they are close to
     * (in a decreasing order). In case of illegal input, this method returns an empty array.
     * @param latitude of given geographic location
     * @param longitude of given geographic location
     * @return an array of hotels.
     */

    public Hotel[] getHotelsByProximity(double latitude, double longitude){
        if (latitude<ILLEGAL_LATITUDE_NEG || latitude>ILLEGAL_LATITUDE_POS ||
                longitude<ILLEGAL_LONGITUDE_NEG || longitude>ILLEGAL_LONGITUDE_POS) // illegal input
            return new Hotel[0];

        Arrays.sort(this.hotelsArray, new ProximityComparator(latitude, longitude));
        return this.hotelsArray;
    }

    /**
     * Returns an array of hotels in the given city, sorted according to their (euclidean) distance
     * from the given geographic location, in ascending order. Hotels that are at the same distance from the
     * given location are organized according to the number of points-of-interest for which they are close to
     * (in a decreasing order). In case of illegal input, this method returns an empty array.
     * @param city the city to look for hotels in
     * @param latitude of given geographic location
     * @param longitude of given geographic location
     * @return an array of hotels.
     */

    public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude){
        if (latitude<ILLEGAL_LATITUDE_NEG || latitude>ILLEGAL_LATITUDE_POS ||
                longitude<ILLEGAL_LONGITUDE_NEG || longitude>ILLEGAL_LONGITUDE_POS) // illegal input
            return new Hotel[0];

        Hotel[] hotelsInCity = getHotelsInCity(city);
        Arrays.sort(hotelsInCity, new ProximityComparator(latitude, longitude));
        return hotelsInCity;
    }
}
