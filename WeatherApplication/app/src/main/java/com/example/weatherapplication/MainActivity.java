package com.example.weatherapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.weatherapplication.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    String querystring;
    private ActivityMainBinding bind;
    Context ctx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        ConstraintLayout c=(ConstraintLayout)bind.ct;
        ConstraintLayout c1=(ConstraintLayout)bind.ct1;
        c1.setVisibility(View.INVISIBLE);
        Glide.with(this).load(R.drawable.weather).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull  Drawable resource, @Nullable  Transition<? super Drawable> transition) {
                c.setBackground(resource);
            }
        });




        if(getSupportLoaderManager().getLoader(0)!=null){
            getSupportLoaderManager().initLoader(0,null,this);
        }

    }

    public void checkweather(View view){
        ConstraintLayout c1=(ConstraintLayout)bind.ct1;
        c1.setVisibility(View.INVISIBLE);

        querystring = bind.inputcityname.getText().toString();
        InputMethodManager inputmanager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputmanager!=null){
            inputmanager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        if(networkInfo!=null && networkInfo.isConnected() && querystring.length()!=0){
            Bundle b=new Bundle();
            b.putString("query",querystring);
            getSupportLoaderManager().restartLoader(0,b,this);
            bind.cityname.setText("loading...");
        }
        else{
            if(querystring.length()==0){
                bind.cityname.setText("Please enter a search term");
            }
            else{
                bind.cityname.setText("pLEASE CHECK YOUR CONNECTION");
            }
        }


    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String query=null;
        if(args!=null){
            query=args.getString("query");
        }
        return new WeatherLoader(this,query);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try{

//            convert the response into JSON object
            JSONObject jsonobject=new JSONObject(data);
//            get the jsonarray of weather
            JSONArray itemarr=jsonobject.getJSONArray("weather");
//            Initialize iterator ans result
            int i=0;
            String weather=null;
            while(i<itemarr.length() && weather==null){
//                get the current item information
                try{
                    JSONObject climate=itemarr.getJSONObject(i);
                    weather=climate.getString("main");
                }catch(Exception e){
                    e.printStackTrace();
                }
                i++;
            }
            JSONObject Main=jsonobject.getJSONObject("main");
            String maxtemp=Main.getString("temp_max");
            String mintemp=Main.getString("temp_min");

            double min=Double.parseDouble(mintemp)-273.15;
            double max=Double.parseDouble(maxtemp)-273.15;

            mintemp=String.format("%.2f",min);
            maxtemp=String.format("%.2f",max);

//            if weather are found
            if(weather!=null){

                bind.weather.setText(weather);
                String output = querystring.substring(0, 1).toUpperCase() + querystring.substring(1).toLowerCase();
                bind.cityname.setText(output);
                bind.maxtemp.setText(new StringBuilder().append(maxtemp).append("\u00B0").append("C").toString());
                bind.mintemp.setText(new StringBuilder().append(mintemp).append("\u00B0").append("C").toString());
                ConstraintLayout c=(ConstraintLayout)bind.ct;
                ConstraintLayout c1=(ConstraintLayout)bind.ct1;
                c1.setVisibility(View.VISIBLE);

                if(weather.equals("Clear")){
                    Glide.with(this).load(R.drawable.clear).into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull  Drawable resource, @Nullable  Transition<? super Drawable> transition) {
                            c.setBackground(resource);
                        }
                    });
                }
                else if(weather.equals("Clouds")){
                    Glide.with(this).load(R.drawable.cloud).into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull  Drawable resource, @Nullable  Transition<? super Drawable> transition) {
                            c.setBackground(resource);
                        }
                    });
                }
                else if(weather.equals("Haze")){
                    Glide.with(this).load(R.drawable.haze).into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull  Drawable resource, @Nullable  Transition<? super Drawable> transition) {
                            c.setBackground(resource);
                        }
                    });
                }
                else if(weather.equals("Rain")){
                    Glide.with(this).load(R.drawable.rain).into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull  Drawable resource, @Nullable  Transition<? super Drawable> transition) {
                            c.setBackground(resource);
                        }
                    });
                }
                else if(weather.equals("Snow")){
                    Glide.with(this).load(R.drawable.snow).into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull  Drawable resource, @Nullable  Transition<? super Drawable> transition) {
                            c.setBackground(resource);
                        }
                    });
                }
                else if(weather.equals("Sunny")){
                    Glide.with(this).load(R.drawable.sunny).into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull  Drawable resource, @Nullable  Transition<? super Drawable> transition) {
                            c.setBackground(resource);
                        }
                    });
                }
                else if(weather.equals("Thunderstorm")){
                    Glide.with(this).load(R.drawable.thunderstorm).into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull  Drawable resource, @Nullable  Transition<? super Drawable> transition) {
                            c.setBackground(resource);
                        }
                    });
                }

            }
            else{
                Toast.makeText(this,"No Result",Toast.LENGTH_SHORT).show();
                bind.cityname.setText(R.string.noresult);
            }
        }
        catch(Exception e){
            Toast.makeText(this,"No Result",Toast.LENGTH_SHORT).show();
            bind.cityname.setText(R.string.noresult);
        }

    }

    @Override
    public void onLoaderReset(@NonNull  Loader<String> loader) {

    }
}