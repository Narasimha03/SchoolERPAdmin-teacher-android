package com.example.medianet.proschool;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.medianet.proschool.suresh.activity.AddAssigmentFragment;
import com.example.medianet.proschool.suresh.feemodule.feecollection.AddFeeCollectionFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JANI on 03-06-2017.
 */

public class BookListFragment extends Fragment implements AllBookBackTask.AllBooks{
View bookListView;
    RecyclerView recycler_view;
    ArrayList<Books> booksList = new ArrayList<Books>();
    BookAdapter bookAdapter;
    FloatingActionButton fab;
    FrameLayout frameLayout;
    LinearLayout fabLayout1;
    View fabBGLayout;
    boolean isFABOpen=false;
    TextView noText;
    FloatingActionButton floatingActionButton;
Context mContext;
String schoolId;
String role;

    public BookListFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bookListView = inflater.inflate(R.layout.books_list_layout_two, container, false);
        mContext=getActivity();

        recycler_view = (RecyclerView) bookListView.findViewById(R.id.recycler_view);
        noText = (TextView) bookListView.findViewById(R.id.noText);


        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        role = sharedPreferences.getString(Constants.rolePref, "");
        new AllBookBackTask(getActivity(), BookListFragment.this).execute(schoolId);
        fabLayout1= (LinearLayout) bookListView.findViewById(R.id.fabLayout1);

        fab = (FloatingActionButton) bookListView.findViewById(R.id.fab);

        frameLayout=(FrameLayout)bookListView.findViewById(R.id.frameLayout);
        fabBGLayout=bookListView.findViewById(R.id.fabBGLayout);

        /*floatingActionButton=(FloatingActionButton) bookListView.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              AddBookFragment addBookFragment=new AddBookFragment();
              setFragment(addBookFragment);

            }
        });*/

        fabLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //  showFABMenu();
                /*AddBookFragment addBookFragment=new AddBookFragment();
                setFragment(addBookFragment);*/
                //   closeFABMenu();

                Intent i = new Intent(getActivity(),AddBookActivity.class);
                startActivity(i);

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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Books List");



        return bookListView;
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
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void allBooks(String result) throws JSONException {
        if (result != null && !result.isEmpty()){
            booksList.clear();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("books");
            if (jsonArray.length() > 0){
                int count = 0;
                while (count < jsonArray.length()){
                    JSONObject bookObject = jsonArray.getJSONObject(count);
                    Books books = new Books(bookObject.getString("_id"), bookObject.getString("book_id"), bookObject.getString("book_title"),
                            bookObject.getString("author_name"), bookObject.getString("book_price"), bookObject.getString("qty"),
                            bookObject.getString("rack_number"), bookObject.getString("inward_date"), bookObject.getString("book_description"),
                            bookObject.getString("subject"));
                    booksList.add(books);
                    count++;
                }
                bookAdapter = new BookAdapter(getActivity(), booksList);
                recycler_view.setAdapter(bookAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                bookAdapter.notifyDataSetChanged();
            } else {
                recycler_view.setVisibility(View.GONE);
                noText.setVisibility(View.VISIBLE);
            }
        }
    }
    /*
    "{
  ""books"": [
    {
      ""_id"": ""5924f342db9f4d1d4413831f"",
      ""book_id"": ""BOOK-1"",
      ""book_title"": ""Test Book"",
      ""author_name"": ""Jagadeesh"",
      ""book_price"": ""500"",
      ""qty"": ""2"",
      ""rack_number"": ""21"",
      ""inward_date"": ""16-05-2017"",
      ""book_description"": ""This is test description"",
      ""subject"": ""Economics""
    }
  ]
}"
     */
}
