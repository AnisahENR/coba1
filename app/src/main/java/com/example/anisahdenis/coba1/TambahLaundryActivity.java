package com.example.anisahdenis.coba1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

public class TambahLaundryActivity extends AppCompatActivity {

    TextView textViewKategori;
    EditText editTextNamaLaundry, editTextAlamatLaundry;
    Button btTambahLaundry;
    ListView listViewLaundry;

    DatabaseReference databaseLaundry; // inisialisasi databaseLaundry

    List<Laundry> laundryList;

    @Override
    protected void onStart() {
        super.onStart();

        databaseLaundry.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                laundryList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Laundry laundry = postSnapshot.getValue(Laundry.class);
                    laundryList.add(laundry);
                }

                LaundryList adapter = new LaundryList(TambahLaundryActivity.this, laundryList);
                listViewLaundry.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_laundry);

        textViewKategori = (TextView)findViewById(R.id.textViewNamaKategori);
        editTextAlamatLaundry = (EditText)findViewById(R.id.editTextAlamatLaundry);
        editTextNamaLaundry = (EditText) findViewById(R.id.editTextNamaLaundry);
        btTambahLaundry = (Button)findViewById(R.id.bt_tambah_laundry);
        listViewLaundry = (ListView)findViewById(R.id.listViewDaftarLaundry);

        laundryList = new ArrayList<>();

        // intent dibawah ini utk menangkap intent dari main activity
        Intent intentTambahLaundry = getIntent();
        // inisialisasi dibawah ini utk dapet nilai id dan nama kategori yang di pos dari mainActivity
        String id_kategori = intentTambahLaundry.getStringExtra(LaundryActivity.kategori_id);
        String nama_kategori = intentTambahLaundry.getStringExtra(LaundryActivity.kategori_nama);

        textViewKategori.setText(nama_kategori);

        //membuat database laundry yang mempunyai relasi dengan id_kategori
        databaseLaundry = FirebaseDatabase.getInstance().getReference("laundry").child(id_kategori);

        btTambahLaundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tambahLaundry();
            }
        });


    }

    public void tambahLaundry() {
        String nama_laundry = editTextNamaLaundry.getText().toString();
        String alamat_laundry = editTextAlamatLaundry.getText().toString();

        //disini proses untuk menambah ke databasenya
        String id = databaseLaundry.push().getKey(); // set id otomatis dari firebase
        Laundry laundry = new Laundry(id , nama_laundry , alamat_laundry); //membuat objek yang akan disimpan
        databaseLaundry.child(id).setValue(laundry); //insert ke databasenya deh

    }
}
