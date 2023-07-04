import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.sql.Date;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Result> results = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            FileReader fileReader = new FileReader("src/main/resources/results.json");
            JSONObject json = (JSONObject) parser.parse(fileReader);

            JSONArray resultsArray = (JSONArray) json.get("results");
            for (Object obj : resultsArray) {
                JSONObject resultObj = (JSONObject) obj;

                for (Object key : resultObj.keySet()) {
                    String login = (String) key;
                    JSONArray tests = (JSONArray) resultObj.get(login);

                    for (Object testObj : tests) {
                        JSONObject test = (JSONObject) testObj;

                        for (Object testName : test.keySet()) {
                            String testKey = (String) testName;
                            JSONObject details = (JSONObject) test.get(testKey);

                            String testDate = (String) details.get("date");
                            Double testMark = (Double) details.get("mark");

                            // Convert the date string to java.sql.Date
                            Date date = Date.valueOf(testDate);

                            // Create a new Result object and add it to the results list
                            Result result = new Result(login, testKey, date, testMark);
                            results.add(result);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Print the collection in the required format
        for (Result result : results) {
            System.out.println(result);
        }
    }
}