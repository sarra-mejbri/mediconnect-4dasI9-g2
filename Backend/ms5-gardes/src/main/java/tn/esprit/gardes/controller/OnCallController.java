package tn.esprit.gardes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.gardes.dto.GardeResponse;
import tn.esprit.gardes.dto.ServiceUrgenceResponse;
import tn.esprit.gardes.service.GardeService;
import tn.esprit.gardes.service.ServiceUrgenceService;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Recherche de garde et d'urgence")
@RestController
@RequestMapping("/api/on-call")
@RequiredArgsConstructor
public class OnCallController {

    private final GardeService gardeService;
    private final ServiceUrgenceService urgenceService;

    @Operation(summary = "Rechercher les pharmacies de garde par zone et date")
    @GetMapping("/pharmacies")
    public List<GardeResponse> findOnCallPharmacies(
            @RequestParam String zone,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return gardeService.findOnCallPharmacies(zone, date);
    }

    @Operation(summary = "Rechercher les services d'urgence les plus proches")
    @GetMapping("/emergency-services")
    public List<ServiceUrgenceResponse> findNearestEmergencyServices(
            @RequestParam("lat") double latitude,
            @RequestParam("lng") double longitude,
            @RequestParam(defaultValue = "50") double rayonKm) {
        return urgenceService.findNearest(latitude, longitude, rayonKm);
    }
}
