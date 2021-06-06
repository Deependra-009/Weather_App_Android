package com.example.weatherapplication;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    private  static final String WEATHER_URL="http://api.openweathermap.org/data/2.5/weather?";
    private static final String QUERY_PARAM="q";
    private static final String API_KEY="appid";
    private static final String PRINT_TYPE="printType";

    static String checkweather(String querystring){

        HttpURLConnection urlConnection=null;
        BufferedReader reader=null;
        String weatherJSONString=null;

        try{
            Uri builtURI=Uri.parse(WEATHER_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM,querystring)
                    .appendQueryParameter(API_KEY,"b875135a1eadfd38b69f06973339f079")
                    .build();
            URL requestURL=new URL(builtURI.toString());
            urlConnection=(HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream=urlConnection.getInputStream();
            reader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder=new StringBuilder();
            String line;
            while((line= reader.readLine())!=null){
                builder.append(line);
                builder.append("\n");
            }

            if(builder.length()==0){
                return null;
            }
            weatherJSONString=builder.toString();

        }
        catch(Exception e){
            Log.e("ERROR",e.getMessage());
        }
        finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(reader!=null){
                try{
                    reader.close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

//        Log.d("OUTPUT",weatherJSONString);
        return weatherJSONString;

    }
}
