package com.mediconnect.ms2.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "laboratoire")
@Schema(description = "Laboratoire d'analyses medicales")
public class Laboratoire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    @Schema(example = "Laboratoire Pasteur")
    private String nom;

    @NotBlank(message = "L'adresse est obligatoire")
    @Size(max = 200)
    @Column(nullable = false, length = 200)
    @Schema(example = "12 avenue Habib Bourguiba")
    private String adresse;

    @NotBlank(message = "La ville est obligatoire")
    @Size(max = 60)
    @Column(nullable = false, length = 60)
    @Schema(example = "Tunis")
    private String ville;

    @Pattern(regexp = "^\\+?[0-9 ]{8,15}$", message = "Numero de telephone invalide")
    @Column(length = 20)
    @Schema(example = "71 123 456")
    private String telephone;

    public Laboratoire() {
    }

    public Laboratoire(String nom, String adresse, String ville, String telephone) {
        this.nom = nom;
        this.adresse = adresse;
        this.ville = ville;
        this.telephone = telephone;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
}
