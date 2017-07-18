package com.abarska.flowerbooklets;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

/**
 * Created by Dell I5 on 11.07.2017.
 */

public class FlowerLoader extends AsyncTaskLoader<ArrayList<Flower>> {

    public FlowerLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public ArrayList<Flower> loadInBackground() {
        return QueryUtils.fetchFlowerData(getContext());
    }
}
