package com.example.medianet.proschool;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import pub.devrel.easypermissions.EasyPermissions;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.content.Context.MODE_PRIVATE;
import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by JANI on 06-05-2017.
 */

public class StudentAdmissionActivity extends AppCompatActivity implements ClassBackGroundTask.OnClassResponse, SectionBackGroundTask.OnSectionResponse,
        RouteBackGroundTask.OnRouteResponse {
    View studentAdmissionLayout;
    // EditText....
    EditText editAdmissionNo, editRollNo, editFirstName, editLastName, editDob, editReligion, editMobile, editEmail, editAdmDate,
            editFatherName, editFatherMobile, editMotherName, editMotherMobile, editFatherOcc, editMotherOcc, editGuardianName, editGuardianRelation,
            editGuardianPhone, editGuardianOcc, editGuardianAddress, editCurrentAddress, editPermanentAddress, editAadhaarNo;
    // Button....
    Button saveBtn;
    // Class Spinner....
    String classKey, selectedClass;
    Spinner classSpinner;
    Map<String, String> classMap = new LinkedHashMap<String, String>();
    ArrayList<String> classList;
    ArrayAdapter<String> classAdapter;
    // Section Spinner....
    String sectionKey, selectedSection;
    Spinner sectionSpinner;
    Map<String, String> sectionMap = new LinkedHashMap<String, String>();
    ArrayList<String> sectionList;
    ArrayAdapter<String> sectionAdapter;
    // Gender Spinner....
    String selectedGender;
    Spinner genSpinner;
    List<String> genList;
    ArrayAdapter<String> genAdapter;
    // Category Spinner...
    String selectedCat;
    Spinner catSpinner;
    List<String> catList;
    ArrayAdapter<String> catAdapter;
    // Route Spinner....
    String routeKey, selectedRoute;
    Spinner routeSpinner;
    Map<String, String> routeMap = new LinkedHashMap<String, String>();
    ArrayList<String> routeList;
    ArrayAdapter<String> routeAdapter;
    // Upload Image Layout....
    RelativeLayout uploadImgLayout;
    AlertDialog.Builder imageBuilder;
    AlertDialog imageDialog;
    Uri filePath, mCapturedImageURI;
    public static final int REQUEST_GALLERY = 3;
    public static final int REQUEST_CAMERA = 4;
    public static final int REQUEST_FILE_SELECT = 6;
    LinearLayout photoLayout, docAttachmentLayout, uploadDocLayout;
    String path, file_name, compressedImg, reportName;
String schoolId;
Context mContext;
    public StudentAdmissionActivity() {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_admission_layout);
        mContext = this;

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        new ClassBackGroundTask(mContext, StudentAdmissionActivity.this).execute(schoolId);
        new RouteBackGroundTask(mContext, StudentAdmissionActivity.this).execute(schoolId);

        Toolbar toolbar = (Toolbar) findViewById(R.id.test_toolabr);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        editAdmissionNo = (EditText) findViewById(R.id.editAdmissionNo);
        editRollNo = (EditText) findViewById(R.id.editRollNo);
        editFirstName = (EditText) findViewById(R.id.editFirstName);
        editLastName = (EditText) findViewById(R.id.editLastName);
        editReligion = (EditText) findViewById(R.id.editReligion);
        editMobile = (EditText) findViewById(R.id.editMobile);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editFatherName = (EditText)findViewById(R.id.editFatherName);
        editFatherMobile = (EditText) findViewById(R.id.editFatherMobile);
        editMotherName = (EditText) findViewById(R.id.editMotherName);
        editMotherMobile = (EditText) findViewById(R.id.editMotherMobile);
        editFatherOcc = (EditText) findViewById(R.id.editFatherOcc);
        editMotherOcc = (EditText) findViewById(R.id.editMotherOcc);
        editGuardianName = (EditText) findViewById(R.id.editGuardianName);
        editGuardianRelation = (EditText) findViewById(R.id.editGuardianRelation);
        editGuardianPhone = (EditText) findViewById(R.id.editGuardianPhone);
        editGuardianOcc = (EditText) findViewById(R.id.editGuardianOcc);
        editGuardianAddress = (EditText) findViewById(R.id.editGuardianAddress);
        editCurrentAddress = (EditText) findViewById(R.id.editCurrentAddress);
        editPermanentAddress = (EditText) findViewById(R.id.editPermanentAddress);
        editAadhaarNo = (EditText) findViewById(R.id.editAadhaarNo);
        saveBtn = (Button) findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject saveObject = new JSONObject();
                try {
                    saveObject.put("first_name", editFirstName.getText().toString());
                    saveObject.put("last_name", editLastName.getText().toString());
                    saveObject.put("gender", selectedGender);
                    saveObject.put("dob", editDob.getText().toString());
                    saveObject.put("aadhar_no", editAadhaarNo.getText().toString());
                    saveObject.put("phone", editMobile.getText().toString());
                    saveObject.put("email", editEmail.getText().toString());
                    saveObject.put("category", selectedCat);
                    saveObject.put("admission_date", editAdmDate.getText().toString());
                    saveObject.put("roll_no", editRollNo.getText().toString());
                    saveObject.put("academic_year", "2017");
                    saveObject.put("bus_route_id", routeKey);
                    saveObject.put("cur_address", editCurrentAddress.getText().toString());
                    saveObject.put("perm_address", editPermanentAddress.getText().toString());
                    saveObject.put("father_name", editFatherName.getText().toString());
                    saveObject.put("father_contact", editFatherMobile.getText().toString());
                    saveObject.put("father_occupation", editFatherOcc.getText().toString());
                    saveObject.put("mother_name", editMotherName.getText().toString());
                    saveObject.put("mother_contact", editMotherMobile.getText().toString());
                    saveObject.put("mother_occupation", editMotherOcc.getText().toString());
                    saveObject.put("gaurdian_name", editGuardianName.getText().toString());
                    saveObject.put("gaurdian_contact", editGuardianPhone.getText().toString());
                    saveObject.put("gaurdian_relation", editGuardianRelation.getText().toString());
                    saveObject.put("gaurdian_address", editGuardianAddress.getText().toString());
                    saveObject.put("gaurdian_occupation", editGuardianOcc.getText().toString());
                  /*  saveObject.put("status",
                            "1");*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (saveObject.length() > 0) {
                    StdADMBackTask stdADMBackTask = new StdADMBackTask(mContext);
                    stdADMBackTask.execute(String.valueOf(saveObject), sectionKey);
                   /* // Validating userid, pwd in server....
                    LoginBackGroundTask loginBackGroundTask = new LoginBackGroundTask(mContext);
                    loginBackGroundTask.execute(String.valueOf(loginObject)); */
                }
            }
        });

        // class spinner....
        classSpinner = (Spinner) findViewById(R.id.classSpinner);
        classList = new ArrayList<String>();
        classMap.put("", "-- Select --");
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClass = classSpinner.getSelectedItem().toString();
                classKey = (String) getKeyFromValue(classMap, selectedClass);
                // Getting Sections From Server....
                if (classKey != null && !classKey.isEmpty()) {
                    new SectionBackGroundTask(mContext, StudentAdmissionActivity.this).execute(classKey);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // section spinner....
        sectionSpinner = (Spinner) findViewById(R.id.sectionSpinner);
        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSection = sectionSpinner.getSelectedItem().toString();
                sectionKey = (String) getKeyFromValue(sectionMap, selectedSection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // gender spinner
        genSpinner = (Spinner) findViewById(R.id.genSpinner);
        genList = new ArrayList<String>();
        genList.add("-- Select ---");
        genList.add("Male");
        genList.add("Female");
        // setting adapter for gender spinner
        genAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, genList);
        genAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genSpinner.setAdapter(genAdapter);
        genAdapter.notifyDataSetChanged();
        //
        genSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = genSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // category spinner
        catSpinner = (Spinner) findViewById(R.id.catSpinner);
        catList = new ArrayList<String>();
        catList.add("-- Select --");
        catList.add("General");
        catList.add("Written Test");
        catList.add("PH");
        catList.add("Special Category");
        // setting adapter for category spinner
        catAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, catList);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpinner.setAdapter(catAdapter);
        catAdapter.notifyDataSetChanged();
        //
        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCat = catSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Route Spinner....
        routeSpinner = (Spinner) findViewById(R.id.routeSpinner);
        routeMap.put("", "-- Select --");
        routeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRoute = routeSpinner.getSelectedItem().toString();
                routeKey = (String) getKeyFromValue(routeMap, selectedRoute);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //dob
        editDob = (EditText) findViewById(R.id.editDob);
        editDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int years = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerFragement(), years, month, day);
                datePickerDialog.show();
            }
        });
        //admission date
        editAdmDate = (EditText) findViewById(R.id.editAdmDate);
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        editAdmDate.setText(date);
        editAdmDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int years = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerFragement2(), years, month, day);
                datePickerDialog.show();
            }
        });
        // Upload Image....
        photoLayout = (LinearLayout) findViewById(R.id.photoLayout);
        uploadImgLayout = (RelativeLayout) findViewById(R.id.uploadImgLayout);
        uploadImgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageBuilder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View alertDialog = inflater.inflate(R.layout.image_selection_layout, null);
                LinearLayout photoLayout = (LinearLayout) alertDialog.findViewById(R.id.photoLayout);
                LinearLayout galleryLayout = (LinearLayout) alertDialog.findViewById(R.id.galleryLayout);
                LinearLayout fileLayout = (LinearLayout) alertDialog.findViewById(R.id.fileLayout);
                fileLayout.setVisibility(View.GONE);
                imageBuilder.setView(alertDialog);
                imageDialog = imageBuilder.create();
                imageDialog.show();
                /*photoLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (imageDialog != null && imageDialog.isShowing()) {
                            imageDialog.dismiss();
                        }
                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            // Check Permissions Now
                            // Callback onRequestPermissionsResult interceptado na Activity MainActivity
                            ActivityCompat.requestPermissions(mContext,
                                    new String[]{Manifest.permission.CAMERA},
                                    StudentAdmissionActivity.REQUEST_CAMERA);
                        } else {
                            ContentValues values = new ContentValues();
                            values.put(MediaStore.Images.Media.TITLE, "Image File name");
                            mCapturedImageURI = mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (cameraIntent.resolveActivity(mContext.getPackageManager()) != null) {
                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                                startActivityForResult(cameraIntent, REQUEST_CAMERA);
                            }
                        }
                    }
                });*/

                galleryLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (imageDialog != null && imageDialog.isShowing()) {
                            imageDialog.dismiss();
                        }
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Photo"), REQUEST_GALLERY);
                    }
                });
            }
        });
        //Uploading Documents....
        uploadDocLayout = (LinearLayout) findViewById(R.id.uploadDocLayout);
        docAttachmentLayout = (LinearLayout) findViewById(R.id.docAttachmentLayout);
        docAttachmentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageBuilder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View alertDialog = inflater.inflate(R.layout.image_selection_layout, null);
                LinearLayout photoLayout = (LinearLayout) alertDialog.findViewById(R.id.photoLayout);
                LinearLayout galleryLayout = (LinearLayout) alertDialog.findViewById(R.id.galleryLayout);
                LinearLayout fileLayout = (LinearLayout) alertDialog.findViewById(R.id.fileLayout);
                imageBuilder.setView(alertDialog);
                imageDialog = imageBuilder.create();
                imageDialog.show();
                /*photoLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (imageDialog != null && imageDialog.isShowing()) {
                            imageDialog.dismiss();
                        }
                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            // Check Permissions Now
                            // Callback onRequestPermissionsResult interceptado na Activity MainActivity
                            ActivityCompat.requestPermissions(mContext,
                                    new String[]{Manifest.permission.CAMERA},
                                    StudentAdmissionActivity.REQUEST_CAMERA);
                        } else {
                            ContentValues values = new ContentValues();
                            values.put(MediaStore.Images.Media.TITLE, "Image File name");
                            mCapturedImageURI = mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (cameraIntent.resolveActivity(mContext.getPackageManager()) != null) {
                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                                startActivityForResult(cameraIntent, REQUEST_CAMERA);
                            }
                        }
                    }
                });*/

                galleryLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (imageDialog != null && imageDialog.isShowing()) {
                            imageDialog.dismiss();
                        }
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Photo"), REQUEST_GALLERY);
                    }
                });

                fileLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (imageDialog != null && imageDialog.isShowing()) {
                            imageDialog.dismiss();
                        }
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        //intent.setType("application/pdf");
                        intent.setType("*/*");
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), REQUEST_FILE_SELECT);
                    }
                });
            }
        });
        // to set action bar title....
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Student Admission");

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        // Relative Layout....
        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        LinearLayout.LayoutParams relativeLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayoutParams.setMargins(0, 10, 0, 10);
        relativeLayout.setLayoutParams(relativeLayoutParams);

        ImageView imgReport = new ImageView(mContext);
        RelativeLayout.LayoutParams imgParams = new RelativeLayout.LayoutParams(50, 50);
        imgParams.setMargins(0, 10, 0, 0);
        imgReport.setLayoutParams(imgParams);
        imgReport.setId(R.id.img_id);
        imgReport.setImageResource(R.drawable.file_1);
        relativeLayout.addView(imgReport);

        TextView name = new TextView(mContext);
        RelativeLayout.LayoutParams layoutParamstext = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamstext.addRule(RelativeLayout.RIGHT_OF, R.id.img_id);
        name.setLayoutParams(layoutParamstext);
        name.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        name.setTextSize(15f);
        name.setPadding(30, 0, 0, 0);
        relativeLayout.addView(name);
        String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (requestCode == REQUEST_GALLERY) {
            if (EasyPermissions.hasPermissions(mContext, galleryPermissions)) {
            if (data != null) {
                filePath = data.getData();
                // To get selected image path...
                FilePath filePathClass = new FilePath(mContext);
                path = filePathClass.getFilePath(filePath);
                // Adding uploaded file paths....
                final String uploadFilePath = path;
                // Adding uploaded file names to alert dialog....
                file_name = path.substring(path.lastIndexOf("/") + 1);
                final String extension = path.substring(path.lastIndexOf(".") + 1);

                name.setText(file_name);
                imgReport.setImageResource(R.drawable.picture);

                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("jpg")) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(new File(uploadFilePath)), "image/*");
                            startActivity(intent);
                        }
                    }
                });
            }
            else {
                EasyPermissions.requestPermissions(this, "Access for storage",
                        101, galleryPermissions);
            }



                photoLayout.addView(relativeLayout);

                //uploadDocLayout.addView(relativeLayout);

                // To compress image of higher quality....
                try {
                    compressedImg = new ImageCompressor(mContext).execute(path).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                // To upload image to server....
                File uploadingFile = new File(compressedImg);
               /* // Uploading files to server....
                UploadFileBackGroundTask uploadFileBackGroundTask = new UploadFileBackGroundTask(ctx);
                uploadFileBackGroundTask.execute(compressedImg);*/
            }
        }
        if (requestCode == REQUEST_CAMERA) {
            // To get path of captured image....
            FilePath filePathClass = new FilePath(mContext);
            String actualPath = filePathClass.getFilePath(mCapturedImageURI);
            // Adding uploaded file paths....
            final String uploadFilePath = actualPath;
            // Adding uploaded file names to alert dialog....
            file_name = actualPath.substring(actualPath.lastIndexOf("/") + 1);
            final String extension = actualPath.substring(actualPath.lastIndexOf(".") + 1);

            name.setText(file_name);
            imgReport.setImageResource(R.drawable.picture);

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("jpg")) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(new File(uploadFilePath)), "image/*");
                        startActivity(intent);
                    }
                }
            });

            photoLayout.addView(relativeLayout);

            //uploadDocLayout.addView(relativeLayout);
            // To compress image of higher quality....
            try {
                compressedImg = new ImageCompressor(mContext).execute(actualPath).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            // To upload image to server....
            File uploadingFile = new File(compressedImg);

        }
        if (requestCode == REQUEST_FILE_SELECT) {
            filePath = data.getData();
            // To get selected image path...
            FilePath filePathClass = new FilePath(mContext);
            String path = filePathClass.getFilePath(filePath);
            // Adding uploaded file paths....
            final String uploadFilePath = path;
            // Adding uploaded file names to alert dialog....
            file_name = path.substring(path.lastIndexOf("/") + 1);
            final String extension = path.substring(path.lastIndexOf(".") + 1);

            if (extension.equals("jpeg") || extension.equals("jpg") || extension.equals("png")) {
                try {
                    path = new ImageCompressor(mContext).execute(path).get();
                    imgReport.setImageResource(R.drawable.picture);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            /*if (extension.equals("pdf")) {
                imgReport.setImageResource(R.drawable.pdf);
            }*/

            name.setText(file_name);

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (extension.equals("pdf")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(new File(uploadFilePath)), "application/pdf");
                        startActivity(intent);
                    } else if (extension.equals("jpeg") || extension.equals("jpg") || extension.equals("png")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(new File(uploadFilePath)), "image/*");
                        startActivity(intent);
                    } else if (extension.equals("doc") || extension.equals("docx")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(new File(uploadFilePath)), "text/html");
                        startActivity(intent);
                    }
                }
            });

            uploadDocLayout.addView(relativeLayout);
            // To upload files to server....
            File uploadingFile = new File(path);
            //new BackgroundUploader(ctx, uploadingFile).execute();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClassResponse(String response) throws JSONException {
        if (response != null && !response.isEmpty()) {
            try {
                JSONObject classObject = new JSONObject(response);
                JSONArray classArray = classObject.getJSONArray("school_classes");
                int count = 0;
                while (count < classArray.length()) {
                    JSONObject cObject = classArray.getJSONObject(count);
                    classMap.put(cObject.getString("class_id"), cObject.getString("name"));
                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            classList = new ArrayList<String>(classMap.values());
            // Creating adapter for spinner
            classAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, classList);
            classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            classSpinner.setAdapter(classAdapter);
            classAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSectionResponse(String response) throws JSONException {
        if (response != null && !response.isEmpty()) {
            sectionMap.clear();
            sectionMap.put("", "-- select --");
            JSONObject secObject = new JSONObject(response);
            JSONArray secArray = secObject.getJSONArray("class_sections");
            int count = 0;
            while (count < secArray.length()) {
                JSONObject jsonObject = secArray.getJSONObject(count);
                sectionMap.put(jsonObject.getString("section_id"), jsonObject.getString("name"));
                count++;
            }
            sectionList = new ArrayList<String>(sectionMap.values());
            // Creating adapter for student spinner
            sectionAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, sectionList);
            sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sectionSpinner.setAdapter(sectionAdapter);
            sectionAdapter.notifyDataSetChanged();
            //Log.e("Section", response);
        }
    }

    @Override
    public void OnRouteResponse(String route) throws JSONException {
        if (route != null && !route.isEmpty()) {
            routeMap.clear();
            JSONObject jsonObject = new JSONObject(route);
            JSONArray jsonArray = jsonObject.getJSONArray("bus_routes");
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject routeObject = jsonArray.getJSONObject(count);
                routeMap.put(routeObject.getString("route_id"), routeObject.getString("route_title"));
                count++;
            }
            routeList = new ArrayList<String>(routeMap.values());
            // Creating Adapter
            routeAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, routeList);
            routeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            routeSpinner.setAdapter(routeAdapter);
            routeAdapter.notifyDataSetChanged();
        }
    }

    public class DatePickerFragement implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int years = year;
            int month = monthOfYear;
            int day = dayOfMonth;
            editDob.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(years).append(""));
        }
    }

    public class DatePickerFragement2 implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int years = year;
            int month = monthOfYear;
            int day = dayOfMonth;
            editAdmDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(years).append(""));
        }
    }
}
