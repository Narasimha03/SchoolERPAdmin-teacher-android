package com.example.medianet.proschool;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.medianet.proschool.suresh.studentprofile.StudentProfileTabsFragment;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.medianet.proschool.Constants.studentId;

/**
 * Created by JANI on 08-05-2017.
 */

public class StudentDetailsAdapter extends RecyclerView.Adapter<StudentDetailsAdapter.ViewHolder>  {
    private ArrayList<StudentsDetails> studentsDetailses;
    private List<StudentsDetails> allStudentDetails;
    private Context mContext;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    SharedPreferences sharedPreferences;


    private ItemClickStudentProfileListener itemClickStudentProfileListener;

    public StudentDetailsAdapter(Context context, ArrayList<StudentsDetails> studentsDetailses) {
        this.mContext = context;
        this.allStudentDetails = studentsDetailses;
        this.studentsDetailses = new ArrayList<StudentsDetails>();
        this.studentsDetailses.addAll(allStudentDetails);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_student_layout_two, parent, false);
        return new ViewHolder(v);
    }


    public void setClickListener(ItemClickStudentProfileListener itemClickListener) {
        this.itemClickStudentProfileListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StudentsDetails studentsDetails = allStudentDetails.get(position);
        //  Picasso.with(mContext).load(allStudentDetails.get(position).getImageDisplay()).resize(120, 60).into(holder.imgText);

        if (studentsDetails != null) {
            // To display name....
            holder.name.setText(studentsDetails.getStdName());
            holder.classStd.setText(studentsDetails.getStdClass());
            holder.dob.setText(studentsDetails.getStdDob());
            holder.gender.setText(studentsDetails.getStdGender());
            holder.ldn.setText(studentsDetails.getStdAdNo());
            holder.guardianName.setText(studentsDetails.getCategory());
            holder.guardianNum.setText(studentsDetails.getMobile());
            holder.currentAdd.setText(studentsDetails.getRte());
            //
         //   if(studentsDetails.getImageDisplay()!=null) {
              //  Picasso.with(mContext).load(studentsDetails.getImageDisplay()).into(holder.imgUser);
              //  holder.imgUser.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(studentsDetails.getImageDisplay())
                    .into(holder.imgUser);
            holder.imgUser.setVisibility(View.VISIBLE);

            //   }
          /*  else
            {

              *//*   String firstLetter = studentsDetails.getStdName().substring(0, 1);
                ColorGenerator generator = ColorGenerator.MATERIAL;
                int color1 = generator.getRandomColor();
                TextDrawable drawable1 = TextDrawable.builder()
                        .beginConfig()
                        .bold()
                        .toUpperCase()
                        .endConfig()
                        .buildRound(firstLetter, color1);
                holder.imgText.setImageDrawable(drawable1);
                holder.imgText.setVisibility(View.VISIBLE);*//*
              //  holder.imgUser.setVisibility(View.INVISIBLE);
               // holder.imgUser.setVisibility(View.INVISIBLE);

                String firstLetter = studentsDetails.getStdName().substring(0, 1);
                ColorGenerator generator = ColorGenerator.MATERIAL;
                int color1 = generator.getRandomColor();
                TextDrawable drawable1 = TextDrawable.builder()
                        .beginConfig()
                        .bold()
                        .toUpperCase()
                        .endConfig()
                        .buildRound(firstLetter, color1);
                holder.imgUser.setImageDrawable(drawable1);
                holder.imgUser.setVisibility(View.VISIBLE);

               // Picasso.with(mContext).load(firstLetter).into(holder.imgUser);
              //  holder.imgText.setVisibility(View.VISIBLE);


            }
*/
            // holder.imgText.setImageDrawable(s);





            holder.imgUser.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {



                                                      LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                      View alertLayout = layoutInflater.inflate(R.layout.add_student_image_show_dialog, null);
                                                    ImageView imageShow = (ImageView) alertLayout.findViewById(R.id.imgShow);
                                                    //TextView profileName = alertLayout.findViewById(R.id.stdNamePopUp);
                                                      //LinearLayout profileLinear = alertLayout.findViewById(R.id.profileLayout);
                                                     TextView studentName = (TextView) alertLayout.findViewById(R.id.studentName);
                                                      final ProgressBar progressBar = (ProgressBar)alertLayout. findViewById(R.id.progress);

                                                      /*profileLinear.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View view) {


                                                             sharedPreferences = mContext.getSharedPreferences("studentInfo", MODE_PRIVATE);
                                                              SharedPreferences.Editor editor = sharedPreferences.edit();
                                                              editor.putString(studentId, studentsDetails.getStdId());
                                                              editor.commit();
                                                              //startActivity(new Intent(mContext, StudentProfileTabsFragment.class));*//*


                                                              Intent intent = new Intent(view.getContext(), StudentProfileTabsFragment.class);
                                                              mContext.startActivity(intent);

                                                          }
                                                      });*/

                                                      builder = new AlertDialog.Builder(mContext);
                                                      //TODO

                                                      if (v.getId() ==R.id.imgUser ){
                                                         // Picasso.with(mContext).load(studentsDetails.getImageDisplay()).into(imageShow);
                                                          Glide.with(mContext)
                                                                  .load(studentsDetails.getImageDisplay()).listener(new RequestListener<String, GlideDrawable>() {
                                                              @Override
                                                              public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                                                  progressBar.setVisibility(View.GONE);
                                                                  return false;

                                                              }

                                                              @Override
                                                              public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                                                  progressBar.setVisibility(View.GONE);

                                                                  return false;
                                                              }
                                                          })
                                                                  .into(imageShow);
                                                          studentName.setText(studentsDetails.getStdName());



                                                          builder.setView(alertLayout);
                                                          dialog = builder.create();
                                                          dialog.show();
                                                      }
                                                      else {
                                                          Toast.makeText(v.getContext(), "ROW PRESSED = " + String.valueOf(studentsDetails.getStdName()), Toast.LENGTH_SHORT).show();
                                                      }



                                                      //    Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(studentsDetails.getStdId()), Toast.LENGTH_SHORT).show();
                                                      }

                                              });



                                              }


          /*  String image = studentsDetails.getImageDisplay();
       new AllImageBackTask(mContext,StudentDetailsAdapter.this).execute(image);
        //    System.out.println("image data names"+s);

           // String firstLetter = studentsDetails.getStdName().substring(0, 1);
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color1 = generator.getRandomColor();
            TextDrawable drawable1 = TextDrawable.builder()
                    .beginConfig()
                    .bold()
                    .toUpperCase()
                    .endConfig()
                    .buildRound(image, color1);
            holder.imgText.setImageDrawable(drawable1);
            holder.imgText.setVisibility(View.VISIBLE);*/
          /*  String firstLetter = studentsDetails.getStdName().substring(0, 1);
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color1 = generator.getRandomColor();
            TextDrawable drawable1 = TextDrawable.builder()
                    .beginConfig()
                    .bold()
                    .toUpperCase()
                    .endConfig()
                    .buildRound(firstLetter, color1);
            holder.imgText.setImageDrawable(s);
            holder.imgText.setVisibility(View.VISIBLE);*/
        }


    @Override
    public int getItemCount() {
        return allStudentDetails.size();
    }

