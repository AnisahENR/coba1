package com.example.anisahdenis.coba1;

/**
 * Created by meita on 01/06/2017.
 */

public class Laundry {
    private String laundry_id;
    private String nama;
    private String alamat;
    private String telpon;
    private String website;
    private double latitude;
    private double longitude;

    public Laundry() {

    }

    public Laundry(String laundry_id, String nama, String alamat, double latitude, double longitude, String telpon, String website) {
        this.laundry_id = laundry_id;
        this.nama = nama;
        this.alamat = alamat;
        this.latitude = latitude;
        this.longitude = longitude;
        this.telpon = telpon;
        this.website = website;
    }

    public String getLaundry_id() {
        return laundry_id;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTelpon() {
        return telpon;
    }

    public void setTelpon(String telpon) {
        this.telpon = telpon;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
