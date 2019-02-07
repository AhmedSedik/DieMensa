package com.example.ahmed.diemensa;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.example.ahmed.diemensa.MondayActivity.LOG_TAG;

public class QueryUtils {
    public static long date;
    public static ArrayList<String> ls;

    public QueryUtils() {
    }

    private static URL createUrl(String strUrl) {
        URL url = null;

        try {
            url = new URL(strUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";
        //TODO new
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();

            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    public static List<Dish> fetchData(String requestUrl) {
      /*  try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        URL url = createUrl(requestUrl);

        String jsonResponse = "";

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Dish> dishes = extractFeatureFromJson(jsonResponse);
        Log.i(LOG_TAG, "QueryUtils returns " + dishes);
        return dishes;
    }

    private static List<Dish> extractFeatureFromJson(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding dishes to
        //List<Dish> dishes = new LinkedList<>();
        List<Dish> dishes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.

        try {

            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            JSONObject jsonObject1 = null;
            //dishes = new ArrayList<>();

            ls = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject1 = (JSONObject) jsonArray.get(i);
                if (jsonObject1 != null) {
                    String name = jsonObject1.getString("dish");
                    String daytime = jsonObject1.getString("daytime");
                    String dishName = jsonObject1.getString("dish");
                    String component = jsonObject1.getString("component");
                    double price = jsonObject1.getDouble("price");
                    String day = jsonObject1.getString("weakday");
                    int iconId_1 = jsonObject1.getInt("iconid_1");
                    int iconId_2 = jsonObject1.getInt("iconid_2");
                    date = jsonObject1.getLong("date");
                    int likesCounter = jsonObject1.getInt("likes");
                    String likes = String.valueOf((int) likesCounter);
                    Log.e(LOG_TAG, String.valueOf(date));

                    ls.add(jsonObject1.getString("date"));
                    Dish dish = new Dish(name, daytime, dishName,
                            component, price, day, date, iconId_1, iconId_2, likes);
                    dishes.add(dish);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dishes;
    }


}