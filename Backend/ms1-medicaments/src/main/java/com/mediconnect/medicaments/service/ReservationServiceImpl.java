package com.mediconnect.medicaments.service;

import com.mediconnect.medicaments.entity.Reservation;
import com.mediconnect.medicaments.entity.StatutReservation;
import com.mediconnect.medicaments.entity.Stock;
import com.mediconnect.medicaments.repository.ReservationRepository;
import com.mediconnect.medicaments.repository.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    ReservationRepository reservationRepository;
    StockRepository stockRepository;

    @Override
    @Transactional
    public Reservation addReservation(Reservation reservation, Long idStock) {
        Stock stock = stockRepository.findById(idStock).orElse(null);
        if (stock == null) {
            return null;
        }

        // Vérification de la disponibilité du stock
        if (stock.getQuantite() < reservation.getQuantite()) {
            throw new RuntimeException("Stock insuffisant ! Quantité disponible : " + stock.getQuantite());
        }

        // Décrémenter le stock
        stock.setQuantite(stock.getQuantite() - reservation.getQuantite());
        stockRepository.save(stock);

        // Affecter le stock à la réservation
        reservation.setStock(stock);
        reservation.setStatut(StatutReservation.EN_ATTENTE);

        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation retrieveReservation(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Reservation> retrieveAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        if (reservation != null) {
            // Restituer le stock si la réservation était active
            if (reservation.getStatut() == StatutReservation.EN_ATTENTE || reservation.getStatut() == StatutReservation.CONFIRMEE) {
                Stock stock = reservation.getStock();
                if (stock != null) {
                    stock.setQuantite(stock.getQuantite() + reservation.getQuantite());
                    stockRepository.save(stock);
                }
            }
            reservationRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public Reservation changerStatutReservation(Long idReservation, StatutReservation nouveauStatut) {
        Reservation reservation = reservationRepository.findById(idReservation).orElse(null);
        if (reservation == null) {
            return null;
        }

        // Si annulation, restituer la quantité au stock
        if (nouveauStatut == StatutReservation.ANNULEE && reservation.getStatut() != StatutReservation.ANNULEE) {
            Stock stock = reservation.getStock();
            if (stock != null) {
                stock.setQuantite(stock.getQuantite() + reservation.getQuantite());
                stockRepository.save(stock);
            }
        }

        reservation.setStatut(nouveauStatut);
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservationsByPatient(String patientId) {
        return reservationRepository.findByPatientId(patientId);
    }

    @Override
    public List<Reservation> getReservationsByStatut(StatutReservation statut) {
        return reservationRepository.findByStatut(statut);
    }
}
