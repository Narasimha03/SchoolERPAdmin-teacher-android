package com.example.medianet.proschool.suresh.attendancemodule.daywisereport;

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

public class AllAttendanceReportByDayBackTask extends AsyncTask<String, Void, String> {

    Context mContext;
    AllAttendanceByday allAttendanceByday;

    public AllAttendanceReportByDayBackTask(Context mContext, AllAttendanceByday allAttendanceByday) {
        this.mContext = mContext;
        this.allAttendanceByday = allAttendanceByday;
    }

    public interface AllAttendanceByday {
        void allAttendanceByday(String result) throws JSONException;
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
        String sectionId = params[2];



        String json_url = Constants.attendancechartbyday + selectDate+ "/" + classId+ "/" +sectionId;
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
            if (allAttendanceByday != null) {
                try {
                    allAttendanceByday.allAttendanceByday(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
