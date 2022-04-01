import oop . ex3 . spaceship .*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * This is a test class for LongTermStorage class.
 */

public class LongTermTest {

    /** Array of legal items */
    private static final Item[] items = ItemFactory.createAllLegalItems();

    /** Items from array of legal items */
    private final Item baseballBat = items[0];
    private final Item smallHelmet = items[1];
    private final Item bigHelmet = items[2];
    private final Item sporesEngine = items[3];
    private final Item football = items[4];

    /** The long term storage we are testing */
    private LongTermStorage LTS;

    /**
     * Empties the long-term storage after each test.
     */

    @After
    public void emptyLongTermStorage(){
        LTS.resetInventory();
    }

    /**
     * Ordinary case of adding 10 items of type helmet, size 3.
     */

    @Test
    public void testAddItem1(){
        LTS = new LongTermStorage();
        int result = LTS.addItem(bigHelmet, 10);
        assertTrue(result == LTS.SUCCESSFUL);
        assertTrue(LTS.getAvailableCapacity() == 950);
        assertTrue(LTS.getItemCount(bigHelmet.getType()) == 10);
    }

    /**
     * Case of trying to add baseball bat type item, while the long-term storage contains a football
     * type item. Should return 0 (success).
     */

    @Test
    public void testAddItem2(){
        LTS = new LongTermStorage();
        LTS.addItem(football, 2);
        int result = LTS.addItem(baseballBat, 2);
        assertTrue(result == LTS.SUCCESSFUL);
        assertTrue(LTS.getAvailableCapacity() == 988);
        assertTrue(LTS.getItemCount(baseballBat.getType()) == 2);
        assertTrue(LTS.getItemCount(football.getType()) == 2);
    }

    /**
     * Edge case of trying to add too many items, more than the capacity
     */

    @Test
    public void testAddItem3(){
        LTS = new LongTermStorage();
        int result = LTS.addItem(sporesEngine, 1500);
        assertTrue(result == LTS.UNSUCCESSFUL);
        assertTrue(LTS.getItemCount(sporesEngine.getType())== 0);
        assertTrue(LTS.getAvailableCapacity() == 1000);
    }

    /**
     * Edge case of trying to add a negative number of items
     */

    @Test
    public void testAddItem4() {
        LTS = new LongTermStorage();
        int result = LTS.addItem(football, -2);
        assertTrue(result == LTS.UNSUCCESSFUL);
        assertTrue(LTS.getItemCount(football.getType()) == 0);
    }
}