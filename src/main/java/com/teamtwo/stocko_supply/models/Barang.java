package com.teamtwo.stocko_supply.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "barang")
public class Barang {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nama;
    private String kategoriBarang;
    private String keteranganBarang;

    @Column(nullable = false)
    private int jumlahBarang;

    public Barang() {
    }

    public Barang(Long id, String nama, String kategoriBarang, int jumlahBarang, String keteranganBarang) {
        this.id = id;
        this.nama = nama;
        this.kategoriBarang = kategoriBarang;
        this.keteranganBarang = keteranganBarang;
        this.jumlahBarang = jumlahBarang;
    }

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
