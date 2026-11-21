package com.mediconnect.medicaments.service;

import com.mediconnect.medicaments.entity.Reservation;
import com.mediconnect.medicaments.entity.StatutReservation;

import java.util.List;

public interface IReservationService {

    Reservation addReservation(Reservation reservation, Long idStock);

    Reservation updateReservation(Reservation reservation);

    Reservation retrieveReservation(Long id);

    List<Reservation> retrieveAllReservations();

    void deleteReservation(Long id);

    Reservation changerStatutReservation(Long idReservation, StatutReservation nouveauStatut);

    // Méthodes avec Keywords
    List<Reservation> getReservationsByPatient(String patientId);

    List<Reservation> getReservationsByStatut(StatutReservation statut);
}
