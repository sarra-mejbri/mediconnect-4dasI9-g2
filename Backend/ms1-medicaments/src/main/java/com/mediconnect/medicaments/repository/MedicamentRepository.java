package com.mediconnect.medicaments.repository;

import com.mediconnect.medicaments.entity.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicamentRepository extends JpaRepository<Medicament, Long> {

    // Keywords Spring Data JPA
    List<Medicament> findByNom(String nom);

    List<Medicament> findByNomContaining(String nom);

    List<Medicament> findByForme(String forme);

    List<Medicament> findBySurOrdonnance(boolean surOrdonnance);

    List<Medicament> findByNomContainingAndSurOrdonnance(String nom, boolean surOrdonnance);
}
