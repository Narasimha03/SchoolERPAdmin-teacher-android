package com.example.medianet.proschool.suresh.examination;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.medianet.proschool.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by JANI on 17-06-2017.
 */

public class AddMarkBackTask extends AsyncTask<String, Void, String>{

    Context mContext;

    public AddMarkBackTask(Context mContext){
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
      //  :exam_sch_id/:exam_paper_id/:student_id/:section_id/:class_id')
        String json_string;
        String json_emp = params[0];
        String examSchId = params[1];
        String examPaperId = params[2];
        String studentId = params[3];
        String sectionId = params[4];

        String classId = params[5];

        String json_url = Constants.addEvalMarkUrl + examSchId+"/"+examPaperId + "/" + studentId+"/" +sectionId+"/" +classId ;
        try {
            URL url = new URL(json_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(json_emp);
            //bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
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
        if (result != null && !result.isEmpty() && result.equalsIgnoreCase("TRUE")){
            Toast.makeText(mContext, "Marks Added...!", Toast.LENGTH_LONG).show();
        }
    }
}
