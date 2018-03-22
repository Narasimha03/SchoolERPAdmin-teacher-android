package com.example.medianet.proschool;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by JANI on 18-06-2017.
 */

public class BusRouteFragment extends Fragment implements RouteBackGroundTask.OnRouteResponse, AllStationsBackTask.AllStations,
        AllVehicleBackTask.AllVehicle {

    View busRouteView;
    //
    EditText editTitle, editNumber, editPickUpTime, editDropTime;
    // Route Spinner....
    String vehicleKey, selectedVehicle;
    Spinner vehicleSpinner;
    Map<String, String> vehicleMap = new LinkedHashMap<String, String>();
    ArrayList<String> vehicleList;
    ArrayAdapter<String> vehicleAdapter;
    // Station Spinner...
    String stationKey, selectedStation;
    Spinner stationSpinner;
    Map<String, String> stationMap = new LinkedHashMap<String, String>();
    ArrayList<String> stationList;
    ArrayAdapter<String> stationAdapter;
    //Button....
    Button addBusRoute;
    //RecyclerView....
    RecyclerView recycler_view;
    RoutesAdapter routesAdapter;
    ArrayList<Routes> listRoute = new ArrayList<Routes>();
    //
    TextView noText;
    Context mContext;
    String schoolId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        busRouteView = inflater.inflate(R.layout.add_bus_route_layout, container, false);
        mContext=getActivity();
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        new RouteBackGroundTask(getActivity(), BusRouteFragment.this).execute(schoolId);
        new AllStationsBackTask(getActivity(), BusRouteFragment.this).execute(schoolId);
        new AllVehicleBackTask(getActivity(), BusRouteFragment.this).execute(schoolId);
        // Route Spinner....
        vehicleSpinner = (Spinner) busRouteView.findViewById(R.id.vehicleSpinner);
        vehicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedVehicle = vehicleSpinner.getSelectedItem().toString();
                vehicleKey = (String) getKeyFromValue(vehicleMap, selectedVehicle);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Station Spinner...
        stationSpinner = (Spinner) busRouteView.findViewById(R.id.stationSpinner);
        stationMap.put("", "-- Select --");
        stationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStation = stationSpinner.getSelectedItem().toString();
                stationKey = (String) getKeyFromValue(stationMap, selectedStation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        recycler_view = (RecyclerView) busRouteView.findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);
        noText = (TextView) busRouteView.findViewById(R.id.noText);
        // to set action bar title....
       // ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Bus Route");

        return busRouteView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editTitle = (EditText) busRouteView.findViewById(R.id.editTitle);
        editNumber = (EditText) busRouteView.findViewById(R.id.editNumber);
        editPickUpTime = (EditText) busRouteView.findViewById(R.id.editPickUpTime);
        editPickUpTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editPickUpTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        editDropTime = (EditText) busRouteView.findViewById(R.id.editDropTime);
        editDropTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editDropTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        addBusRoute = (Button) busRouteView.findViewById(R.id.addBusRoute);
        addBusRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("route_title", editTitle.getText().toString());
                    jsonObject.put("vehicle_code", vehicleKey);
                    jsonObject.put("station", selectedStation);
                    jsonObject.put("pickup_time", editPickUpTime.getText().toString());
                    jsonObject.put("drop_time", editDropTime.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new AddBusRouteBackTask(getActivity()).execute(String.valueOf(jsonObject), schoolId);
            }
        });
        /*
        "{
     ""route_title"":""Test Route"",
    ""vehicle_code"": ""1234"",
     ""station"" : ""A101"",
     ""pickup_time"":""10.30AM"",
    ""drop_time"": ""4.30PM""
}"
         */
    }

    @Override
    public void OnRouteResponse(String route) throws JSONException {
        if (route != null && !route.isEmpty()) {
            listRoute.clear();
            JSONObject jsonObject = new JSONObject(route);
            JSONArray jsonArray = jsonObject.getJSONArray("bus_routes");
            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject routeObject = jsonArray.getJSONObject(count);
                    Routes routes = new Routes(routeObject.getString("_id"), routeObject.getString("route_id"), routeObject.getString("school_id"),
                            routeObject.getString("route_title"), routeObject.getString("vehicle_code"), routeObject.getString("station"),
                            routeObject.getString("pickup_time"), routeObject.getString("drop_time"));
                    listRoute.add(routes);
                    count++;
                }
                //
                routesAdapter = new RoutesAdapter(getActivity(), listRoute);
                recycler_view.setAdapter(routesAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                routesAdapter.notifyDataSetChanged();

            } else {
                recycler_view.setVisibility(View.GONE);
                noText.setVisibility(View.VISIBLE);
            }
        }
    }
    /*
    "{
    ""bus_routes"": [

        {
            ""_id"": ""59421a684b53ac22f432945f"",
            ""route_id"": ""ROUTE-2"",
            ""school_id"": ""SCH-9273"",
            ""route_title"": ""Test Route"",
            ""vehicle_code"": ""1234"",
            ""station"": ""A101"",
            ""pickup_time"": ""10.30AM"",
            ""drop_time"": ""4.30PM"",
            ""status"": 1
        }
    ]
}"
     */

    @Override
    public void allStations(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("stations");
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject stationObject = jsonArray.getJSONObject(count);
                stationMap.put(stationObject.getString("station_id"), stationObject.getString("station_name"));
                count++;
            }
            stationList = new ArrayList<String>(stationMap.values());
            //
            stationAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stationList);
            stationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            stationSpinner.setAdapter(stationAdapter);
            stationAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void allVehicle(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            vehicleMap.clear();
            vehicleMap.put("", "-- select --");
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("vehicles");
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject vehicleObject = jsonArray.getJSONObject(count);
                vehicleMap.put(vehicleObject.getString("vehicle_number"), vehicleObject.getString("vehicle_name"));
                count++;
            }
            vehicleList = new ArrayList<String>(vehicleMap.values());
            // Creating Adapter
            vehicleAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, vehicleList);
            vehicleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vehicleSpinner.setAdapter(vehicleAdapter);
            vehicleAdapter.notifyDataSetChanged();
        }
    }
}
