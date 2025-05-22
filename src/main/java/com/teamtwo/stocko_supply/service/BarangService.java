package com.teamtwo.stocko_supply.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamtwo.stocko_supply.models.Barang;
import com.teamtwo.stocko_supply.repository.BarangRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class BarangService {
    private BarangRepository barangRepository;

    @Autowired
    public BarangService(BarangRepository barangRepository) {
        this.barangRepository = barangRepository;
    }

    @Transactional
    public boolean addNewBarang(String namaBarang, String kategoriBarang, Integer jumlahBarang,
            String keteranganBarang, ZonedDateTime masuk) {
        Barang barangExist = barangRepository.findByNama(namaBarang);
        if (barangExist != null) {
            return false;
        }

        Barang barang = new Barang(namaBarang, kategoriBarang, jumlahBarang, keteranganBarang, masuk);
        barangRepository.save(barang);

        return true;
    }

    @Transactional(readOnly = true)
    public List<Barang> getAllBarang() {
        return barangRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Barang getBarangById(Long id) {
        return barangRepository.findById(id);
    }

    public Barang getCurrentBarang(HttpServletRequest request) {
        return (Barang) request.getSession().getAttribute("barangs");
    }

    @Transactional
    public boolean updateBarang(Long id, String nama, String kategori, Integer jumlah,
            String keterangan) {
        try {
            Barang barang = getBarangById(id);
            if (barang == null) {
                return false;
            }

            if (nama != null && !nama.isEmpty()) {
                barang.setNama(nama);
            }

            if (kategori != null && kategori.isEmpty()) {
                barang.setKategori(kategori);
            }

            if (!jumlah.equals(null)) {
                barang.setJumlah(jumlah);
            }

            if (keterangan != null && !keterangan.isEmpty()) {
                barang.setKeterangan(keterangan);
            }

            return true;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public boolean deleteBarang(Long id) {
        if (!barangRepository.existsById(id)) {
            return false;
        }
        barangRepository.deleteById(id.intValue());
        return true;
    }
}
