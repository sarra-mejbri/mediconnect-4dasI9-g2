package tn.esprit.gardes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gardes.entity.Zone;

import java.util.Optional;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    Optional<Zone> findByNomIgnoreCase(String nom);
}
