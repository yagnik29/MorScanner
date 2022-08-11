package com.mor.morscanner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mor.morscanner.Activity.OrderSummery;
import com.mor.morscanner.Model.GetCheckBarcodeResponse.BarcodeDetails;
import com.mor.morscanner.Model.GetOrderSummeryResponse.GetOrderSummeryResponse;
import com.mor.morscanner.R;

import java.util.ArrayList;


public class AdapterOrderSummery extends RecyclerView.Adapter<AdapterOrderSummery.ViewHolder> {


    Context context;
    ArrayList<GetOrderSummeryResponse> data;
    private ClickCallback callback;





    public AdapterOrderSummery(Context context, ArrayList<GetOrderSummeryResponse> data, ClickCallback callback) {
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
                .inflate(R.layout.list_order_summery, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.txtOrderDate.setText(String.format("Order Date:\n%s", data.get(position).getOrderDate()));
        holder.txtDeliverDate.setText(String.format("Delivery Date: \n%s", data.get(position).getDeliveryDate()));
        holder.txtPartyName.setText(String.format("Party Name:\n%s", data.get(position).getPartyName()));
        holder.txtPartyContactNumber.setText(String.format("Contact No: \n%s", data.get(position).getContactNumber()));
        holder.txtNotes.setText(String.format("Notes:\n%s", data.get(position).getNotes()));
        holder.txtOrderId.setText(String.format("Order Id:\n%s", data.get(position).getOrderID()));



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
        CardView cardView;
        TextView txtOrderDate, txtDeliverDate, txtPartyName, txtPartyContactNumber, txtNotes, txtOrderId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);



            txtOrderDate = itemView.findViewById(R.id.txtOrderDate);
            txtDeliverDate = itemView.findViewById(R.id.txtDeliverDate);
            txtPartyName = itemView.findViewById(R.id.txtPartyName);
            txtPartyContactNumber = itemView.findViewById(R.id.txtPartyContactNumber);
            txtNotes = itemView.findViewById(R.id.txtNotes);
            txtOrderId = itemView.findViewById(R.id.txtOrderId);


            cardView = itemView.findViewById(R.id.card);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onItemClick(getAdapterPosition());
                }
            });

        }
    }
}
