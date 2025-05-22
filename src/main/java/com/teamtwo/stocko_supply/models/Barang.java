package com.teamtwo.stocko_supply.models;

import java.time.ZonedDateTime;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nama;

    @Column(nullable = false, length = 255)
    private String kategori;

    @Column(nullable = true)
    private String keterangan;

    @Column(nullable = false)
    private Integer jumlah;

    @Column(nullable = false, insertable = false, updatable = false)
    private String status;

    @Column(name = "masuk")
    private ZonedDateTime masuk;

    @Column(name = "barang_update")
    private ZonedDateTime barangUpdate;

    public Barang() {
    }

    public Barang(String nama, String kategoriBarang, Integer jumlahBarang, String keteranganBarang,
            ZonedDateTime masuk) {
        this.nama = nama;
        this.kategori = kategoriBarang;
        this.keterangan = keteranganBarang;
        this.jumlah = jumlahBarang;
        this.masuk = masuk;
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

    public void setKategori(String kategoriBarang) {
        this.kategori = kategoriBarang;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKeterangan(String keteranganBarang) {
        this.keterangan = keteranganBarang;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setJumlah(Integer jumlahBarang) {
        this.jumlah = jumlahBarang;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public String getStatus() {
        return status;
    }

    public void setMasuk(ZonedDateTime masuk) {
        this.masuk = masuk;
    }

    public ZonedDateTime getMasuk() {
        return masuk;
    }

    public ZonedDateTime getBarangUpdate() {
        return barangUpdate;
    }
}
