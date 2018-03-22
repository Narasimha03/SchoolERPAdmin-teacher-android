package com.example.medianet.proschool.suresh.studentprofile.employeeprofile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.R;

import com.example.medianet.proschool.suresh.studentprofile.backtasks.EmployeeProfileBackTask;
import com.example.medianet.proschool.suresh.studentprofile.backtasks.StudentProfileBackTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by harish on 12/27/2017.
 */

public class EmployeeProfileShowFragment extends Fragment implements EmployeeProfileBackTask.EmployeeProfile {

    View employeeProfileView;
    Context mContext;
    String employeeId;
    ImageView studentImage;
    TextView studentName, pAdmNo, pRollNo, pGender, pCategory, pContact, pSection, pEmailId,firstName, lastName, dob,martialStatus, spokenLanguages, bloodGroup,academicQualification, basicPay, salaryBand, alternateEmail, currentAddress, currentCity, currentState, currentPin, permanentAddress, permanentState, permanentCity, permanentPin;
    ProgressBar imagePrograss;
    String teacherId;
    String role;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        employeeProfileView = inflater.inflate(R.layout.employee_profile_show_layout_two, container, false);
        mContext = getActivity();

        studentImage = (ImageView) employeeProfileView.findViewById(R.id.imgShow);
        studentName = (TextView) employeeProfileView.findViewById(R.id.studentName);
        pAdmNo = (TextView) employeeProfileView.findViewById(R.id.pAdmNo);




        pSection = (TextView) employeeProfileView.findViewById(R.id.pSection);
        imagePrograss = (ProgressBar) employeeProfileView.findViewById(R.id.progress);

        firstName = employeeProfileView.findViewById(R.id.firstName);
        lastName = employeeProfileView.findViewById(R.id.lastName);
        dob = employeeProfileView.findViewById(R.id.dob);
        pGender = employeeProfileView.findViewById(R.id.pGender);
        pCategory = employeeProfileView.findViewById(R.id.pCategory);
        martialStatus = employeeProfileView.findViewById(R.id.martialStatus);
        spokenLanguages = employeeProfileView.findViewById(R.id.spokenLanguages);
        bloodGroup = employeeProfileView.findViewById(R.id.bloodGroup);

        pRollNo = employeeProfileView.findViewById(R.id.pRollNo);
        academicQualification = employeeProfileView.findViewById(R.id.academicQualification);
        basicPay = employeeProfileView.findViewById(R.id.basicPay);
        salaryBand = employeeProfileView.findViewById(R.id.salaryBand);

        currentAddress = employeeProfileView.findViewById(R.id.currentAddress);
        /*currentCity = employeeProfileView.findViewById(R.id.currentCity);
        currentState = employeeProfileView.findViewById(R.id.currentState);
        currentPin = employeeProfileView.findViewById(R.id.currentPinCode);*/

        permanentAddress = employeeProfileView.findViewById(R.id.empPermanentAddress);
        permanentCity = employeeProfileView.findViewById(R.id.permanentCity);
        permanentState = employeeProfileView.findViewById(R.id.permanentState);
        permanentPin = employeeProfileView.findViewById(R.id.permanentPostalCode);


