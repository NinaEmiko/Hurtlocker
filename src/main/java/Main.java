import org.apache.commons.io.IOUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    
    private static ArrayList<HashMap<String, String>> parsedData = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        addDataToArrayList(convertJerkSONToArray((new Main()).readRawDataToString()));

        HashMap<String, Integer> applePrices = countUniqueValues(getPrices(parsedData, "apples"));
        HashMap<String, Integer> breadPrices = countUniqueValues(getPrices(parsedData, "bread"));
        HashMap<String, Integer> milkPrices = countUniqueValues(getPrices(parsedData, "milk"));
        HashMap<String, Integer> cookiesPrices = countUniqueValues(getPrices(parsedData, "cookies"));

        writeToFile(formatPriceData(applePrices, breadPrices, milkPrices, cookiesPrices));
    }

    public static void writeToFile(String s){
        try {
            FileWriter writer = new FileWriter("./target/classes/outputFormat.txt");
            writer.write(s);
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
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
        String[] arr = new String[0];

        while(matcher.find()) {
            arr = Arrays.copyOf(arr, arr.length + 1);
            arr[arr.length - 1] = matcher.group();
        }
        return arr;
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

    public static String formatPriceData(HashMap<String, Integer> applePrices, HashMap<String, Integer> breadPrices,
                                         HashMap<String, Integer> milkPrices, HashMap<String, Integer> cookiesPrices) {
        StringBuilder sb = new StringBuilder();

        // Format apple prices
        sb.append("name:  Apples       seen: " + applePrices.values().stream().mapToInt(Integer::intValue).sum() + " times\n");
        sb.append("=============       =============\n");

        applePrices.forEach((key, value) -> {
            sb.append("Price:   " + key + "       seen: " + value + " times\n");
            sb.append("-------------       -------------\n");
        });

        // Format bread prices
        sb.append("name:   Bread       seen: " + breadPrices.values().stream().mapToInt(Integer::intValue).sum() + " times\n");
        sb.append("=============       =============\n");

        breadPrices.forEach((key, value) -> {
            sb.append("Price:   " + key + "       seen: " + value + " times\n");
            sb.append("-------------       -------------\n");
        });

        // Format milk prices
        sb.append("name:    Milk       seen: " + milkPrices.values().stream().mapToInt(Integer::intValue).sum() + " times\n");
        sb.append("=============       =============\n");

        milkPrices.forEach((key, value) -> {
            sb.append("Price:   " + key + "       seen: " + value + " times\n");
            sb.append("-------------       -------------\n");
        });

        // Format cookie prices
        sb.append("name: Cookies       seen: " + cookiesPrices.values().stream().mapToInt(Integer::intValue).sum() + " times\n");
        sb.append("=============       =============\n");

        cookiesPrices.forEach((key, value) -> {
            sb.append("Price:   " + key + "       seen: " + value + " times\n");
            sb.append("-------------       -------------\n");
        });

        // Add errors count
        int errorCount = countUniqueValues(getPrices(parsedData, "errors")).size();
        sb.append("Errors              seen: " +
                catchErrors(parsedData)   +
                " times\n");

        return sb.toString();
    }

    public static int catchErrors(ArrayList<HashMap<String, String>> items) {
        int totalCount = 0;
        for (HashMap<String, String> item : items) {
            int errorCount = 0;
            for (Map.Entry<String, String> entry : item.entrySet()) {
                String price = entry.getValue();
                if (price.isEmpty()) {
                    errorCount++;
                }
            }
            totalCount += errorCount;
        }
        return totalCount;
    }
}