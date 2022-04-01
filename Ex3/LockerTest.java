import oop . ex3 . spaceship .*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * This is a test class for Locker class.
 */


public class LockerTest {

    /** Array of legal items */
    private static final Item[] items = ItemFactory.createAllLegalItems();

    /** Items from array of legal items */
    private final Item baseballBat = items[0];
    private final Item smallHelmet = items[1];
    private final Item bigHelmet = items[2];
    private final Item sporesEngine = items[3];
    private final Item football = items[4];

    /** The locker we are testing */
    private Locker testLocker;

    /**
     * Empties the long-term storage after each test.
     */

    @After
    public void emptyLongTermStorage(){
        testLocker.LTS.resetInventory();
    }

    /**
     * Ordinary case of adding 1 item of type helmet, size 3. Locker capacity is 15.
     * Adding shouldn't cause items to move to LTS.
     */

    @Test
    public void testAddItem1(){
        testLocker = new Locker(15);
        int result = testLocker.addItem(bigHelmet, 1);
        assertTrue(result == testLocker.SUCCESSFUL);
        assertTrue(testLocker.getAvailableCapacity() == 10);
        assertTrue(testLocker.getItemCount(bigHelmet.getType()) == 1);
        assertTrue(testLocker.LTS.getAvailableCapacity() == testLocker.LTS.getCapacity());
    }

    /**
     * Case of trying to add baseball bat type item, while the locker contains a football type item.
     * Should return -2.
     */

    @Test
    public void testAddItem2(){
        testLocker = new Locker(35);
        testLocker.addItem(football, 2);
        int result = testLocker.addItem(baseballBat, 1);
        assertTrue(result == testLocker.CONSTRAINT_UNSUCCESSFUL);
        assertTrue(testLocker.getAvailableCapacity() == 27);
        assertTrue(testLocker.getItemCount(baseballBat.getType()) == 0);
        assertTrue(testLocker.LTS.getAvailableCapacity() == testLocker.LTS.getCapacity());
    }

    /**
     * Case of trying to add football type item, while the locker contains a baseball bat type item.
     * Should return -2.
     */

    @Test
    public void testAddItem3(){
        testLocker = new Locker(35);
        testLocker.addItem(baseballBat, 2);
        int result = testLocker.addItem(football, 1);
        assertTrue(result == testLocker.CONSTRAINT_UNSUCCESSFUL);
        assertTrue(testLocker.getAvailableCapacity() == 31);
        assertTrue(testLocker.getItemCount(football.getType()) == 0);
        assertTrue(testLocker.LTS.getAvailableCapacity() == testLocker.LTS.getCapacity());
    }

    /**
     * Case of having to move items to LTS.
     */

    @Test
    public void testAddItem4(){
        testLocker = new Locker(100);
        int result = testLocker.addItem(sporesEngine, 6); // total storage units = 60 (more then 50%)
        assertTrue(result == testLocker.SUCCESSFUL_AND_LTS);
        assertTrue(testLocker.getAvailableCapacity() == 80);
        assertTrue(testLocker.getItemCount(sporesEngine.getType()) == 2);
        assertTrue(testLocker.LTS.getAvailableCapacity() == 960);
    }

    /**
     * Edge case of trying to add too many items (more than the capacity of locker)
     */

    @Test
    public void testAddItem5(){
        testLocker = new Locker(9);
        int result = testLocker.addItem(smallHelmet, 4);
        assertTrue(result == testLocker.UNSUCCESSFUL);
        assertTrue(testLocker.getItemCount(smallHelmet.getType())== 0);
        assertTrue(testLocker.getAvailableCapacity() == 9);
    }

    /**
     * Edge case of trying to add too many items (more than the capacity of LTS)
     */

    @Test
    public void testAddItem6(){
        testLocker = new Locker(1000);
        testLocker.addItem(sporesEngine, 100);
        assertTrue(testLocker.LTS.getAvailableCapacity() == 200);
        int result = testLocker.addItem(sporesEngine, 100);
        assertTrue(result == testLocker.UNSUCCESSFUL);
        assertTrue(testLocker.LTS.getAvailableCapacity() == 200);
        assertTrue(testLocker.getAvailableCapacity() == 800);
        assertTrue(testLocker.getItemCount(sporesEngine.getType()) == 20);
        assertTrue(testLocker.LTS.getItemCount(sporesEngine.getType()) == 80);
    }

    /**
     * Edge case of trying to add a negative number of items
     */

    @Test
    public void testAddItem7(){
        testLocker = new Locker(10);
        int result = testLocker.addItem(football, -2);
        assertTrue(result == testLocker.UNSUCCESSFUL);
        assertTrue(testLocker.getItemCount(football.getType()) ==0);
    }

    /**
     * Ordinary case of trying to remove 1 item from a locker which contains 1 item
     * */

    @Test
    public void testRemoveItem1(){
        testLocker = new Locker(10);
        testLocker.addItem(bigHelmet, 1);
        int result = testLocker.removeItem(bigHelmet, 1);
        assertTrue(result == testLocker.SUCCESSFUL);
        assertTrue(testLocker.getAvailableCapacity() == 10);
        assertTrue(testLocker.getItemCount(bigHelmet.getType()) == 0);
    }

    /**
     * Case of trying to remove an item which isn't found in the locker
     * */

    @Test
    public void testRemoveItem2(){
        testLocker = new Locker(5);
        int result = testLocker.removeItem(smallHelmet, 2);
        assertTrue(result == testLocker.UNSUCCESSFUL);
        assertTrue(testLocker.getAvailableCapacity() == 5);
        assertTrue(testLocker.getItemCount(smallHelmet.getType()) == 0);
    }

    /**
     * Case of trying to remove n items of type football, while there are n-1 items type football
     * in the locker
     * */

    @Test
    public void testRemoveItem3(){
        testLocker = new Locker(20);
        testLocker.addItem(football, 2);
        int result = testLocker.removeItem(football,3);
        assertTrue(result == testLocker.UNSUCCESSFUL);
        assertTrue(testLocker.getAvailableCapacity() == 20 - 2*football.getVolume());
        assertTrue(testLocker.getItemCount(football.getType()) == 2);
    }

    /**
     * Case of trying to remove a negative number of items
     * */

    @Test
    public void testRemoveItem4(){
        testLocker = new Locker(5);
        testLocker.addItem(baseballBat, 1);
        int result = testLocker.removeItem(baseballBat, -1);
        assertTrue(result == testLocker.UNSUCCESSFUL);
        assertTrue(testLocker.getAvailableCapacity() == 3);
        assertTrue(testLocker.getItemCount(baseballBat.getType()) == 1);
    }
}