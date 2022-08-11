package com.mor.morscanner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mor.morscanner.Model.GetPartyListResponse.GetPartyListResponse;
import com.mor.morscanner.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Bhupesh-Pc on 20-07-2017.
 */

public class SelectPartyAdapter extends BaseAdapter {


    Context context;
    ArrayList<GetPartyListResponse> data;


    private ArrayList<GetPartyListResponse> arraylist;


    public SelectPartyAdapter(Context context, ArrayList<GetPartyListResponse> listParty) {
        this.context = context;
        data = listParty;
        this.arraylist = new ArrayList<GetPartyListResponse>();
        this.arraylist.addAll(data);

    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ViewHolder holder;

        if (convertView == null) {


            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.list_select_party, null);


            holder.txtHeadName = (TextView) convertView.findViewById(R.id.txt_HeadName);


            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }


        holder.txtHeadName.setText(data.get(position).getPartyName());


        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        data.clear();
        if (charText.length() == 0) {
            data.addAll(arraylist);
        } else {
            for (GetPartyListResponse wp : arraylist) {

                if (wp.getPartyName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(wp);
                }


            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder {

        TextView txtHeadName;


    }
}
