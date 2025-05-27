package com.teamtwo.stocko_supply.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.teamtwo.stocko_supply.models.Barang;

public interface BarangRepository extends JpaRepository<Barang, Integer> {
    Barang findByNama(String nama);

    Barang findById(Long id);

    boolean existsById(Long id);

    List<Barang> findAllByOrderByIdAsc(); // urut berdasarkan ID naik

    List<Barang> findAllByOrderByIdDesc(); // urut berdasarkan ID turun (opsional)

    List<Barang> findByNamaContainingIgnoreCase(String nama);

    // Barang masuk hari ini
    List<Barang> findByMasukBetween(LocalDateTime created, LocalDateTime end);

    long countByMasukBetween(LocalDateTime start, LocalDateTime end);
}
