package tn.esprit.traitements.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.traitements.entity.Prescription;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByTraitement_Id(Long traitementId);
}
