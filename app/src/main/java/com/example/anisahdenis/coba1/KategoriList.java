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

public class KategoriList extends ArrayAdapter<Kategori> {

    private Activity context;
    private List<Kategori> kategoriList;

    public KategoriList(Activity context, List<Kategori> kategoriList) {
        super(context, R.layout.kategori_list_layout, kategoriList);
        this.context = context;
        this.kategoriList = kategoriList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.kategori_list_layout, null, true);

        TextView textViewKategori = (TextView) listViewItem.findViewById(R.id.textViewNamaKategori);
        Kategori kategori = kategoriList.get(position);

        textViewKategori.setText(kategori.getKategori_nama());
        return listViewItem;
    }

}