package tn.esprit.traitements.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tn.esprit.traitements.entity.Prise;
import tn.esprit.traitements.service.PriseService;

import java.net.URI;
import java.util.List;

@Tag(name = "Prises")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PriseController {

    private final PriseService service;

    @Operation(summary = "Planifier une prise pour une prescription")
    @PostMapping("/prescriptions/{prescriptionId}/intakes")
    public ResponseEntity<Prise> create(@PathVariable Long prescriptionId, @Valid @RequestBody Prise p) {
        Prise saved = service.create(prescriptionId, p);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/intakes/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @Operation(summary = "Lister les prises d'une prescription")
    @GetMapping("/prescriptions/{prescriptionId}/intakes")
    public List<Prise> findByPrescription(@PathVariable Long prescriptionId) {
        return service.findByPrescription(prescriptionId);
    }

    @Operation(summary = "Lister toutes les prises d'un traitement")
    @GetMapping("/treatments/{traitementId}/intakes")
    public List<Prise> findByTraitement(@PathVariable Long traitementId) {
        return service.findByTraitement(traitementId);
    }

    @Operation(summary = "Consulter une prise")
    @GetMapping("/intakes/{id}")
    public Prise findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Modifier une prise (ex. statut CONFIRMEE + datePrise)")
    @PutMapping("/intakes/{id}")
    public Prise update(@PathVariable Long id, @Valid @RequestBody Prise p) {
        return service.update(id, p);
    }

    @Operation(summary = "Supprimer une prise")
    @DeleteMapping("/intakes/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
