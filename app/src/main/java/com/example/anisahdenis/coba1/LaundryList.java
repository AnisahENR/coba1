package com.example.anisahdenis.coba1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Anisah Denis on 6/9/2017.
 */

public class LaundryList extends ArrayAdapter<Laundry>{

        private Activity context;
        private List<Laundry> laundryList;

    public LaundryList(Activity context, List<Laundry> laundryList) {
        super(context, R.layout.laundry_list_layout, laundryList);
        this.context = context;
        this.laundryList = laundryList;
    }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.laundry_list_layout, null, true);

        TextView textViewNamaLaundry = (TextView) listViewItem.findViewById(R.id.textViewNamaLaundry);
        TextView textViewAlamatLaundry = (TextView) listViewItem.findViewById(R.id.textViewAlamatLaundry);
        Laundry laundry = laundryList.get(position);

//        textViewNamaLaundry.setText(laundry.getLaundry_nama());
//        textViewAlamatLaundry.setText(laundry.getLaundry_alamat());
        return listViewItem;
    }
    }