        pContact = employeeProfileView.findViewById(R.id.pContact);
        pEmailId = employeeProfileView.findViewById(R.id.pEmail);
        alternateEmail = employeeProfileView.findViewById(R.id.alternateEmail);


        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);

        teacherId = sharedPreferences.getString(Constants.empId, "");
        role = sharedPreferences.getString(Constants.rolePref, "");
        SharedPreferences sharedPreferences1 = mContext.getSharedPreferences("employeeInfo", MODE_PRIVATE);
        employeeId = sharedPreferences1.getString(Constants.employeeId, "");


        Log.e("employee id for Profile", employeeId);

        if (role.equals("admin")) {

            new EmployeeProfileBackTask(getActivity(), EmployeeProfileShowFragment.this).execute(employeeId);
        } else if (role.equals("teacher")) {

            new EmployeeProfileBackTask(getActivity(), EmployeeProfileShowFragment.this).execute(teacherId);
        }

        return employeeProfileView;
    }


    @Override
    public void employeeProfile(String result) throws JSONException {

        System.out.println("hello");


        System.out.println("hello profile");

        if (result != null && !result.isEmpty()) {
            //  studentsDetailsArrayList.clear();
            String stdName;
            String imageName = "";
            String imageurl = null;

            String currentAddressString = "";
            String currentCityString = "";
            String currentStateString = "";
            String currentPinString = "";

            String permanentAddressString = "";
            String permanentCityString = "";
            String permanentStateString = "";
            String permanentPinString = "";


            JSONObject student = new JSONObject(result);
            JSONArray jsonArray = student.getJSONArray("employee");


            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject jsonObject = jsonArray.getJSONObject(count);
                    stdName = jsonObject.getString("first_name") + jsonObject.getString("last_name");
                    // Parent Array....


                    System.out.println("student Profile Json Data" + jsonArray);
                    studentName.setText(stdName);

                    firstName.setText(jsonObject.getString("first_name"));
                    lastName.setText(jsonObject.getString("last_name"));
                    dob.setText(jsonObject.getString("dob"));
                    pGender.setText(jsonObject.getString("gender"));
                    pCategory.setText(jsonObject.getString("experience"));

                    pRollNo.setText(jsonObject.getString("job_category"));
                    academicQualification.setText(jsonObject.getString("qualification"));
                    permanentState.setText(jsonObject.getString("state"));
                    permanentPin.setText(jsonObject.getString("postal_code"));

                    basicPay.setText(jsonObject.getString("basic_pay"));
                    bloodGroup.setText(jsonObject.getString("blood_group"));
                    spokenLanguages.setText(jsonObject.getString("spoken_languages"));
                    salaryBand.setText(jsonObject.getString("salary_band"));
                    martialStatus.setText(jsonObject.getString("martial_status"));

                    pContact.setText(jsonObject.getString("phone"));
                    pEmailId.setText(jsonObject.getString("email"));

                    if (!jsonObject.isNull("current_address")) {
                        JSONArray imageArray = jsonObject.getJSONArray("current_address");
                        int p = 0;
                        while (p < imageArray.length()) {
                            JSONObject parentObject = imageArray.getJSONObject(p);
                            currentAddressString = parentObject.getString("cur_address");
                           // currentCityString = parentObject.getString("cur_city");
                          //  currentStateString = parentObject.getString("cur_state");
                          //  currentPinString = parentObject.getString("cur_pincode");

                            p++;
                        }


                    }
                    currentAddress.setText(currentAddressString);
                    /*currentCity.setText(currentCityString);
                    currentState.setText(currentStateString);
                    currentPin.setText(currentPinString);*/


                    if (!jsonObject.isNull("permanent_address")) {
                        JSONArray imageArray = jsonObject.getJSONArray("permanent_address");
                        int p = 0;
                        while (p < imageArray.length()) {
                            JSONObject parentObject = imageArray.getJSONObject(p);
                            permanentAddressString = parentObject.getString("perm_address");
                            permanentCityString = parentObject.getString("perm_city");
                          //  permanentStateString = parentObject.getString("perm_state");
                          //  permanentPinString = parentObject.getString("perm_pincode");

                            p++;
                        }


                    }
                    permanentAddress.setText(permanentAddressString);
                    permanentCity.setText(permanentCityString);
                   /* permanentState.setText(permanentStateString);
                    permanentPin.setText(permanentPinString);*/



                    //studentImage
                    if (!jsonObject.isNull("employeeImage")) {
                        JSONArray imageArray = jsonObject.getJSONArray("employeeImage");
                        int p = 0;
                        while (p < imageArray.length()) {
                            JSONObject parentObject = imageArray.getJSONObject(p);
                            imageName = parentObject.getString("filename");
                            p++;
                        }


                    }
                    imageurl = Constants.singleImage + imageName;
                    count++;
                }


                Glide.with(mContext)
                        .load(imageurl).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        imagePrograss.setVisibility(View.GONE);
                        return false;

                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        imagePrograss.setVisibility(View.GONE);

                        return false;
                    }
                })
                        .into(studentImage);


            } else {

                Toast.makeText(getActivity(), "No Records Found....!", Toast.LENGTH_LONG).show();
            }

        }
    }
}
