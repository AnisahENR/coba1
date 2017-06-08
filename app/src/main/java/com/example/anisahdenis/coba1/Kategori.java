package com.example.anisahdenis.coba1;

/**
 * Created by Anisah Denis on 6/9/2017.
 */

public class Kategori {

    private String kategori_id;
    private String kategori_nama;

    public Kategori() {

    }

    public Kategori(String kategori_id, String kategori_nama) {
        this.kategori_id = kategori_id;
        this.kategori_nama = kategori_nama;
    }

    public String getKategori_id() {
        return kategori_id;
    }

    public String getKategori_nama() {
        return kategori_nama;
    }




}
