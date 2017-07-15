package com.abarska.flowerbooklets;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import static com.abarska.flowerbooklets.FlowerPagerAdapter.BUNDLE_KEY_NAME;
import static com.abarska.flowerbooklets.FlowerPagerAdapter.BUNDLE_KEY_PIC;
import static com.abarska.flowerbooklets.FlowerPagerAdapter.BUNDLE_KEY_SEASON;

public class FlowerFragment extends Fragment {

    private String mName;
    private String mSeason;
    private Bitmap mPic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.flower_list_item, container, false);

        Bundle inputArgs = getArguments();

        if (inputArgs.containsKey(BUNDLE_KEY_NAME)) {
            mName = inputArgs.getString(BUNDLE_KEY_NAME);
        }

        if (inputArgs.containsKey(BUNDLE_KEY_SEASON)) {
            mSeason = inputArgs.getString(BUNDLE_KEY_SEASON);
        }

        if (inputArgs.containsKey(BUNDLE_KEY_PIC)) {
            mPic = inputArgs.getParcelable(BUNDLE_KEY_PIC);
        }

        TextView tvName = (TextView) rootView.findViewById(R.id.tv_flower_name);
        TextView tvSeason = (TextView) rootView.findViewById(R.id.tv_blossom_season);
        ImageView ivPic = (ImageView) rootView.findViewById(R.id.ivFlower);

        if (mPic == null) {
            tvName.setTextColor(Color.BLACK);
            tvSeason.setTextColor(Color.BLACK);
        } else {
            ivPic.setImageBitmap(mPic);
        }

        tvName.setText(mName);
        tvSeason.setText(mSeason);

        return rootView;
    }
}
