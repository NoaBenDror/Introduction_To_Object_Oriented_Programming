import org.junit.Test;
import oop.ex3.searchengine.*;
import static org.junit.Assert.*;


/**
 * This is a test class for BoopingSite class.
 */

public class BoopingSiteTest {

    /** The booping site we are testing */
    private BoopingSite booping;

    /*----= Constructor =-----*/

    public BoopingSiteTest(){
        booping = new BoopingSite("hotels_dataset.txt");
    }

    /*----= Instance Methods =-----*/

    /**
     * Ordinary case of a city that appears in the file.
     */

    @Test
    public void testHotelsInCityByRating1(){
        boolean didPass = true;
        Hotel[] hotelsInCityByRating = booping.getHotelsInCityByRating("manali");
        for(int i=0; i<hotelsInCityByRating.length-1; i++){
            if (hotelsInCityByRating[i].getStarRating() < hotelsInCityByRating[i+1].getStarRating())
                didPass = false;
            if (hotelsInCityByRating[i].getStarRating() == hotelsInCityByRating[i+1].getStarRating())
                if (hotelsInCityByRating[i].getPropertyName().compareTo(hotelsInCityByRating[i+1].
                        getPropertyName())>0)
                    didPass = false;
        }
        assertTrue(didPass);
    }

    /**
     * Case of a city that doesn't appear in the file. Checks if the returned array is empty.
     */

    @Test
    public void testHotelsInCityByRating2(){
        Hotel[] hotelsInCityByRating = booping.getHotelsInCityByRating("Ness Ziona");
        assertTrue(hotelsInCityByRating.length==0);
    }

    /**
     * Case of an empty String as input. Checks if the returned array is empty.
     */

    @Test
    public void testHotelsInCityByRating3(){
        Hotel[] hotelsInCityByRating = booping.getHotelsInCityByRating("");
        assertTrue(hotelsInCityByRating.length==0);
    }

    /**
     * Case of illegal inputs. Checks if the returned array is empty.
     */

    @Test
    public void testGetHotelsByProximity1(){
        Hotel[] hotelsByProximity1 = booping.getHotelsByProximity(-120, 50);
        assertTrue(hotelsByProximity1.length ==0);
        Hotel[] hotelsByProximity2 = booping.getHotelsByProximity(-50, 190);
        assertTrue(hotelsByProximity2.length ==0);
        Hotel[] hotelsByProximity3 = booping.getHotelsByProximity(0, -181);
        assertTrue(hotelsByProximity3.length ==0);
    }

    /**
     * Ordinary case, checking sorted array by proximity to given location
     */

    @Test
    public void testGetHotelsByProximity2(){
        boolean didPass = true;
        Hotel[] hotelsByProximity = booping.getHotelsByProximity(20,100);
        for(int i=0; i<hotelsByProximity.length-1; i++){
            double calculate1 = Math.sqrt(Math.pow(hotelsByProximity[i].getLatitude()-20, 2)
                    + Math.pow(hotelsByProximity[i].getLongitude()-100,2));
            double calculate2 = Math.sqrt(Math.pow(hotelsByProximity[i+1].getLatitude()-20, 2)
                    + Math.pow(hotelsByProximity[i+1].getLongitude()-100,2));
            if (calculate1 > calculate2)
                didPass = false;
            if (calculate1 == calculate2)
                if (hotelsByProximity[i].getNumPOI() < hotelsByProximity[i+1].getNumPOI())
                    didPass = false;
        }
        assertTrue(didPass);
    }

    /**
     * Ordinary case, checks sorted array by proximity to given location, checks that array contains only
     * hotels from given city
     */

    @Test
    public void testGetHotelsInCityByProximity1(){
        boolean didPass = true;
        Hotel[] hotelsInCityByProximity = booping.getHotelsInCityByProximity("delhi", 40, -130);
        for (int i=0; i<hotelsInCityByProximity.length; i++){
            assertTrue(hotelsInCityByProximity[i].getCity().equals("delhi"));
        }
        for(int i=0; i<hotelsInCityByProximity.length-1; i++){
            double calculate1 = Math.sqrt(Math.pow(hotelsInCityByProximity[i].getLatitude()-40, 2)
                    + Math.pow(hotelsInCityByProximity[i].getLongitude()+130,2));
            double calculate2 = Math.sqrt(Math.pow(hotelsInCityByProximity[i+1].getLatitude()-40, 2)
                    + Math.pow(hotelsInCityByProximity[i+1].getLongitude()+130,2));
            if (calculate1 > calculate2)
                didPass = false;
            if (calculate1 == calculate2)
                if (hotelsInCityByProximity[i].getNumPOI() < hotelsInCityByProximity[i+1].getNumPOI())
                    didPass = false;
        }
        assertTrue(didPass);
    }

    /**
     * Case of illegal inputs. Checks if the returned array is empty.
     */

    @Test
    public void testGetHotelsInCityByProximity2(){
        Hotel[] hotelsInCityByProximity1 = booping.getHotelsInCityByProximity("delhi", -98, 200);
        assertTrue(hotelsInCityByProximity1.length == 0);
        Hotel[] hotelsInCityByProximity2 = booping.getHotelsInCityByProximity("delhi", 0, -200);
        assertTrue(hotelsInCityByProximity2.length == 0);
        Hotel[] hotelsInCityByProximity3 = booping.getHotelsInCityByProximity("delhi", 98, 2);
        assertTrue(hotelsInCityByProximity3.length == 0);
    }

    /**
     * Case of a city that doesn't appear in the file. Checks if the returned array is empty.
     */

    @Test
    public void testGetHotelsInCityByProximity3(){
        Hotel[] hotelsInCityByProximity1 = booping.getHotelsInCityByProximity("Jerusalem", 5, 5);
        assertTrue(hotelsInCityByProximity1.length == 0);
    }

    /**
     * Case of an empty String as input. Checks if the returned array is empty.
     */

    @Test
    public void testGetHotelsInCityByProximity4(){
        Hotel[] hotelsInCityByProximity1 = booping.getHotelsInCityByProximity("", 5, 5);
        assertTrue(hotelsInCityByProximity1.length == 0);
    }
}