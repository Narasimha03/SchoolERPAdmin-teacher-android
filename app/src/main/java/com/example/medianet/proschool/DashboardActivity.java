package com.example.medianet.proschool;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class DashboardActivity extends AppCompatActivity {
    Context mContext = this;
    // Drawer Layout....
    DrawerLayout mDrawerLayout;
    // ActionBarDrawerToggle....
    ActionBarDrawerToggle mDrawerToggle;
    // Fragment....
    FragmentManager fragmentManager;
    FrameLayout frameLayout;
    // Expandable List View....
    ExpandableListView menuListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> menuListTitle;
    ArrayList<Integer> menuListIcon = new ArrayList<Integer>();
    HashMap<String, List<String>> menuListDetail;
    int previousItem = -1;
    /*ArrayList<String> menuList = new ArrayList<String>();
    HashMap<String, List<String>> expandableListDetails = new LinkedHashMap<String, List<String>>();*/
    boolean doubleBackToExitPressedOnce = false;
    // Permissions..
    String permissions[] = {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.WRITE_CONTACTS};
    // Session....
    SharedPreferences sharedPreferences;
    // Navigation Header...
    CircularImageView imgUser;
    ImageView imgText;
    TextView hName, hEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Session....
        sharedPreferences = mContext.getSharedPreferences(getString(R.string.appinfo), MODE_PRIVATE);
        // Permissions....
        ActivityCompat.requestPermissions(this, permissions, Constants.MULTIPLE_PERMISSIONS);
        // Drawer Layout....
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        // frame layout....
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        // Creating Fragments....
        fragmentManager = getSupportFragmentManager();
        // Navigation Header...
        imgUser = (CircularImageView) findViewById(R.id.imgUser);
        imgText = (ImageView) findViewById(R.id.imgText);
        hName = (TextView) findViewById(R.id.name);
        hEmail = (TextView) findViewById(R.id.email);
        //
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color1 = generator.getRandomColor();
        TextDrawable drawable1 = TextDrawable.builder()
                .beginConfig()
                .bold()
                .toUpperCase()
                .endConfig()
                .buildRound("M", color1);
        imgText.setImageDrawable(drawable1);
        imgText.setVisibility(View.VISIBLE);
        // Expandable Menu List....
        menuListView = (ExpandableListView) findViewById(R.id.listMenu);
        menuListDetail = ExpandableMenuDataPump.getData();
        menuListTitle = new ArrayList<String>(menuListDetail.keySet());
        menuListIcon.add(R.drawable.dashboard);
        menuListIcon.add(R.drawable.users);
        menuListIcon.add(R.drawable.users);
        menuListIcon.add(R.drawable.calendar_attendance);
        menuListIcon.add(R.drawable.students_cap);
        menuListIcon.add(R.drawable.book);
        menuListIcon.add(R.drawable.file_white);
        menuListIcon.add(R.drawable.calendar_timetable);
        menuListIcon.add(R.drawable.book);
        menuListIcon.add(R.drawable.bus);
        expandableListAdapter = new MenuExpandableAdapter(mContext, menuListTitle, menuListDetail, menuListIcon);
        menuListView.setAdapter(expandableListAdapter);
        // Displaying Dashboard, Assignments(Group Headers)....
        menuListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (groupPosition == 0) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    MainFragment mainFragment = new MainFragment();
                    setFragment(mainFragment);
                } else if (groupPosition == 5) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    AssignmentFragment assignmentFragment = new AssignmentFragment();
                    setFragment(assignmentFragment);
                }
                return false;
            }
        });

        menuListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousItem) {
                    menuListView.collapseGroup(previousItem);
                }
                previousItem = groupPosition;
                /*Toast.makeText(getApplicationContext(),
                        menuListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        menuListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        menuListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();*/

            }
        });

        menuListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                if (groupPosition == 1) {
                    if (childPosition == 0) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        StudentDetailsFragment studentDetailsFragment = new StudentDetailsFragment();
                        setFragment(studentDetailsFragment);
                    } else if (childPosition == 1) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        StudentAdmissionFragment studentAdmissionFragment = new StudentAdmissionFragment();
                        setFragment(studentAdmissionFragment);
                    } else if (childPosition == 2) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        StudentMoreFragment studentMoreFragment = new StudentMoreFragment();
                        setFragment(studentMoreFragment);
                    }
                } else if (groupPosition == 2) {
                    if (childPosition == 0) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        EmployeeFragment employeeFragment = new EmployeeFragment();
                        setFragment(employeeFragment);
                    } else if (childPosition == 1) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        EmployeeDetailsFragment employeeDetailsFragment = new EmployeeDetailsFragment();
                        setFragment(employeeDetailsFragment);
                    } else if (childPosition == 2) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        EmpAttendanceFragment empAttendanceFragment = new EmpAttendanceFragment();
                        setFragment(empAttendanceFragment);
                    }
                } else if (groupPosition == 3) {
                    if (childPosition == 0) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        StudentAttendenceFragment studentAttendenceFragment = new StudentAttendenceFragment();
                        setFragment(studentAttendenceFragment);
                    } else if (childPosition == 1) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        EmpAttendanceFragment empAttendanceFragment = new EmpAttendanceFragment();
                        setFragment(empAttendanceFragment);
                    } else if (childPosition == 2) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        AttendanceReportFragment attendanceReportFragment = new AttendanceReportFragment();
                        setFragment(attendanceReportFragment);
                    }
                } else if (groupPosition == 4) {
                    if (childPosition == 0) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        NewSubjectFragment newSubjectFragment = new NewSubjectFragment();
                        setFragment(newSubjectFragment);
                        /*ExamListFragment examListFragment = new ExamListFragment();
                        setFragment(examListFragment);*/
                    } else if (childPosition == 1) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        ChapterFragment chapterFragment = new ChapterFragment();
                        setFragment(chapterFragment);
                       /* ExamScheduleFragment examScheduleFragment = new ExamScheduleFragment();
                        setFragment(examScheduleFragment); */
                    } else if (childPosition == 2) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        AssignSubjectFragment assignSubjectFragment = new AssignSubjectFragment();
                        setFragment(assignSubjectFragment);
                        /*MarksRegisterFragment marksRegisterFragment = new MarksRegisterFragment();
                        setFragment(marksRegisterFragment);*/
                    }
                } else if (groupPosition == 6) {
                    if (childPosition == 0) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        ExamScheduleFragment examScheduleFragment = new ExamScheduleFragment();
                        setFragment(examScheduleFragment);
                        /*UploadContentFragment uploadContentFragment = new UploadContentFragment();
                        setFragment(uploadContentFragment);*/
                    } else if (childPosition == 1) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        ExamPaperFragment examPaperFragment = new ExamPaperFragment();
                        setFragment(examPaperFragment);
                    } else if (childPosition == 2) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        ExamEvaluationFragment examEvaluationFragment = new ExamEvaluationFragment();
                        setFragment(examEvaluationFragment);
                       /* StudyMaterialFragment studyMaterialFragment = new StudyMaterialFragment();
                        setFragment(studyMaterialFragment); */
                    }
                } else if (groupPosition == 7) {
                    if (childPosition == 0) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        ClassWiseTimetableFragment classWiseTimetableFragment = new ClassWiseTimetableFragment();
                        setFragment(classWiseTimetableFragment);
                    } else if (childPosition == 1) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        TeacherwiseTimetableFragment teacherwiseTimetableFragment = new TeacherwiseTimetableFragment();
                        setFragment(teacherwiseTimetableFragment);
                        /*ViewTimeTableFragment viewTimeTableFragment = new ViewTimeTableFragment();
                        setFragment(viewTimeTableFragment);*/
                    }
                    /*if (childPosition == 0){
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        AddBookFragment addBookFragment = new AddBookFragment();
                        setFragment(addBookFragment);
                    } else if(childPosition == 1){
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        BookListFragment bookListFragment = new BookListFragment();
                        setFragment(bookListFragment);
                    }*/
                } else if (groupPosition == 8) {
                    if (childPosition == 0) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        LibraryRuleFragment libraryRuleFragment = new LibraryRuleFragment();
                        setFragment(libraryRuleFragment);
                        /*StationsFragment stationsFragment = new StationsFragment();
                        setFragment(stationsFragment); */
                    } else if (childPosition == 1) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        AddBookFragment addBookFragment = new AddBookFragment();
                        setFragment(addBookFragment);
                       /* RouteFragment routeFragment = new RouteFragment();
                        setFragment(routeFragment); */
                    } else if (childPosition == 2) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        BookListFragment bookListFragment = new BookListFragment();
                        setFragment(bookListFragment);
                    }
                } else if (groupPosition == 9) {
                    if (childPosition == 0) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        AddStationFragment addStationFragment = new AddStationFragment();
                        setFragment(addStationFragment);
                        /*HostelRoomFragment hostelRoomFragment = new HostelRoomFragment();
                        setFragment(hostelRoomFragment);*/
                    } else if (childPosition == 1) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        AddVehicleFragment addVehicleFragment = new AddVehicleFragment();
                        setFragment(addVehicleFragment);

                    } else if (childPosition == 2) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        BusRouteFragment busRouteFragment = new BusRouteFragment();
                        setFragment(busRouteFragment);
                        /*RoomTypeFragment roomTypeFragment = new RoomTypeFragment();
                        setFragment(roomTypeFragment);*/
                    } else if (childPosition == 3) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        RouteGeoLocationFragment routeGeoLocationFragment = new RouteGeoLocationFragment();
                        setFragment(routeGeoLocationFragment);
                        /*AddHostelFragment addHostelFragment = new AddHostelFragment();
                        setFragment(addHostelFragment);*/
                    }
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            menuListTitle.get(groupPosition)
                                    + " -> "
                                    + menuListDetail.get(
                                    menuListTitle.get(groupPosition)).get(
                                    childPosition), Toast.LENGTH_SHORT
                    ).show();
                }
                return false;
            }
        });
        // To display main fragment....
        MainFragment mainFragment = new MainFragment();
        setFragment(mainFragment);
        // to display DrawerToggle...
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Dashboard");
    }

    /**
     * To display fragment....
     */
    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit();
    }

    // To grant permissions....
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.MULTIPLE_PERMISSIONS: {
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
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (id == R.id.logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Log Out");
            builder.setMessage("Are you sure want to Log out?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final ProgressDialog progressDialog = ProgressDialog.show(mContext, "Please wait...", "logging out...", true);
                    Thread thread = new Thread() {
                        public void run() {
                            try {
                                Thread.sleep(1000);
                                SharedPreferences sharedPreferences = mContext.getSharedPreferences(getString(R.string.appinfo), MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                startActivity(intent);
                                progressDialog.dismiss();
                                finish();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    thread.start();
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            /*super.onBackPressed();
            this.finish();*/
            try {
                if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
                    //finish();// finish activity if you are at home screen
                    if (doubleBackToExitPressedOnce) {
                        super.onBackPressed();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                    /**
                     * Displaying toast message when pressing back button
                     */
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(this, "Press again to exit SMS", Toast.LENGTH_LONG).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);
                    return;
                } else {
                    getSupportFragmentManager().popBackStack();// will pop previous fragment
                    return;
                }
            } catch (Exception e) {
                super.onBackPressed();
            }
        }
    }
}
