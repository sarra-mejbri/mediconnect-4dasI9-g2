package tn.esprit.traitements.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import tn.esprit.traitements.enums.StatutPrise;

import java.time.LocalDateTime;

/** Une prise planifiée d'une prescription. */
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Prise {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime datePrevue;

    /** Renseignée quand le patient confirme la prise */
    private LocalDateTime datePrise;

    @Enumerated(EnumType.STRING)
    private StatutPrise statut;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    /** Affiché dans le JSON (lecture seule) */
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    public Long getPrescriptionId() {
        return prescription == null ? null : prescription.getId();
    }
}
