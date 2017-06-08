package com.example.anisahdenis.coba1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anisah Denis on 6/9/2017.
 */

public class LaundryActivity extends AppCompatActivity {
    //digunakan untuk post nama dan id kategori kedalam intent tambah laundry
    public static final String kategori_nama = "kategori_nama";
    public static final String kategori_id = "kategori_id";

    Spinner spinnerKategoriLaundry;
    Button btTambahKategori;
    ListView listViewKategori;

    List<Kategori> kategoriList;

    // membuat referensi ke database firebase
    DatabaseReference databaseKategoriLaundry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laundry_activity_layout);

        //method dibawah ini digunakan untuk mendapatkan referensi kedalam kelas Kategori
        databaseKategoriLaundry = FirebaseDatabase.getInstance().getReference("kategori");

        spinnerKategoriLaundry = (Spinner) findViewById(R.id.spinnerkategorilaundry);
        btTambahKategori = (Button) findViewById(R.id.bt_tambah_kategori);

        listViewKategori = (ListView) findViewById(R.id.listViewNamaKategori);
        kategoriList = new ArrayList<>();

        //aksi dari btTambahKategori yang merujuk ke method tambahKategori()
        btTambahKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tambahKategori();
            }
        });

        //method dibawah ini untuk membuat listview kategori dapat di click dan redirect ke halaman tambah laundry
        listViewKategori.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Kategori kategori = kategoriList.get(i); // kategori merupakan instan objek dari kelas Kategori

                // membuat intent utk ketika list view kategori di klik
                Intent intentTambahLaundry = new Intent(getApplicationContext(), TambahLaundryActivity.class);

                //method untuk post nama dan id ke dalam intent baru yang dimana nama dan id kategori udah di definisikan diawal tadi (line 22)
                intentTambahLaundry.putExtra(kategori_nama, kategori.getKategori_nama());
                intentTambahLaundry.putExtra(kategori_id, kategori.getKategori_id());

                startActivity(intentTambahLaundry);
            }
        });
    }


    //method onsStart berfungsi untk menangkap data dari db firebase dan nampilin didalam adapter
    @Override
    protected void onStart() {
        super.onStart();
        //menangkap nilai dari db
        databaseKategoriLaundry.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                kategoriList.clear();
                // datasnapshot utk mendapatkan data dari firebase
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Kategori kategori = postSnapshot.getValue(Kategori.class);
                    // lalu data tsb di set ke dalam kategoriList
                    kategoriList.add(kategori);
                }
                // baru deh ditampilin data nya
                KategoriList adapter = new KategoriList(LaundryActivity.this, kategoriList);
                listViewKategori.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void tambahKategori() {
        String nama_kategori = spinnerKategoriLaundry.getSelectedItem().toString();

        //disini proses untuk menambah ke databasenya
        String id = databaseKategoriLaundry.push().getKey(); // set id otomatis dari firebase
        Kategori kategori = new Kategori(id, nama_kategori); //membuat objek yang akan disimpan
        databaseKategoriLaundry.child(id).setValue(kategori); //insert ke databasenya deh
    }
}

