package com.mediconnect.medicaments.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Stock implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    int quantite;
    int seuilAlerte;
    LocalDateTime majLe;

    @ManyToOne
    Pharmacie pharmacie;

    @ManyToOne
    Medicament medicament;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    List<Reservation> reservations = new ArrayList<>();

    @PrePersist
    @PreUpdate
    public void mettreAJourDate() {
        this.majLe = LocalDateTime.now();
    }
}
