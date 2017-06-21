package com.example.anisahdenis.coba1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class TambahLaundryActivityBaru extends AppCompatActivity {
    private EditText nama, alamat, website, telpon;
    private ImageButton btSimpan, btBatal;
    private double latitude, longitude;
    private final static int PLACE_PICKER_REQUEST = 2;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    private DatabaseReference databaseLaundry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_laundry_baru);

        databaseLaundry = FirebaseDatabase.getInstance().getReference("laundry");

        nama = (EditText) findViewById(R.id.input_namaLaundry);
        alamat = (EditText) findViewById(R.id.input_alamat);
        website = (EditText) findViewById(R.id.input_website);
        telpon = (EditText) findViewById(R.id.input_noTelfon);
        btSimpan = (ImageButton) findViewById(R.id.btn_simpan);
        btBatal = (ImageButton) findViewById(R.id.btn_batal);


        btSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tambahLaundry();

            }
        });
        btBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lokasiLaundry();
            }

        });
    }

    public void tambahLaundry() {
        String nama_laundry = nama.getText().toString();
        String alamat_laundry = alamat.getText().toString();
        String website_laundry = website.getText().toString();
        String telpon_laundry = telpon.getText().toString();
        String id_laundry = databaseLaundry.push().getKey();

        if (!TextUtils.isEmpty(nama_laundry)&&!TextUtils.isEmpty(alamat_laundry)) {
            Laundry laundry = new Laundry(id_laundry, nama_laundry, alamat_laundry, latitude, longitude, telpon_laundry, website_laundry);
            databaseLaundry.child(id_laundry).setValue(laundry);

            Toast.makeText(TambahLaundryActivityBaru.this,
                    "Data Laundry Berhasil Ditambahkan",
                    Toast.LENGTH_LONG)
                    .show();
            Intent intent = new Intent(TambahLaundryActivityBaru.this, MapsActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Terdapat Field yang Kosong, Data Gagal Disimpan", Toast.LENGTH_LONG).show();
        }

    }


    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }
    }

    private void lokasiLaundry() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            Intent intent = builder.build(TambahLaundryActivityBaru.this);
            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "This app requires location permissions to be granted", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PLACE_PICKER_REQUEST && resultCode==RESULT_OK && data != null){
            Place place = PlacePicker.getPlace(TambahLaundryActivityBaru.this, data);
            latitude=place.getLatLng().latitude;
            longitude=place.getLatLng().longitude;


            this.alamat.setText(""+place.getAddress());
        }

    }


    }

