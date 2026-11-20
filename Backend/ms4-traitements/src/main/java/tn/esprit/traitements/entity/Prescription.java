package tn.esprit.traitements.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/** Une ligne du traitement : un médicament (référence MS1) avec sa posologie. */
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Prescription {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Référence vers un médicament de MS1 (pas de clé étrangère entre bases) */
    @NotNull
    private Long medicamentId;

    @NotBlank
    private String posologie;

    @Min(1)
    private int frequenceParJour;

    /** Exemple : "08:00,20:00" */
    private String heuresPrise;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "traitement_id")
    private Traitement traitement;

    @JsonIgnore
    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Prise> prises = new ArrayList<>();

    /** Affiché dans le JSON (lecture seule) */
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    public Long getTraitementId() {
        return traitement == null ? null : traitement.getId();
    }
}
