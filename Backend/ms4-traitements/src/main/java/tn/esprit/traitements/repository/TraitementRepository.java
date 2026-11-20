package tn.esprit.traitements.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.traitements.entity.Traitement;

import java.util.List;

public interface TraitementRepository extends JpaRepository<Traitement, Long> {
    List<Traitement> findByPatientId(String patientId);
}
