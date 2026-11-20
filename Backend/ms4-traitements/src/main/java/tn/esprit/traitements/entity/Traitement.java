package tn.esprit.traitements.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import tn.esprit.traitements.enums.StatutTraitement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Un traitement est prescrit par un médecin à un patient (un patient peut avoir plusieurs traitements). */
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Traitement {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Identifiant du patient (compte Keycloak) */
    @NotBlank
    private String patientId;

    /** Identifiant du médecin prescripteur (compte Keycloak) */
    @NotBlank
    private String medecinId;

    @NotNull
    private LocalDate dateDebut;

    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    private StatutTraitement statut;

    @JsonIgnore
    @OneToMany(mappedBy = "traitement", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Prescription> prescriptions = new ArrayList<>();
}
