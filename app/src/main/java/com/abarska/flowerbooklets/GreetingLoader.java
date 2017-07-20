package com.abarska.flowerbooklets;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Dell I5 on 15.07.2017.
 */

class GreetingLoader extends AsyncTaskLoader<String> {

    private static final String LOG_TAG = "GreetingLoader";

    GreetingLoader(Context context) {
        super(context);
    }

    @Override
    public String loadInBackground() {

        String locale = TranslateUtils.checkDeviceLanguage(getContext());
        URL translateURL = QueryUtils.createUrl(QueryUtils.URL_GET_TRANSLATE);

        String jsonResponse = null;
        try {
            jsonResponse = QueryUtils.makeHttpRequest(translateURL);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request", e);
        }

        return TranslateUtils.getGreetingMessage(jsonResponse, locale);
    }
}
