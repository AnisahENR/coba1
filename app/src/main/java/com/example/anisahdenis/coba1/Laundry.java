package com.example.anisahdenis.coba1;

/**
 * Created by meita on 01/06/2017.
 */

public class Laundry {
    private String laundry_id;
    private String laundry_nama;
    private String laundry_alamat;
    private Double latitude;
    private Double longitude;

    public Laundry(String id, String nama_laundry, String alamat_laundry) {

    }

    public Laundry(String laundry_id, String laundry_nama, String laundry_alamat, Double latitude, Double longitude) {
        this.laundry_id = laundry_id;
        this.laundry_nama = laundry_nama;
        this.laundry_alamat = laundry_alamat;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLaundry_id() {
        return laundry_id;
    }

    public String getLaundry_nama() {
        return laundry_nama;
    }

    public String getLaundry_alamat() {
        return laundry_alamat;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
