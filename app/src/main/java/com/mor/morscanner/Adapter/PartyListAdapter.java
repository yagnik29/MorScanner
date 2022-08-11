package com.mor.morscanner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mor.morscanner.Model.GetPartyListResponse.GetPartyListResponse;
import com.mor.morscanner.R;

import java.util.ArrayList;
import java.util.List;

public class PartyListAdapter extends ArrayAdapter<GetPartyListResponse> {

    Context context;
    int resource;
    ArrayList<GetPartyListResponse> listPartyDetails;

    public PartyListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<GetPartyListResponse> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listPartyDetails = objects;

    }

    @Override
    public GetPartyListResponse getItem(int position) {

        return listPartyDetails.get(position);
    }

    @Override
    public int getCount() {
        return listPartyDetails.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_select_party, parent, false);

            TextView textView = (TextView) view.findViewById(R.id.txtParty);
            textView.setText(listPartyDetails.get(position).getPartyName());
        }
        GetPartyListResponse getPartyListResponse = listPartyDetails.get(position);

        if (getPartyListResponse != null) {
            TextView textView = (TextView) view.findViewById(R.id.txtParty);
            if (textView != null) {
                textView.setText(getPartyListResponse.getPartyName());
            }

        }
        return view;
    }
}
