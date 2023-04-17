import org.apache.commons.io.IOUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static ArrayList<HashMap<String, String>> parsedData = new ArrayList<>();
    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
        String[] keyValueArray = convertJerkSONToArray(output);
        addDataToArrayList(keyValueArray);

        System.out.println(parsedData);
        System.out.println(getHashMapName(parsedData.get(0)));
    }

    public static void addDataToArrayList(String[] arr){
        for (String s: arr) {
            parsedData.add(convertArrayToHashMap(s));
        }
    }

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        return IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
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
    public static HashMap<String, String> convertArrayToHashMap(String input) {
        HashMap<String, String> map = new HashMap<>();
        Pattern pattern = Pattern.compile("([a-zA-Z]+):([^;!@%^*]+)");
        Matcher matcher = pattern.matcher(input);
        while(matcher.find()){
            String key = matcher.group(1);
            String value = matcher.group(2);
            map.put(key, value);
        }
        return map;
    }

    public static String getHashMapName(HashMap<String, String> map){
        return map.entrySet().stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase("name"))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(null);
    }
}

//naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##