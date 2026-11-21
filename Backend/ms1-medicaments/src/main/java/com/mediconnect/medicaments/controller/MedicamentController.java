package com.mediconnect.medicaments.controller;

import com.mediconnect.medicaments.entity.Medicament;
import com.mediconnect.medicaments.service.IMedicamentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "2. Gestion des Médicaments")
@RestController
@AllArgsConstructor
@RequestMapping("/medicament")
public class MedicamentController {

    IMedicamentService medicamentService;

    @Operation(summary = "Ajouter un médicament")
    @PostMapping("/add")
    public Medicament addMedicament(@RequestBody Medicament medicament) {
        return medicamentService.addMedicament(medicament);
    }

    @Operation(summary = "Mettre à jour un médicament")
    @PutMapping("/update")
    public Medicament updateMedicament(@RequestBody Medicament medicament) {
        return medicamentService.updateMedicament(medicament);
    }

    @Operation(summary = "Récupérer un médicament par son ID")
    @GetMapping("/get/{id}")
    public Medicament retrieveMedicament(@PathVariable("id") Long id) {
        return medicamentService.retrieveMedicament(id);
    }

    @Operation(summary = "Récupérer tous les médicaments")
    @GetMapping("/all")
    public List<Medicament> retrieveAllMedicaments() {
        return medicamentService.retrieveAllMedicaments();
    }

    @Operation(summary = "Supprimer un médicament par son ID")
    @DeleteMapping("/delete/{id}")
    public void deleteMedicament(@PathVariable("id") Long id) {
        medicamentService.deleteMedicament(id);
    }

    @Operation(summary = "Rechercher des médicaments par nom (Keyword Containing)")
    @GetMapping("/search/{nom}")
    public List<Medicament> searchMedicamentsByNom(@PathVariable("nom") String nom) {
        return medicamentService.searchMedicamentsByNom(nom);
    }

    @Operation(summary = "Filtrer des médicaments par forme galénique (Keyword)")
    @GetMapping("/forme/{forme}")
    public List<Medicament> getMedicamentsByForme(@PathVariable("forme") String forme) {
        return medicamentService.getMedicamentsByForme(forme);
    }
}
