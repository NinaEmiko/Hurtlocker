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
    public void testGetHashMapName() {
        newJawn.put("NaMe", "bReaD");
        String expected = "bReaD";
        String actual = main.getHashMapName(newJawn);
        Assert.assertEquals(expected, actual);



    }
}