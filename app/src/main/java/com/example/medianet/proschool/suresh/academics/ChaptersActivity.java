package com.example.medianet.proschool.suresh.academics;

import android.animation.Animator;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.medianet.proschool.AllChapterBackTask;
import com.example.medianet.proschool.AllSubjectBackTask;
import com.example.medianet.proschool.ChapterAdapter;
import com.example.medianet.proschool.Chapters;
import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.SectionBackGroundTask;
import com.example.medianet.proschool.suresh.activity.AddChapterFragment;
import com.example.medianet.proschool.suresh.activity.AllChapterFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;


public class ChaptersActivity extends AppCompatActivity  implements  AllChapterBackTask.ChapterResponse {

    Toolbar toolbar;

    //
    SlidingUpPanelLayout sliding_layout;
    //Student Layout
    LinearLayout chapter_layout;
    TextView textChapterCount;

    // Subject Spinner...
    String selectedSubject;

    RecyclerView recycler_view;
    ArrayList<Chapters> listChapters = new ArrayList<Chapters>();
    ChapterAdapter chapterAdapter;
    FrameLayout frameLayout;
    LinearLayout fabLayout1;
    FloatingActionButton fab;
    View fabBGLayout;
    Context mContext;
    String schoolId;
    boolean isFABOpen = false;
    String role;
    String subjectId;
    SharedPreferences sharedPreferences1;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.all_chapter_layout_two_three);
       // toolbar = (Toolbar) findViewById(R.id.test_toolbar);
       // setSupportActionBar(toolbar);


        mContext = this;
        /*sliding_layout = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);*/
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        role = sharedPreferences.getString(Constants.rolePref, "");

         sharedPreferences1 = mContext.getSharedPreferences("subjectId", MODE_PRIVATE);
        subjectId = sharedPreferences1.getString(Constants.subjectId, "");
        new AllChapterBackTask(mContext, ChaptersActivity.this).execute(subjectId);

        fab = (FloatingActionButton)findViewById(R.id.fab);
       /*fab1 = (FloatingActionButton) subjectView.findViewById(R.id.fab1);
        fab2= (FloatingActionButton) subjectView.findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) subjectView.findViewById(R.id.fab3);*/
        frameLayout = (FrameLayout)findViewById(R.id.frameLayout);
        fabBGLayout = findViewById(R.id.fabBGLayout);
        fabLayout1 = (LinearLayout)findViewById(R.id.fabLayout1);


        fabLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChaptersActivity.this,AddChapterFragment.class);
                startActivity(intent);

            }
        });
        if (role.equals("admin")) {
            fab.setOnClickListener(view -> {


                //    animateFAB();
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            });
        } else if (role.equals("teacher")) {
            fab.hide();
        }

        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
                //animateFAB();
            }
        });


        //  noRecordsFound=(TextView)chapterView.findViewById(R.id.no_records_found);

        /*chapter_layout = (LinearLayout)findViewById(R.id.chapter_layout);
        textChapterCount = (TextView)findViewById(R.id.textChapterCount);*/
        // class spinner....

        //

        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);
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
        //fabLayout1.animate().translationY(0);
        //fabLayout2.animate().translationY(0);
        fabLayout1.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    fabLayout1.setVisibility(View.GONE);
                    //fabLayout2.setVisibility(View.GONE);
                    //fabLayout3.setVisibility(View.GONE);


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

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void ChapterResponse(String response) throws JSONException {
        if (response != null && !response.isEmpty()) {
            listChapters.clear();
            JSONObject jsonObject = new JSONObject(response);
            System.out.println("chapters jsonObject" + jsonObject);

            JSONArray jsonArray = jsonObject.getJSONArray("chapters");
            System.out.println("chapters" + jsonArray);
            if (jsonArray.length() > 0) {

                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject chapObject = jsonArray.getJSONObject(count);
                    Chapters chapters = new Chapters(chapObject.getString("_id"), chapObject.getString("lession_id"), selectedSubject,
                            chapObject.getString("title"), chapObject.getString("chapter_code"), chapObject.getString("no_of_topics"),
                            chapObject.getString("description"), chapObject.getString("status"));
                    listChapters.add(chapters);
                    count++;
                }
                //Count....
                /*String taskCount = "Chapters (" + String.valueOf(listChapters.size()) + ")";
                textChapterCount.setText(taskCount);*/
                // textChapterCount.setVisibility(View.VISIBLE);
                recycler_view.setVisibility(View.VISIBLE);

                //
                chapterAdapter = new ChapterAdapter(mContext, listChapters);
                recycler_view.setAdapter(chapterAdapter);
                //noRecordsFound.setVisibility(View.GONE);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                chapterAdapter.notifyDataSetChanged();
                //
                /*sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(120);*/
            } else {
                /*sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(0);*/
                // String taskCount = "Chapters";
                //  textChapterCount.setText(taskCount);
                //  recycler_view.setVisibility(View.GONE);
                // noRecordsFound.setVisibility(View.VISIBLE);
                // noRecordsFound.setText("No Records Found");
                Toast.makeText(mContext, "No Records Found....!", Toast.LENGTH_SHORT).show();
                //recycler_view.setVisibility(View.GONE);
            }
        }
    }
}