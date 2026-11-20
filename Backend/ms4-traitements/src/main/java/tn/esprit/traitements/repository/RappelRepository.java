package tn.esprit.traitements.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.traitements.entity.Rappel;

import java.util.List;

public interface RappelRepository extends JpaRepository<Rappel, Long> {
    List<Rappel> findByPatientId(String patientId);
}
