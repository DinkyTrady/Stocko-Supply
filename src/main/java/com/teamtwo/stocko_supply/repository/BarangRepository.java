package com.teamtwo.stocko_supply.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamtwo.stocko_supply.models.Barang;

public interface BarangRepository extends JpaRepository<Barang, Integer> {
    Barang findByNama(String nama);

    Barang findById(Long id);

    boolean existsById(Long id);

    List<Barang> findAllByOrderByIdAsc(); // urut berdasarkan ID naik

    List<Barang> findAllByOrderByIdDesc(); // urut berdasarkan ID turun (opsional)
}
