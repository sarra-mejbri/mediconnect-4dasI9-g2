package tn.esprit.gardes.dto;

import tn.esprit.gardes.enums.TypeUrgence;

public record ServiceUrgenceResponse(
        Long id,
        String nom,
        TypeUrgence type,
        String adresse,
        Double latitude,
        Double longitude,
        String telephone,
        boolean ouvert24h,
        Long zoneId,
        String zone,
        String ville,
        Double distanceKm
) {
}
