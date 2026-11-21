package com.mediconnect.medicaments.controller;

import com.mediconnect.medicaments.entity.Pharmacie;
import com.mediconnect.medicaments.service.IPharmacieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "1. Gestion des Pharmacies")
@RestController
@AllArgsConstructor
@RequestMapping("/pharmacie")
public class PharmacieController {

    IPharmacieService pharmacieService;

    @Operation(summary = "Ajouter une pharmacie")
    @PostMapping("/add")
    public Pharmacie addPharmacie(@RequestBody Pharmacie pharmacie) {
        return pharmacieService.addPharmacie(pharmacie);
    }

    @Operation(summary = "Mettre à jour une pharmacie")
    @PutMapping("/update")
    public Pharmacie updatePharmacie(@RequestBody Pharmacie pharmacie) {
        return pharmacieService.updatePharmacie(pharmacie);
    }

    @Operation(summary = "Récupérer une pharmacie par son ID")
    @GetMapping("/get/{id}")
    public Pharmacie retrievePharmacie(@PathVariable("id") Long id) {
        return pharmacieService.retrievePharmacie(id);
    }

    @Operation(summary = "Récupérer toutes les pharmacies")
    @GetMapping("/all")
    public List<Pharmacie> retrieveAllPharmacies() {
        return pharmacieService.retrieveAllPharmacies();
    }

    @Operation(summary = "Supprimer une pharmacie par son ID")
    @DeleteMapping("/delete/{id}")
    public void deletePharmacie(@PathVariable("id") Long id) {
        pharmacieService.deletePharmacie(id);
    }

    @Operation(summary = "Rechercher des pharmacies par ville (Keyword)")
    @GetMapping("/by-ville/{ville}")
    public List<Pharmacie> getPharmaciesByVille(@PathVariable("ville") String ville) {
        return pharmacieService.getPharmaciesByVille(ville);
    }
}
