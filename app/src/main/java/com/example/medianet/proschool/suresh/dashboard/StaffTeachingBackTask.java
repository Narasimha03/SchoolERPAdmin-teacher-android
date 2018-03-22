package com.example.medianet.proschool.suresh.dashboard;

import android.content.Context;
import android.os.AsyncTask;

import com.example.medianet.proschool.Constants;

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

public class StaffTeachingBackTask extends AsyncTask<String, Void, String> {

    Context contextNonTeaching;
   StaffTeachingGraph staffTeachingGraph;



    public StaffTeachingBackTask(Context mContext, StaffTeachingGraph staffTeachingGraph) {
        this.contextNonTeaching = mContext;
        this.staffTeachingGraph = staffTeachingGraph;
    }


    public interface StaffTeachingGraph {
        void staffTeachingGraph(String nonTeaching) throws JSONException;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String json_string;
        String empCategory = params[0];
        String selectDate = params[1];
        String schoolId = params[2];

        //  String sectionId = params[2];



        String json_url = Constants.empAttendanceByCategory + empCategory+ "/" + selectDate+"/"+schoolId;
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
            if (staffTeachingGraph != null) {
                try {
                    staffTeachingGraph.staffTeachingGraph(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }




    }
}
