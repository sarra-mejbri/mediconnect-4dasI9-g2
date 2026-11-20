package tn.esprit.traitements.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tn.esprit.traitements.entity.Prescription;
import tn.esprit.traitements.service.PrescriptionService;

import java.net.URI;
import java.util.List;

@Tag(name = "Prescriptions")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService service;

    @Operation(summary = "Ajouter une prescription à un traitement")
    @PostMapping("/treatments/{traitementId}/prescriptions")
    public ResponseEntity<Prescription> create(@PathVariable Long traitementId,
                                               @Valid @RequestBody Prescription p) {
        Prescription saved = service.create(traitementId, p);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/prescriptions/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @Operation(summary = "Lister les prescriptions d'un traitement")
    @GetMapping("/treatments/{traitementId}/prescriptions")
    public List<Prescription> findByTraitement(@PathVariable Long traitementId) {
        return service.findByTraitement(traitementId);
    }

    @Operation(summary = "Lister toutes les prescriptions")
    @GetMapping("/prescriptions")
    public List<Prescription> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Consulter une prescription")
    @GetMapping("/prescriptions/{id}")
    public Prescription findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Modifier une prescription")
    @PutMapping("/prescriptions/{id}")
    public Prescription update(@PathVariable Long id, @Valid @RequestBody Prescription p) {
        return service.update(id, p);
    }

    @Operation(summary = "Supprimer une prescription (et ses prises)")
    @DeleteMapping("/prescriptions/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
