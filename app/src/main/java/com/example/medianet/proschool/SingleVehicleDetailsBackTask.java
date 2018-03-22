package com.example.medianet.proschool;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by USER on 11/20/2017.
 */



        import android.content.Context;
        import android.os.AsyncTask;

        import org.json.JSONException;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;

/**
 * Created by JANI on 24-06-2017.
 */

public class SingleVehicleDetailsBackTask extends AsyncTask<String, Void, String> {

    Context mContext;
    SingleVehicle allVehicle;

    public SingleVehicleDetailsBackTask(Context mContext, SingleVehicle allVehicle) {
        this.mContext = mContext;
        this.allVehicle = allVehicle;
    }

    public interface SingleVehicle {
        void singleVehicle(String result) throws JSONException;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String json_string;
        String vehicleId = params[0];
        String json_url = Constants.singleSearchVehicle + vehicleId;
        try {
            URL url = new URL(json_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));
            StringBuilder stringBuilder = new StringBuilder();
            while ((json_string = bufferedReader.readLine()) != null) {
                stringBuilder.append(json_string + "\n");
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuilder.toString().trim();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null && !result.isEmpty()) {
            if (allVehicle != null) {
                try {
                    allVehicle.singleVehicle(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
