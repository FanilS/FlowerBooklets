package com.abarska.flowerbooklets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.concurrent.TimeUnit;

/**
 * Created by Dell I5 on 11.07.2017.
 */

public class QueryUtils {

    static final String URL_GET_FLOWERS = "http://52.51.81.191:85/getFlowers";
    static final String URL_GET_TRANSLATE = "http://52.51.81.191:85/getTranslate";

    private static final String LOG_TAG = "QueryUtils";

    private QueryUtils() {
    }

    static ArrayList<Flower> fetchFlowerData(Context context, URL flowersUrl, URL translateUrl) {
        String flowersJsonResponse = null;
        String translateJsonResponse = null;
        try {
            flowersJsonResponse = makeHttpRequest(flowersUrl);
            translateJsonResponse = makeHttpRequest(translateUrl);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        return extractFeatureFromJson(context, flowersJsonResponse, translateJsonResponse);
    }

    static URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    static String makeHttpRequest(URL url) throws IOException {
        if (url == null) return null;

        HttpURLConnection connection = null;
        InputStream inputStream = null;
        String jsonResponse = "";

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout((int) TimeUnit.SECONDS.toMillis(10));
            connection.setConnectTimeout((int) TimeUnit.SECONDS.toMillis(15));
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.d(LOG_TAG, "response code = " + connection.getResponseCode());
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (connection != null) connection.disconnect();
            if (inputStream != null) inputStream.close();
        }
        return jsonResponse;
    }

    private static ArrayList<Flower> extractFeatureFromJson(Context context, String flowersJsonResponse, String translateJsonResponse) {

        if (TextUtils.isEmpty(flowersJsonResponse) || TextUtils.isEmpty(translateJsonResponse))
            return null;

        String locale = TranslateUtils.checkDeviceLanguage(context);
        ArrayList<Flower> flowers = new ArrayList<>();

        try {
            JSONObject rootObject = new JSONObject(flowersJsonResponse);
            JSONArray data = rootObject.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject flower = (JSONObject) data.get(i);
                String name = flower.getString("name");
                String season = flower.getString("best season");
                Bitmap pic = getBitmapPic(flower.getString("image link"));
                Flower currentFlower = createFlower(context, translateJsonResponse, name, season, pic);
                flowers.add(currentFlower);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("QueryUtils", "IOException", e);
        }
        return flowers;
    }

    private static Flower createFlower(Context context, String translateJsonResponse, String name, String season, Bitmap pic) {
        String deviceLanguage = TranslateUtils.checkDeviceLanguage(context);
        return TranslateUtils.getTranslatedFlower(translateJsonResponse, deviceLanguage, name, season, pic);
    }

    private static String readFromStream(InputStream stream) throws IOException {
        if (stream == null) return null;
        StringBuilder builder = new StringBuilder();
        InputStreamReader is = new InputStreamReader(stream, Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(is);
        String line = br.readLine();
        while (line != null) {
            builder.append(line);
            line = br.readLine();
        }
        return builder.toString();
    }

    private static Bitmap getBitmapPic(String stringPicUrl) throws IOException {

        Log.d(LOG_TAG, "pic URL = " + stringPicUrl);
        Log.d(LOG_TAG, "rose URL = " + "https://www.almanac.com/sites/default/files/styles/primary_image_in_article/public/images/photo_9705.jpg?itok=44DBZcZV");
        Log.d(LOG_TAG, "pic URL = rose?" + (stringPicUrl.equals("https://www.almanac.com/sites/default/files/styles/primary_image_in_article/public/images/photo_9705.jpg?itok=44DBZcZV")));

        Bitmap resultBitmap = null;
        if (stringPicUrl == null) return resultBitmap;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        URL queryUrl = null;

        try {
            queryUrl = new URL(stringPicUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "MalformedURLException", e);
        }

        try {
            urlConnection = (HttpURLConnection) queryUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout((int) TimeUnit.SECONDS.toMillis(10));
            urlConnection.setConnectTimeout((int) TimeUnit.SECONDS.toMillis(15));
            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                resultBitmap = BitmapFactory.decodeStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Unexpected response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException caught: " + e.getMessage());

        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (inputStream != null)
                inputStream.close();
        }
        return resultBitmap;
    }

    private static String toTitleCase(String input) {
        String[] stringArray = input.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String s : stringArray) {
            sb.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).append(" ");
        }
        return sb.toString();
    }
}
