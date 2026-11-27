package com.mediconnect.ms2.repository;

import com.mediconnect.ms2.entity.Laboratoire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LaboratoireRepository extends JpaRepository<Laboratoire, Long> {

    // GET /api/labs?ville=Tunis (insensible a la casse)
    List<Laboratoire> findByVilleIgnoreCase(String ville);
}
