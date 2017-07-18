package com.abarska.flowerbooklets;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import static com.abarska.flowerbooklets.QueryUtils.URL_GET_TRANSLATE;

/**
 * Created by Dell I5 on 15.07.2017.
 */

public class TranslateUtils {

    private static final String LOG_TAG = "TranslateUtils";

    private TranslateUtils() {
    }

    public static String checkDeviceLanguage(Context context) {
        String locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = context.getResources().getConfiguration().getLocales().get(0).toString().substring(0, 2);
        } else {
            locale = context.getResources().getConfiguration().locale.toString().substring(0, 2);
        }
        return locale;
    }

    public static String getGreetingMessage(String jsonResponse, String deviceLanguage) {
        String result = null;
        try {
            JSONObject rootObject = new JSONObject(jsonResponse);
            JSONObject data = rootObject.getJSONObject("data");
            JSONObject langObject;
            if (data.has(deviceLanguage)) {
                langObject = data.getJSONObject(deviceLanguage);
            } else {
                langObject = data.getJSONObject("en");
            }
            result = langObject.getString("hello") + "\n" + langObject.getString("welcome") + "\n" + langObject.getString("flower guide");
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Flower getTranslatedFlower(String deviceLanguageCode, String name, String season, Bitmap pic) {

        URL url = QueryUtils.createUrl(URL_GET_TRANSLATE);

        String jsonResponse = null;
        try {
            jsonResponse = QueryUtils.makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        Flower resultFlower = null;
        try {
            JSONObject rootObject = new JSONObject(jsonResponse);
            JSONObject data = rootObject.getJSONObject("data");
            if (data.has(deviceLanguageCode)) {
                JSONObject langObject = data.getJSONObject(deviceLanguageCode);
                resultFlower = new Flower(langObject.getString(name), langObject.getString(season), pic);
            } else {
                resultFlower = new Flower(name, season, pic);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultFlower;
    }
}
