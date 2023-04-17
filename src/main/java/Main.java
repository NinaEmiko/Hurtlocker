import org.apache.commons.io.IOUtils;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    private static ArrayList<HashMap<String, String>> parsedData = new ArrayList<>();
    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
        String[] keyValueArray = convertJerkSONToArray(output);
        addDataToArrayList(keyValueArray);

        HashMap<String, Integer> applePrices = countUniqueValues(getPrices(parsedData, "apples"));
        HashMap<String, Integer> breadPrices = countUniqueValues(getPrices(parsedData, "bread"));
        HashMap<String, Integer> milkPrices = countUniqueValues(getPrices(parsedData, "milk"));
        HashMap<String, Integer> cookiesPrices = countUniqueValues(getPrices(parsedData, "cookies"));
        System.out.println(applePrices);
    }

    public static ArrayList<String> getPrices(ArrayList<HashMap<String, String>> list, String key){
        ArrayList<String> price = new ArrayList<>();
        for (HashMap<String, String> hm : list) {
            if(getHashMapValue(hm, "name").equalsIgnoreCase(key)){
                price.add(getHashMapValue(hm, "price"));
            }
        }
        return price;
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
    public static String getHashMapKey(HashMap<String, String> map, String key){
        return map.entrySet().stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(key))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }
    public static String getHashMapValue(HashMap<String, String> map, String key){
        return map.entrySet().stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(key))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse("");
    }
    public static HashMap<String, Integer> countUniqueValues(ArrayList<String> list) {
        return list.stream()
                .collect(Collectors.toMap(Function.identity(), s -> 1, Integer::sum, HashMap::new));
    }
}

//naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##