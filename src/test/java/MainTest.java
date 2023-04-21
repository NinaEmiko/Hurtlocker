import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class MainTest {
    private Main main;
    private static ArrayList<HashMap<String, String>> parsedData = new ArrayList<>();
    HashMap<String, String> newJawn = new HashMap<>();
    ArrayList<String> prices = new ArrayList<>();
    @Before
    public void setUp(){
        this.main = new Main();

    }
    @Test
    public void testConvertJerkSONToArray(){
        String stringToProcess = "nina:Bobina##tiffy:Spiffy";
        int expected = 2;
        int actual = main.convertJerkSONToArray(stringToProcess).length;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testNameWithConvertArrayToHashMap(){
        String toBeConverted = "NAMe:BrEAD;price:1.23;type:Food;expiration:1/25/2016";
        newJawn = main.convertArrayToHashMap(toBeConverted);
        String expected = "BrEAD";
        String actual = newJawn.get("NAMe");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPriceWithConvertArrayToHashMap(){
        String toBeConverted = "NAMe:BrEAD;price:1.23;type:Food;expiration:1/25/2016";
        newJawn = main.convertArrayToHashMap(toBeConverted);
        String expected = "1.23";
        String actual = newJawn.get("price");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testTypeWithConvertArrayToHashMap(){
        String toBeConverted = "NAMe:BrEAD;price:1.23;type:Food;expiration:1/25/2016";
        newJawn = main.convertArrayToHashMap(toBeConverted);
        String expected = "Food";
        String actual = newJawn.get("type");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExpirationWithConvertArrayToHashMap(){
        String toBeConverted = "NAMe:BrEAD;price:1.23;type:Food;expiration:1/25/2016";
        newJawn = main.convertArrayToHashMap(toBeConverted);
        String expected = "1/25/2016";
        String actual = newJawn.get("expiration");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetHashMapKey() {
        newJawn.put("NaMe", "bReaD");
        String expected = "NaMe";
        String actual = main.getHashMapKey(newJawn, "name");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetHashMapValue() {
        newJawn.put("pRICe", "3.14");
        String expected = "3.14";
        String actual = main.getHashMapValue(newJawn, "pRIce");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetPrices() {
        newJawn.put("name", "bread");
        newJawn.put("price", "1.24");
        parsedData.add(newJawn);

        String expected = "1.24";
        String actual = main.getPrices(parsedData, "bread").get(0);
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testCountUniqueValues() {
        prices.add("2.00");

        Integer expected = 1;
        Integer actual = main.countUniqueValues(prices).get("2.00");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCatchBlankPriceErrors() {
        prices.add("");

        Integer expected = 1;
        Integer actual = main.catchErrors(parsedData);

        Assert.assertEquals(expected, actual);
    }
}