package com.abarska.flowerbooklets;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Dell I5 on 11.07.2017.
 */

public class FlowerPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Flower> mFlowers;

    public static String BUNDLE_KEY_NAME = "bundle-key-name";
    public static String BUNDLE_KEY_SEASON = "bundle-key-season";
    public static String BUNDLE_KEY_PIC = "bundle-key-pic";


    public FlowerPagerAdapter(ArrayList<Flower> flowers, FragmentManager fm) {
        super(fm);
        mFlowers = flowers;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putString(BUNDLE_KEY_NAME, mFlowers.get(position).getName());
        args.putString(BUNDLE_KEY_SEASON, mFlowers.get(position).getBestSeason());
        args.putParcelable(BUNDLE_KEY_PIC, mFlowers.get(position).getPic());
        FlowerFragment flowerFragment = new FlowerFragment();
        flowerFragment.setArguments(args);
        return flowerFragment;
    }

    @Override
    public int getCount() {
        return mFlowers.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFlowers.get(position).getName();
    }
}
