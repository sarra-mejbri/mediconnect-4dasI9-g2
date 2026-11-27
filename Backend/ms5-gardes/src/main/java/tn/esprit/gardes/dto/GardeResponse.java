package tn.esprit.gardes.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record GardeResponse(
        Long id,
        Long pharmacieId,
        LocalDate date,
        LocalTime heureDebut,
        LocalTime heureFin,
        Long zoneId,
        String zone,
        String ville
) {
}
