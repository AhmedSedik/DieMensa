package com.example.ahmed.diemensa;

import android.text.TextUtils;
import android.util.Log;

import com.facebook.shimmer.ShimmerFrameLayout;

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
import java.util.LinkedList;
import java.util.List;

import static com.example.ahmed.diemensa.MondayDishActivity.LOG_TAG;

public class QuerryUtils {

    ShimmerFrameLayout container;

    public QuerryUtils(){

    }

    private static URL createUrl(String strUrl){
        URL url = null;

        try {
            url  = new URL(strUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse="";

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;


        try {
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static  String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();

        if(inputStream != null){

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();

            while(line!=null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }
    public static List<Dish> fetchData(String requestUrl){

        URL url = createUrl(requestUrl);

        String jsonResponse = "";

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Dish> dishes = extractFeatureFromJson(jsonResponse);
        return dishes;
    }

    private static List<Dish> extractFeatureFromJson(String jsonResponse) {
        if(TextUtils.isEmpty(jsonResponse)){
            return null;
        }

        // Create an empty ArrayList that we can start adding dishes to
        List<Dish> dishes = new LinkedList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.



        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray dishArray = baseJsonResponse.getJSONArray("feature");

            for(int i =0;i<dishArray.length();i++){
                JSONObject currentDish = dishArray.getJSONObject(i);

                JSONObject properties = currentDish.getJSONObject("properties");

                String place = properties.getString("place");
                String daytime = properties.getString("daytime");
                String dishName = properties.getString("dish");
                String component = properties.getString("inhalt");
                int price = properties.getInt("price");

                Dish dish  = new Dish(place,daytime,dishName,component,price);

                dishes.add(dish);


            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return dishes;
    }
}
