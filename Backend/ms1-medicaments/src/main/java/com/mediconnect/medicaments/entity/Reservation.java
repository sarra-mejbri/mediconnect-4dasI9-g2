package com.mediconnect.medicaments.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String patientId;
    int quantite;
    LocalDateTime creeLe;

    @Enumerated(EnumType.STRING)
    StatutReservation statut;

    @ManyToOne
    Stock stock;

    @PrePersist
    public void initialiser() {
        if (this.creeLe == null) {
            this.creeLe = LocalDateTime.now();
        }
        if (this.statut == null) {
            this.statut = StatutReservation.EN_ATTENTE;
        }
    }
}
