package com.example.medianet.proschool.suresh.examination;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.medianet.proschool.R;

import java.util.List;

public class SelectItemAdapter extends RecyclerView.Adapter<SelectItemAdapter.ItemsHolder>{

    private int quantities [];
    List<BulkEvaluationModel> lista;

    public SelectItemAdapter(List<BulkEvaluationModel> lista) {
        this.lista=lista;
        this.quantities = new int[getItemCount()];
    }

    @Override
    public ItemsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
        return new SelectItemAdapter.ItemsHolder(v);
    }

 /*   @Override
    public ItemsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }*/

    @Override
    public void onBindViewHolder(final ItemsHolder holder, int position) {

        BulkEvaluationModel producto = lista.get(position);
        holder.textItemName.setText(producto.getStdName());
       // producto.holder.set(itemsQty.get(position));
            holder.numPicker.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                  //  if(lista.get(position).equals(quantities[holder.getAdapterPosition()])) {
                        quantities[holder.getAdapterPosition()] =
                                //  Integer.parseInt(holder.numPicker.getText().toString());
                                Integer.parseInt(holder.numPicker.getText().toString());
                    //}

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }


    @Override
    public int getItemCount() {
        return lista.size();
    }


    class ItemsHolder extends RecyclerView.ViewHolder {

        private SelectItemAdapter parent;
        TextView textItemName;
        EditText numPicker;

        ItemsHolder(final View view) {
            super(view);

            numPicker = (EditText) itemView.findViewById(R.id.etCantidad);
            textItemName=(TextView)itemView.findViewById(R.id.tvProducto);


        }
        public String getQtyNumber() {
            return numPicker.getText().toString();
        }
    }


    public int [] getQuantities(){
        return this.quantities;
    }
}