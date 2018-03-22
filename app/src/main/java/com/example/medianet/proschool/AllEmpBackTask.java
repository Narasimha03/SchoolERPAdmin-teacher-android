package com.example.medianet.proschool;

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

public class AllEmpBackTask extends AsyncTask<String, Void, String> {

    Context mContext;
    AllEmployees allEmployees;

    public AllEmpBackTask(Context mContext, AllEmployees allEmployees) {
        this.mContext = mContext;
        this.allEmployees = allEmployees;
    }

    public interface AllEmployees {
        void allEmployees(String result) throws JSONException;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String json_string;
        String schoolId = params[0];
        String json_url = Constants.listEmpUrl + schoolId;
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
            if (allEmployees != null) {
                try {
                    allEmployees.allEmployees(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
