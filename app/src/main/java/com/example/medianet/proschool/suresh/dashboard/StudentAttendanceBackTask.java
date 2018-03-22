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

public class StudentAttendanceBackTask extends AsyncTask<String, Void, String> {

    Context mContext;
    StudentAttendanceGraph studentAttendanceGraph;


    public StudentAttendanceBackTask(Context mContext, StudentAttendanceGraph studentAttendanceGraph) {
        this.mContext = mContext;
        this.studentAttendanceGraph = studentAttendanceGraph;
    }


    public interface StudentAttendanceGraph {
        void studentAttendanceGraph(String studentAttendanceGraph) throws JSONException;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String json_string;
        String selectDate = params[0];
        String classId = params[1];
        String schoolId = params[2];

        //  String sectionId = params[2];



        String json_url = Constants.studentAttendanceGraph + selectDate+ "/" + classId+"/"+schoolId;
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
            if (studentAttendanceGraph != null) {
                try {
                    studentAttendanceGraph.studentAttendanceGraph(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
