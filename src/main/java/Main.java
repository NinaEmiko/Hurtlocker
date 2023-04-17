import org.apache.commons.io.IOUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        return IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
    }

    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
        String[] keyValueArray = convertJerkSONToArray(output);
        printArray(keyValueArray);
    }

    public static void printArray(String[] arr) {
        for (String s: arr) {
            System.out.println(s);
        }
    }

    public static String[] convertJerkSONToArray(String s){
        Pattern pattern = Pattern.compile("\\w+:[^#]+");
        Matcher matcher = pattern.matcher(s);

        String[] individualJawns = new String[0];

        while(matcher.find()) {
            individualJawns = Arrays.copyOf(individualJawns, individualJawns.length + 1);
            individualJawns[individualJawns.length - 1] = matcher.group();
        }
        return individualJawns;
    }

    //toLowerCase
    //if thisString.get(4) starts with c
    //if starts with a
    //if starts with m
    //if starts with b
    //if "" increment error's seen value
}

//naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##