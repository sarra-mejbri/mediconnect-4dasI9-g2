package com.mediconnect.medicaments.repository;

import com.mediconnect.medicaments.entity.Reservation;
import com.mediconnect.medicaments.entity.StatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Keywords Spring Data JPA
    List<Reservation> findByPatientId(String patientId);

    List<Reservation> findByStatut(StatutReservation statut);

    List<Reservation> findByPatientIdAndStatut(String patientId, StatutReservation statut);

    List<Reservation> findByOrderByCreeLeDesc();
}
