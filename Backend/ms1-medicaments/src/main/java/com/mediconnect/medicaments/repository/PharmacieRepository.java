package com.mediconnect.medicaments.repository;

import com.mediconnect.medicaments.entity.Pharmacie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PharmacieRepository extends JpaRepository<Pharmacie, Long> {

    // Keywords Spring Data JPA
    List<Pharmacie> findByNom(String nom);

    List<Pharmacie> findByNomContaining(String nom);

    List<Pharmacie> findByVille(String ville);

    List<Pharmacie> findByVilleAndNomContaining(String ville, String nom);
}
