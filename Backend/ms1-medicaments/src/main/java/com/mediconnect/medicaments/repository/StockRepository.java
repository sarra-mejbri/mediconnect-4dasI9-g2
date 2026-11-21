package com.mediconnect.medicaments.repository;

import com.mediconnect.medicaments.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    // Keywords Spring Data JPA
    List<Stock> findByPharmacieId(Long pharmacieId);

    List<Stock> findByMedicamentId(Long medicamentId);

    Stock findByPharmacieIdAndMedicamentId(Long pharmacieId, Long medicamentId);

    List<Stock> findByQuantiteLessThanEqual(int seuil);
}
