package com.abarska.flowerbooklets;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

/**
 * Created by Dell I5 on 15.07.2017.
 */

public class SplashActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final int GREETING_LOADER_ID = 111;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
            getSupportLoaderManager().initLoader(GREETING_LOADER_ID, null, this).forceLoad();
        } else {
            Toast.makeText(this, getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new GreetingLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (TextUtils.isEmpty(data)) return;

        TextView tvGreeting = (TextView) findViewById(R.id.tv_greeting);
        tvGreeting.setText(data);
        tvGreeting.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, TimeUnit.SECONDS.toMillis(2));
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }
}
