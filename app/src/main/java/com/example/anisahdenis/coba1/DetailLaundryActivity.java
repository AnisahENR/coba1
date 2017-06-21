package com.example.anisahdenis.coba1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class DetailLaundryActivity extends AppCompatActivity {

    TextView nama, alamat, notel, website;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laundry);

        final Laundry laundry = new Laundry();

        back = (ImageButton) findViewById(R.id.btn_back3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nama = (TextView) findViewById(R.id.tv_nama);
        alamat = (TextView) findViewById(R.id.tv_addr);
        notel = (TextView) findViewById(R.id.tv_notel);
        website = (TextView) findViewById(R.id.tv_web);

        nama.setText(laundry.getNama());
        alamat.setText(laundry.getAlamat());
        notel.setText(laundry.getTelpon());
        website.setText(laundry.getWebsite());

    }
}