/*

    public void setFragment(Fragment fragment) {
        Activity activity=null;


        FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentTransaction replace;
        replace = fragmentTransaction.replace(R.id.frameLayout, fragment);
        replace.commit();
       */
/* FragmentManager fragmentManager = activity.getFragmentManager();
        int commit = fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)

                .commit();*//*

    }
*/

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View container;
        public CircularImageView imgUser;
        public ImageView imgText;
        public TextView name, classStd, dob, gender, ldn, guardianName, guardianNum, currentAdd;

        public ViewHolder(View itemView) {
            super(itemView);
            imgUser = (CircularImageView) itemView.findViewById(R.id.imgUser);
            imgText = (ImageView) itemView.findViewById(R.id.imgText);
            name = (TextView) itemView.findViewById(R.id.stdName);
            classStd = (TextView) itemView.findViewById(R.id.classStd);
            dob = (TextView) itemView.findViewById(R.id.dob);
            gender = (TextView) itemView.findViewById(R.id.gender);
            ldn = (TextView) itemView.findViewById(R.id.ldn);
            guardianName = (TextView) itemView.findViewById(R.id.guardianName);
            guardianNum = (TextView) itemView.findViewById(R.id.guardianNum);
            currentAdd = (TextView) itemView.findViewById(R.id.currentAdd);
            name.setOnClickListener(this);
            name.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);


        }

        @Override
        public void onClick(View view) {
            if (itemClickStudentProfileListener != null) itemClickStudentProfileListener.onClickProfile(view, getAdapterPosition());

        }
    }


    public interface ItemClickStudentProfileListener {
        void onClickProfile(View view, int position);

    }

}
