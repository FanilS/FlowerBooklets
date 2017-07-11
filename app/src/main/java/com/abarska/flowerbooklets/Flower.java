package com.abarska.flowerbooklets;

import android.graphics.Bitmap;

/**
 * Created by Dell I5 on 11.07.2017.
 */

public class Flower {

    private String mName;
    private String mBestSeason;
    private Bitmap mPic;

    public Flower(String name, String bestSeason, Bitmap pic) {
        mName = name;
        mBestSeason = bestSeason;
        mPic = pic;
    }

    public String getName() {
        return mName;
    }

    public String getBestSeason() {
        return mBestSeason;
    }

    public Bitmap getPic() {
        return mPic;
    }
}
