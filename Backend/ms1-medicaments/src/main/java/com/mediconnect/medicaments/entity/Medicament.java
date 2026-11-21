package com.mediconnect.medicaments.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medicament")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Medicament implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String nom;
    String dosage;
    String forme;
    boolean surOrdonnance;

    @OneToMany(mappedBy = "medicament", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    List<Stock> stocks = new ArrayList<>();
}
