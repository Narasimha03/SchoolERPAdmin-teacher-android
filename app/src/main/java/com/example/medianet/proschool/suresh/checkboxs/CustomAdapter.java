package com.example.medianet.proschool.suresh.checkboxs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medianet.proschool.R;

import java.util.ArrayList;
 
/**
 * Created by Parsania Hardik on 29-Jun-17.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
 
    private LayoutInflater inflater;
    public static ArrayList<Model> imageModelArrayList;
    private Context ctx;
 
    public CustomAdapter(Context ctx, ArrayList<Model> imageModelArrayList) {
 
        inflater = LayoutInflater.from(ctx);
        this.imageModelArrayList = imageModelArrayList;
        this.ctx = ctx;
    }
 
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
 
        View view = inflater.inflate(R.layout.rv_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
 
        return holder;
    }
 
    @Override
    public void onBindViewHolder(final CustomAdapter.MyViewHolder holder, int position) {
 
        holder.checkBox.setText("Checkbox " + position);
        holder.checkBox.setChecked(imageModelArrayList.get(position).getSelected());
        holder.tvAnimal.setText(imageModelArrayList.get(position).getAnimal());



       // holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.present.setChecked(false);


                Integer pos = (Integer) holder.checkBox.getTag();
                Toast.makeText(ctx, imageModelArrayList.get(pos).getAnimal() + " clicked!", Toast.LENGTH_SHORT).show();
 
                if (imageModelArrayList.get(pos).getSelected()) {
                    imageModelArrayList.get(pos).setSelected(false);
                } else {
                    imageModelArrayList.get(pos).setSelected(true);
                }
            }
        });

        holder.present.setText("Checkbox " + position);
        holder.present.setChecked(imageModelArrayList.get(position).getSelected());
        holder.presentText.setText(imageModelArrayList.get(position).getAnimal());

        // holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.present.setTag(position);
        holder.present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.checkBox.setChecked(false);

                Integer pos = (Integer) holder.present.getTag();
                Toast.makeText(ctx, imageModelArrayList.get(pos).getAnimal() + " clicked!", Toast.LENGTH_SHORT).show();

                if (imageModelArrayList.get(pos).getSelected()) {
                    imageModelArrayList.get(pos).setSelected(false);
                } else {
                    imageModelArrayList.get(pos).setSelected(true);
                }
            }
        });


        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.circle) {

                 //   Integer pos = (Integer) holder.checkBox.getTag();
                    Integer pos = (Integer) holder.circle.getTag();
                    Toast.makeText(ctx, imageModelArrayList.get(pos).getAnimal() + " clicked!", Toast.LENGTH_SHORT).show();

                    if (imageModelArrayList.get(pos).getSelected()) {
                        imageModelArrayList.get(pos).setSelected(false);
                    } else {
                        imageModelArrayList.get(pos).setSelected(true);
                    }


                 /*   Toast.makeText(ctx, "choice: Circle",
                            Toast.LENGTH_SHORT).show();*/
                } else if(checkedId == R.id.sqare) {
                    Integer pos = (Integer) holder.square.getTag();
                    Toast.makeText(ctx, imageModelArrayList.get(pos).getAnimal() + " clicked!", Toast.LENGTH_SHORT).show();

                    Toast.makeText(ctx, "choice: Square",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ctx, "choice: Rectangle",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
 
    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }
 
    class MyViewHolder extends RecyclerView.ViewHolder {
        private RadioGroup radioGroup;
        protected CheckBox checkBox,present;
        private TextView tvAnimal,presentText;
        private RadioButton circle, square, rectangle;
 
        public MyViewHolder(View itemView) {
            super(itemView);
 
            checkBox = (CheckBox) itemView.findViewById(R.id.cb);
            tvAnimal = (TextView) itemView.findViewById(R.id.animal);
            present = (CheckBox) itemView.findViewById(R.id.present);
            presentText = (TextView) itemView.findViewById(R.id.presentText);

            radioGroup = (RadioGroup)itemView.findViewById(R.id.myRadioGroup);
            circle = (RadioButton)itemView.findViewById(R.id.circle);
            square = (RadioButton)itemView.findViewById(R.id.sqare);
            rectangle = (RadioButton)itemView.findViewById(R.id.rectangle);
        }
 
    }
}