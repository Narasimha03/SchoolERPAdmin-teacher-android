package com.example.medianet.proschool;

import android.Manifest;
import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medianet.proschool.suresh.feemodule.feecollection.AddFeeCollectionFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JANI on 18-06-2017.
 */

public class AddStationFragment extends Fragment implements AllStationsBackTask.AllStations {

    View stationView;
    EditText editName, editCode, editLocation;
    //Geo Location
    double latitude, longitude;
    Geocoder geocoder;
    GPSTracker gps;
    //List<Address> addresses;
    String address, city, state, country, postalCode, knownName;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 0;
    public static final int REQUEST_CAMERA = 2;
    public static final int REQUEST_GPS_PERMISSION = 1;
    //
    RecyclerView recycler_view;
    StationAdapter stationAdapter;
    ArrayList<Stations> listStations = new ArrayList<Stations>();
    //
    FloatingActionButton fab;
    LinearLayout fabLayout1;
    View fabBGLayout;
    FrameLayout frameLayout;
    boolean isFABOpen=false;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    Context mContext;
    String schoolId;
    String role;


    public AddStationFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationpermission();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        stationView = inflater.inflate(R.layout.add_station_layout_two, container, false);
        mContext=getActivity();


        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        role = sharedPreferences.getString(Constants.rolePref, "");

        new AllStationsBackTask(getActivity(), AddStationFragment.this).execute(schoolId);

        fabLayout1= (LinearLayout) stationView.findViewById(R.id.fabLayout1);

        fab = (FloatingActionButton) stationView.findViewById(R.id.fab);

        frameLayout=(FrameLayout)stationView.findViewById(R.id.frameLayout);
        fabBGLayout=stationView.findViewById(R.id.fabBGLayout);

        fabLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View alertLayout = layoutInflater.inflate(R.layout.add_station_dialog, null);
                editName = (EditText) alertLayout.findViewById(R.id.editName);
                editCode = (EditText) alertLayout.findViewById(R.id.editCode);
                editLocation = (EditText) alertLayout.findViewById(R.id.editLocation);
                // Adding Current Location....
                gps = new GPSTracker(getActivity());
                if (gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    editLocation.setText(latitude + ", " + longitude);
                    //Toast.makeText(SingleToDoActivity.this, "Your Location is - Lat: " + latitude + "Long:" + longitude, Toast.LENGTH_SHORT).show();
                }
                builder = new AlertDialog.Builder(getActivity());
                builder.setPositiveButton("Add Station", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JSONObject addObject = new JSONObject();
                        try {
                            addObject.put("station_name", editName.getText().toString());
                            addObject.put("station_code", editCode.getText().toString());
                            addObject.put("station_geo_location", editLocation.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (addObject.length() > 0) {
                            new AddStationBackTask(getActivity()).execute(String.valueOf(addObject), schoolId);
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setView(alertLayout);
                dialog = builder.create();
                dialog.show();
                closeFABMenu();
            }
        });
        if (role.equals("admin")) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    //    animateFAB();
                    if (!isFABOpen) {
                        showFABMenu();
                    } else {
                        closeFABMenu();
                    }
                }
            });
        }
        else  if (role.equals("teacher")) {
            fab.hide();
        }


        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
                //animateFAB();
            }
        });
        // to set action bar title....
      //  ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Stations");

        return stationView;
    }

    private void showFABMenu() {
        isFABOpen = true;
        fabLayout1.setVisibility(View.VISIBLE);

        fabBGLayout.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));



    }

    private void closeFABMenu() {
        isFABOpen = false;
        fabBGLayout.setVisibility(View.GONE);
        fab.animate().rotationBy(-180);
        fabLayout1.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator)
            {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    fabLayout1.setVisibility(View.GONE);



                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*
        "{
""station_name"": ""Test Station"",

""station_code"": ""2342"",
""station_geo_location"":""523423234.13""
}"
         */
        recycler_view = (RecyclerView) stationView.findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);
    }

    @Override
    public void allStations(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            listStations.clear();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("stations");
            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject stationObject = jsonArray.getJSONObject(count);
                    Stations stations = new Stations(stationObject.getString("_id"), stationObject.getString("station_id"),
                            stationObject.getString("station_name"), stationObject.getString("station_code"),
                            stationObject.getString("station_geo_location"), stationObject.getString("status"));
                    listStations.add(stations);
                    count++;
                }
                stationAdapter = new StationAdapter(getActivity(), listStations);
                recycler_view.setAdapter(stationAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                stationAdapter.notifyDataSetChanged();
            } else {
                recycler_view.setVisibility(View.GONE);
            }
        }
    }

    /*
    "{
  ""stations"": [
    {
      ""_id"": ""592525dc1bd0aa23a8e5f3e4"",
      ""station_id"": ""STN-1"",
      ""station_name"": ""Test Statiom"",
      ""station_code"": ""1232"",
      ""station_geo_location"": ""1111233212"",
      ""status"": 1
    }
  ]
}"
     */
    private void locationpermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an expanation to the loigin_user *asynchronously* -- don't block
                // this thread waiting for the loigin_user's response! After the loigin_user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(getActivity(), "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case REQUEST_GPS_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "GPS Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "GPS Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
