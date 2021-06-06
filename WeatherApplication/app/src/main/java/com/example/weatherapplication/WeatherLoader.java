package com.example.weatherapplication;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class WeatherLoader extends AsyncTaskLoader<String> {
    private final String query;

    public WeatherLoader(@NonNull  Context context, String query) {
        super(context);
        this.query = query;
    }


    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.checkweather(query);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
