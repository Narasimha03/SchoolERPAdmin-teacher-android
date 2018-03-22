package com.example.medianet.proschool;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.medianet.proschool.suresh.deletebacktask.VehicleDeleteBackTask;
import com.example.medianet.proschool.suresh.swipetodelete.RecyclerItemTouchHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JANI on 24-06-2017.
 */

public class AddVehicleFragment extends Fragment implements AllVehicleBackTask.AllVehicle,SingleVehicleDetailsBackTask.SingleVehicle, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
    private boolean add = false;

    View addVehicleView;
    //
    EditText editCode, editNumber;
    //
    RecyclerView recycler_view;
    ArrayList<Vehicles> listVehicles = new ArrayList<Vehicles>();
    VehicleAdapter vehicleAdapter;
    //
    AlertDialog.Builder builder;
    AlertDialog dialog;


    FloatingActionButton fab, fab1, fab2, fab3, fab4, fab5, fab6;
    LinearLayout fabLayout1, fabLayout2, fabLayout3, fabLayout4, fabLayout5, fabLayout6;
    View fabBGLayout;
    boolean isFABOpen = false;
    SharedPreferences sharedPreferences;
    FrameLayout frameLayout;
    String id, name, code;
    CoordinatorLayout coordinatorLayout;
    Vehicles vehicles;
    Context mContext;
    String schoolId;
    String role;

    public AddVehicleFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addVehicleView = inflater.inflate(R.layout.add_vehicle_layout, container, false);

        mContext=getActivity();


        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        role = sharedPreferences.getString(Constants.rolePref, "");

        new AllVehicleBackTask(getActivity(), AddVehicleFragment.this).execute(schoolId);

        fabLayout1 = (LinearLayout) addVehicleView.findViewById(R.id.fabLayout1);

        fab = (FloatingActionButton) addVehicleView.findViewById(R.id.fab);
        fab1 = (FloatingActionButton) addVehicleView.findViewById(R.id.fab1);

        coordinatorLayout=(CoordinatorLayout)addVehicleView.findViewById(R.id.coordinator_layout);



        fabBGLayout = addVehicleView.findViewById(R.id.fabBGLayout);
        frameLayout=addVehicleView.findViewById(R.id.frameLayout);


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

        //   fab3 = (FloatingActionButton) addVehicleView.findViewById(R.id.fab);
        fabLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View alertLayout = layoutInflater.inflate(R.layout.add_vehicle_dialog, null);
                editCode = (EditText) alertLayout.findViewById(R.id.editCode);
                editNumber = (EditText) alertLayout.findViewById(R.id.editNumber);
                builder = new AlertDialog.Builder(getActivity());

                builder.setPositiveButton("Add Vehicle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JSONObject addObject = new JSONObject();


                        try {
                            vehicles=new Vehicles();
                            vehicles.setName(editCode.getText().toString());
                            vehicles.setCode(editNumber.getText().toString());

                            addObject.put("vehicle_name",vehicles.getName());
                            addObject.put("vehicle_code",vehicles.getCode());
                            vehicleAdapter.addItem(vehicles);
                            vehicleAdapter.notifyItemInserted(listVehicles.size() - 1);
                            recycler_view.scrollToPosition(vehicleAdapter.getItemCount() - 1);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        new AddVehicleBackTask(getActivity()).execute(String.valueOf(addObject), schoolId);
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
        //
        recycler_view = (RecyclerView) addVehicleView.findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);
        // to set action bar title....
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Vehicle");

        return addVehicleView;
    }

   /* @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
          //  getFragmentManager().beginTransaction().detach(this).attach(this).commit();

            AddVehicleFragment addVehicleFragment=new AddVehicleFragment();
            setFragment(addVehicleFragment);
        }
    }*/

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
            public void onAnimationStart(Animator animator) {

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
    }

    @Override
    public void allVehicle(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            listVehicles.clear();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("vehicles");
            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject vehicleObject = jsonArray.getJSONObject(count);

                    Vehicles vehicles = new Vehicles(vehicleObject.getString("vehicle_details_id"), vehicleObject.getString("vehicle_number"),
                            vehicleObject.getString("vehicle_name"), vehicleObject.getString("school_id"),
                            vehicleObject.getString("vehicle_details_id"));
                    listVehicles.add(vehicles);
                    count++;
                }

                vehicleAdapter = new VehicleAdapter(getActivity(), listVehicles);
                /* new VehicleAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(Vehicles item) {
                        id=item.getStatus();
                        code=item.getCode();
                        name=item.getName();
                     *//*   System.out.println("vehicles single item"+" "+id+" "+code+" "+name);
                        Bundle bundle = new Bundle();
                        bundle.putString("id", id);
                        bundle.putString("code", code);
                        bundle.putString("name", name);
                        EditorDeleteVehicleFragment editorDeleteVehicleFragment=new EditorDeleteVehicleFragment();
                        editorDeleteVehicleFragment.setArguments(bundle);
                        setFragment(editorDeleteVehicleFragment);*//*


                     //   SharedPreferences.Editor editor = sharedPreferences.edit();
                      //  editor.putString("id", id);
                      //  editor.putString("code", code);
                      //  editor.putString("name", name);

                      //  editor.apply();
                        new SingleVehicleDetailsBackTask(getActivity(), AddVehicleFragment.this).execute(id);

                        // Toast.makeText(getContext(), item.getId()+" "+item.getName()+""+"Item Clicked", Toast.LENGTH_LONG).show();

                    }*/


              //  });
                recycler_view.setItemAnimator(new DefaultItemAnimator());
                recycler_view.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

                //recycler_view.smoothScrollToPosition(position);
                recycler_view.setAdapter(vehicleAdapter);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                vehicleAdapter.notifyDataSetChanged();
                ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
                new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recycler_view);

            } else {
                recycler_view.setVisibility(View.GONE);
            }
        }

    }

    private void setFragment(EditorDeleteVehicleFragment editorDeleteVehicleFragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, editorDeleteVehicleFragment)
                // .addToBackStack(null)
                .commit();
    }

  /*  public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                // .addToBackStack(null)
                .commit();
    }
*/
    @Override
    public void singleVehicle(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            listVehicles.clear();
          JSONObject jsonObject = new JSONObject(result);
           // Toast.makeText(getContext(), jsonObject + "Item Clicked", Toast.LENGTH_LONG).show();
           System.out.println("vehicle"+" "+jsonObject);
            //JSONArray jsonArray=new JSONArray(result);
          //  JSONArray jsonArray = jsonObject.getJSONArray("vehicle_details");
          //  System.out.println("vehicle Array"+jsonArray);

            if (jsonObject.length() > 0) {
                int count = 0;
                while (count < jsonObject.length()) {
                  //  JSONObject vehicleObject = jsonObject.getJSONObject(count);

                    Vehicles vehicles = new Vehicles(jsonObject.getString("_id"), jsonObject.getString("vehicle_number"),
                            jsonObject.getString("vehicle_name"), jsonObject.getString("school_id"),
                            jsonObject.getString("vehicle_details_id"));
                    listVehicles.add(vehicles);
                    count++;
                }

            }
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        //if (direction == ItemTouchHelper.LEFT) {

            if (viewHolder instanceof VehicleAdapter.ViewHolder) {
                // get the removed item name to display it in snack bar
                String name = listVehicles.get(viewHolder.getAdapterPosition()).getStatus();

                // backup of removed item for undo purpose
                final Vehicles deletedItem = listVehicles.get(viewHolder.getAdapterPosition());
                final int deletedIndex = viewHolder.getAdapterPosition();

                // remove the item from recycler view
                vehicleAdapter.removeItem(viewHolder.getAdapterPosition());

                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View alertLayout = layoutInflater.inflate(R.layout.delete_vehicle_dialog, null);
               // editCode = (EditText) alertLayout.findViewById(R.id.editCode);
             //   editNumber = (EditText) alertLayout.findViewById(R.id.editNumber);
                builder = new AlertDialog.Builder(getActivity());
                builder.setPositiveButton("Delete Vehicle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new VehicleDeleteBackTask(getActivity()).execute(name);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        vehicleAdapter.restoreItem(deletedItem, deletedIndex);

                    }
                });
                builder.setView(alertLayout);
                dialog = builder.create();
                dialog.show();
            }

        if (direction == ItemTouchHelper.RIGHT) {
             Toast.makeText(getContext(), name + "Item Clicked", Toast.LENGTH_LONG).show();

        }
        }





         //   Toast.makeText(getContext(), name + "Item Clicked", Toast.LENGTH_LONG).show();

          /*  // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
           snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    vehicleAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });*/
         /*   snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();*/
        }






    /*
    "{
    ""vehicles"": [
        {
            ""_id"": ""594a453183c11c17246a66b0"",
            ""vehicle_code"": ""1234"",
            ""vehicle_name"": ""test"",
            ""school_id"": ""SCH-9273"",
            ""status"": 1
        }
    ]
}"
     */

