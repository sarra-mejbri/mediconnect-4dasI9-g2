package tn.esprit.gardes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.gardes.entity.ServiceUrgence;

import java.util.List;

public interface ServiceUrgenceRepository extends JpaRepository<ServiceUrgence, Long> {

    @Query(value = """
            SELECT su.*
            FROM services_urgence su
            WHERE (6371.0 * acos(least(1.0, greatest(-1.0,
                cos(radians(:latitude)) * cos(radians(su.latitude))
                * cos(radians(su.longitude) - radians(:longitude))
                + sin(radians(:latitude)) * sin(radians(su.latitude))
            )))) <= :rayonKm
            ORDER BY (6371.0 * acos(least(1.0, greatest(-1.0,
                cos(radians(:latitude)) * cos(radians(su.latitude))
                * cos(radians(su.longitude) - radians(:longitude))
                + sin(radians(:latitude)) * sin(radians(su.latitude))
            ))))
            """, nativeQuery = true)
    List<ServiceUrgence> findNearest(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("rayonKm") double rayonKm);

    boolean existsByZone_Id(Long zoneId);
}
