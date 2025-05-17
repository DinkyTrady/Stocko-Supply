package com.example.warehouse.models;

public class Item {

    private Long id;
    private String nama;
    private String kategoriBarang;
    private String keteranganBarang;

    private int jumlahBarang;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setKategoriBarang(String kategoriBarang) {
        this.kategoriBarang = kategoriBarang;
    }

    public String getKategoriBarang() {
        return kategoriBarang;
    }

    public void setKeteranganBarang(String keteranganBarang) {
        this.keteranganBarang = keteranganBarang;
    }

    public String getKeteranganBarang() {
        return keteranganBarang;
    }

    public void setJumlahBarang(int jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public int getJumlahBarang() {
        return jumlahBarang;
    }

}
