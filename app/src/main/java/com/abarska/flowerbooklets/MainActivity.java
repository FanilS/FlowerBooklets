package com.abarska.flowerbooklets;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Flower>> {

    public static final String URL_GET_FLOWERS = "http://52.51.81.191:85/getFlowers";
    private static final int LOADER_ID = 222;
    private static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
            getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        }
    }

    @Override
    public Loader<ArrayList<Flower>> onCreateLoader(int id, Bundle args) {
        return new FlowerLoader(this, URL_GET_FLOWERS);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Flower>> loader, ArrayList<Flower> data) {

        if (!data.isEmpty()) {

            ViewPager viewPager = (ViewPager) findViewById(R.id.container);
            FlowerPagerAdapter adapter = new FlowerPagerAdapter(data, getSupportFragmentManager());
            viewPager.setAdapter(adapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);

        } else {
            Toast.makeText(this, "No data from server", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Flower>> loader) {
    }
}
