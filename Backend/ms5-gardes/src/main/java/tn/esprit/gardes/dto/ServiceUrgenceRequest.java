package tn.esprit.gardes.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import tn.esprit.gardes.enums.TypeUrgence;

public record ServiceUrgenceRequest(
        @NotBlank String nom,
        @NotNull TypeUrgence type,
        @NotBlank String adresse,
        @NotNull @DecimalMin("-90.0") @DecimalMax("90.0") Double latitude,
        @NotNull @DecimalMin("-180.0") @DecimalMax("180.0") Double longitude,
        @NotBlank @Pattern(regexp = "^[+0-9][0-9 ]{7,19}$", message = "format de telephone invalide") String telephone,
        boolean ouvert24h,
        @NotNull Long zoneId
) {
}
