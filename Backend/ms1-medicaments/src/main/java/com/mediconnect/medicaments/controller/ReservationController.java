package com.mediconnect.medicaments.controller;

import com.mediconnect.medicaments.entity.Reservation;
import com.mediconnect.medicaments.entity.StatutReservation;
import com.mediconnect.medicaments.service.IReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "4. Gestion des Réservations")
@RestController
@AllArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    IReservationService reservationService;

    @Operation(summary = "Ajouter une réservation sur un stock donné")
    @PostMapping("/add/{idStock}")
    public Reservation addReservation(
            @RequestBody Reservation reservation,
            @PathVariable("idStock") Long idStock) {
        return reservationService.addReservation(reservation, idStock);
    }

    @Operation(summary = "Mettre à jour une réservation")
    @PutMapping("/update")
    public Reservation updateReservation(@RequestBody Reservation reservation) {
        return reservationService.updateReservation(reservation);
    }

    @Operation(summary = "Récupérer une réservation par son ID")
    @GetMapping("/get/{id}")
    public Reservation retrieveReservation(@PathVariable("id") Long id) {
        return reservationService.retrieveReservation(id);
    }

    @Operation(summary = "Récupérer toutes les réservations")
    @GetMapping("/all")
    public List<Reservation> retrieveAllReservations() {
        return reservationService.retrieveAllReservations();
    }

    @Operation(summary = "Supprimer une réservation par son ID")
    @DeleteMapping("/delete/{id}")
    public void deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservation(id);
    }

    @Operation(summary = "Changer le statut d'une réservation (EN_ATTENTE, CONFIRMEE, RETIREE, ANNULEE)")
    @PutMapping("/changer-statut/{id}/{statut}")
    public Reservation changerStatutReservation(
            @PathVariable("id") Long id,
            @PathVariable("statut") StatutReservation statut) {
        return reservationService.changerStatutReservation(id, statut);
    }

    @Operation(summary = "Récupérer les réservations d'un patient (Keyword)")
    @GetMapping("/patient/{patientId}")
    public List<Reservation> getReservationsByPatient(@PathVariable("patientId") String patientId) {
        return reservationService.getReservationsByPatient(patientId);
    }

    @Operation(summary = "Récupérer les réservations par statut (Keyword)")
    @GetMapping("/statut/{statut}")
    public List<Reservation> getReservationsByStatut(@PathVariable("statut") StatutReservation statut) {
        return reservationService.getReservationsByStatut(statut);
    }
}
