package com.example.anisahdenis.coba1;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback,GoogleMap.OnMapClickListener,GoogleMap.OnMarkerClickListener{


    private GoogleMap mMap;
    private FirebaseAuth auth;
    private Toolbar mActionBarToolbar;
    private double my_lat;
    private double my_long;

    private HashMap<Marker,Laundry> mHashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setLokasiSaya() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        LatLng sydney;
        if (location != null) {
            my_lat = location.getLatitude();
            my_long = location.getLongitude();
            sydney = new LatLng(my_lat, my_long);
        } else {
            sydney = new LatLng(-34, 151);
        }
        mMap.setMyLocationEnabled(true);


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));

        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
        showMarker();
    }

    private void showMarker(){

        final ProgressDialog pd = new ProgressDialog(MapsActivity.this);
        pd.setMessage("loading");
        pd.show();
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("kosan");
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnpashot : dataSnapshot.getChildren()) {
                    Laundry laundry = userSnpashot.getValue(Laundry.class);
                    LatLng markerLokasi = new LatLng(laundry.getLatitude(), laundry.getLongitude());
                    Marker marker = mMap.addMarker(
                            new MarkerOptions().position(markerLokasi)
                    );

                    mHashMap.put(marker, laundry);

                }

                pd.dismiss();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    /**
     * Manipulates the map when it's available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = database.getReference("laundry");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
//                    Laundry laundry = postSnapshot.getValue(Laundry.class);

       setLokasiSaya();


    }

    @Override
    public void onMapClick(LatLng latLng) {

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        final Laundry laundry = this.mHashMap.get(marker);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_info_contents);
        TextView nama_laundry = (TextView)dialog.findViewById(R.id.nama_laundry);
        TextView alamat_laundry = (TextView)dialog.findViewById(R.id.alamat_laundry);
        nama_laundry.setText(laundry.getNama());
        alamat_laundry.setText(laundry.getAlamat());

        dialog.show();
        //lanjutin buat button ke detail laundry

        return false;
    }
}
