package com.mediconnect.medicaments.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pharmacie")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Pharmacie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String nom;
    String adresse;
    String ville;
    String telephone;

    @OneToMany(mappedBy = "pharmacie", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    List<Stock> stocks = new ArrayList<>();
}
