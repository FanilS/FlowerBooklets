package com.abarska.flowerbooklets;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dell I5 on 15.07.2017.
 */

class TranslateUtils {

    private TranslateUtils() {
    }

    static String checkDeviceLanguage(Context context) {
        String locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = context.getResources().getConfiguration().getLocales().get(0).toString().substring(0, 2);
        } else {
            locale = context.getResources().getConfiguration().locale.toString().substring(0, 2);
        }
        return locale;
    }

    static String getGreetingMessage(String translateJsonResponse, String deviceLanguage) {
        JSONObject jsonLangObject = getJsonLangObject(translateJsonResponse, deviceLanguage);
        return jsonLangObject.optString("hello") + "\n" + jsonLangObject.optString("welcome") + "\n" + jsonLangObject.optString("flower guide");
    }

    static Flower getTranslatedFlower(String translateJsonResponse, String deviceLanguage, String name, String season, Bitmap pic) {
        JSONObject jsonLangObject = getJsonLangObject(translateJsonResponse, deviceLanguage);
        if (deviceLanguage.equals("en")) {
            return new Flower(name, season, pic);
        } else {
            return new Flower(jsonLangObject.optString(name), jsonLangObject.optString("blossom season") + ":\n" + jsonLangObject.optString(season), pic);
        }
    }

    private static JSONObject getJsonLangObject(String translateJsonResponse, String deviceLanguage) {
        try {
            JSONObject rootObject = new JSONObject(translateJsonResponse);
            JSONObject data = rootObject.getJSONObject("data");
            JSONObject langObject;
            if (data.has(deviceLanguage)) {
                langObject = data.getJSONObject(deviceLanguage);
            } else {
                langObject = data.getJSONObject("en");
            }
            return langObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
