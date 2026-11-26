package tn.esprit.traitements.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import tn.esprit.traitements.enums.TypeRappel;

import java.time.LocalDateTime;

/** Rappel envoyé au patient (prise, rendez-vous ou résultat d'analyse). */
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Rappel {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String patientId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeRappel type;

    @NotNull
    private LocalDateTime dateEnvoi;

    @NotBlank
    private String message;

    private boolean envoye;

    /** Rappel lié à une prise (0..1), vide pour un rappel de rendez-vous ou de résultat */
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "prise_id", unique = true)
    @OnDelete(action = OnDeleteAction.SET_NULL) // si la prise est supprimée, le rappel reste
    private Prise prise;

    /** Affiché dans le JSON (lecture seule) */
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    public Long getPriseId() {
        return prise == null ? null : prise.getId();
    }
}
