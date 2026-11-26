package tn.esprit.traitements.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.traitements.entity.Prise;

import java.util.List;

public interface PriseRepository extends JpaRepository<Prise, Long> {
    List<Prise> findByPrescription_Id(Long prescriptionId);
    List<Prise> findByPrescription_Traitement_Id(Long traitementId);
}
