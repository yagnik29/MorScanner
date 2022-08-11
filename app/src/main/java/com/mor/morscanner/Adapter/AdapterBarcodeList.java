package com.mor.morscanner.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mor.morscanner.Model.GetCheckBarcodeResponse.BarcodeDetails;
import com.mor.morscanner.R;

import java.util.ArrayList;


public class AdapterBarcodeList extends RecyclerView.Adapter<AdapterBarcodeList.ViewHolder> {


    Context context;
    ArrayList<BarcodeDetails> data;
    private ClickCallback callback;





    public AdapterBarcodeList(Context context, ArrayList<BarcodeDetails> data, ClickCallback callback) {
        this.context = context;
        this.data = data;
        this.callback = callback;

    }

    public interface ClickCallback {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_barcode, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.txtBarcode.setText(String.format("Barcode Number:\n%s", data.get(position).getBarcode()));
        holder.txtDesignCategory.setText(String.format("Design Category: \n%s", data.get(position).getDesignCategory()));
        holder.txtDesignNumber.setText(String.format("Design Number:\n%s", data.get(position).getDesignNumber()));
        holder.txtDiamondPcs.setText(String.format("Diamond PCs: \n%s", data.get(position).getDiamondPCS()));
        holder.txtGoldCategory.setText(String.format("Gold Category:\n%s", data.get(position).getGoldCategory()));
        holder.txtGoldWGT.setText(String.format("Gold Weight:\n%s", data.get(position).getGoldWGT()));


        /*holder.llDelete.setTag(data.get(position));
        holder.llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ItemSplit itemSplit = (ItemSplit) view.getTag();
                data.remove(position);
                notifyDataSetChanged();
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llDelete;
        TextView txtBarcode, txtDesignCategory, txtDesignNumber, txtDiamondPcs, txtGoldCategory,
                txtGoldWGT;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);



            txtBarcode = itemView.findViewById(R.id.txtBarcode);
            txtDesignCategory = itemView.findViewById(R.id.txtDesignCategory);
            txtDesignNumber = itemView.findViewById(R.id.txtDesignNumber);
            txtDiamondPcs = itemView.findViewById(R.id.txtDiamondPcs);
            txtGoldCategory = itemView.findViewById(R.id.txtGoldCategory);
            txtGoldWGT = itemView.findViewById(R.id.txtGoldWGT);

            llDelete = itemView.findViewById(R.id.llDelete);


            llDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onItemClick(getAdapterPosition());
                }
            });

        }
    }
}
