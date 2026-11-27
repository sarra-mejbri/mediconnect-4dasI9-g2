package tn.esprit.gardes.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record GardeRequest(
        @NotNull Long pharmacieId,
        @NotNull Long zoneId,
        @NotNull @FutureOrPresent LocalDate date,
        @NotNull LocalTime heureDebut,
        @NotNull LocalTime heureFin
) {
}
