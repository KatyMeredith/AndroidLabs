package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class WeatherForecast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] urls = {
                "https://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric",
                "https://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389"
        };

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);


        findViewById(R.id.ProgressBar).setVisibility(View.VISIBLE);

        new ForecastQuery().execute(urls);
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {

        private String UV, min, max, temp, icon;
        private Bitmap picture;

        @Override
        protected String doInBackground(String... strings) {
            publishProgress(25);
            parseTemps(fetch(strings[0]));
            publishProgress(50);
            parseUV(fetch(strings[1]));
            publishProgress(75);
            fetchImage();
            publishProgress(100);

            return "Done";
        }

        @Override
        protected void onPostExecute(String s) {
            ((ProgressBar)findViewById(R.id.ProgressBar)).setVisibility(View.INVISIBLE);
            ((TextView)findViewById(R.id.CurrentTempTxt)).setText("Current temperature" + this.temp);
            ((TextView)findViewById(R.id.MaxTempTxt)).setText("Max: " + this.max);
            ((TextView)findViewById(R.id.MinTempTxt)).setText("Min:" + this.min);
            ((TextView)findViewById(R.id.UVRatingTxt)).setText("UV: " + this.UV);

            ((ImageView)findViewById(R.id.WeatherImg)).setImageBitmap(picture);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            ((ProgressBar)findViewById(R.id.ProgressBar)).setProgress(values[0]);
        }

        private void parseTemps(InputStream input) {
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( input , "UTF-8");

                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if(eventType == XmlPullParser.START_TAG) {
                        if(xpp.getName().equals("temperature")) {
                            this.temp = xpp.getAttributeValue(null, "value");
                            this.min = xpp.getAttributeValue(null, "min");
                            this.max = xpp.getAttributeValue(null, "max");
                        }

                        if(xpp.getName().equals("weather")) {
                            this.icon = xpp.getAttributeValue(null, "icon");
                        }
                    }
                    eventType = xpp.next();
                }

            } catch (Exception e) {
                Log.e("parseTemps", e.toString());
            }

        }

        private void fetchImage() {
            FileInputStream fis = null;
            try {
                Log.i("fetchImage", "Looking for " + this.icon + ".png");
                fis = openFileInput(this.icon + ".png");
                Log.i("fetchImage", "Image found locally");
                this.picture = BitmapFactory.decodeStream(fis);

                return;
            } catch (FileNotFoundException e) {
                Log.i("fetchImage", "Image not found locally - must download");
            }


            String url = "https://openweathermap.org/img/w/" + this.icon + ".png";

            try {
                Bitmap image = null;
                URL address = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) address.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    image = BitmapFactory.decodeStream(connection.getInputStream());
                }

                FileOutputStream outputStream = openFileOutput( this.icon + ".png", Context.MODE_PRIVATE);
                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                outputStream.flush();
                outputStream.close();

                this.picture = image;

            } catch (Exception e) {
                Log.e("fetchImage", e.toString());
            }


        }

        private void parseUV(InputStream input) {
            // json is UTF-8 by default
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                JSONObject uvReport = new JSONObject(result);

                this.UV = String.valueOf(uvReport.getDouble("value"));

                Log.i("UV", this.UV);
            } catch (Exception e) {
                Log.e("parseUV", e.toString());
            }

        }

        private InputStream fetch(String url) {
            HttpURLConnection urlConnection = null;
            try {
                URL address = new URL(url);

                urlConnection = (HttpURLConnection) address.openConnection();

                return new BufferedInputStream(urlConnection.getInputStream());
            } catch (Exception e) {
                Log.e(WeatherForecast.class.toString(), e.toString());
            } finally {
                urlConnection.disconnect();
            }

            return null;
        }
    }
}