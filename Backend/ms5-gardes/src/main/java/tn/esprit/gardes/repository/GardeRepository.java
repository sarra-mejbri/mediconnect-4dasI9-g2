package tn.esprit.gardes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gardes.entity.Garde;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface GardeRepository extends JpaRepository<Garde, Long> {

    List<Garde> findByZone_NomIgnoreCaseAndDateOrderByHeureDebut(String zone, LocalDate date);

    boolean existsByPharmacieIdAndDateAndHeureDebutLessThanAndHeureFinGreaterThan(
            Long pharmacieId, LocalDate date, LocalTime heureFin, LocalTime heureDebut);

    boolean existsByPharmacieIdAndDateAndHeureDebutLessThanAndHeureFinGreaterThanAndIdNot(
            Long pharmacieId, LocalDate date, LocalTime heureFin, LocalTime heureDebut, Long id);

    boolean existsByZone_Id(Long zoneId);
}
