import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class MainTest {
    private Main main;
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
}