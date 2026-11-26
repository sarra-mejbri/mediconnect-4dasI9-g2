package tn.esprit.traitements.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tn.esprit.traitements.entity.Traitement;
import tn.esprit.traitements.service.TraitementService;

import java.net.URI;
import java.util.List;

@Tag(name = "Traitements")
@RestController
@RequestMapping("/api/treatments")
@RequiredArgsConstructor
public class TraitementController {

    private final TraitementService service;

    @Operation(summary = "Créer un traitement")
    @PostMapping
    public ResponseEntity<Traitement> create(@Valid @RequestBody Traitement t) {
        Traitement saved = service.create(t);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @Operation(summary = "Lister les traitements (filtre optionnel par patient)")
    @GetMapping
    public List<Traitement> findAll(@RequestParam(required = false) String patientId) {
        return service.findAll(patientId);
    }

    @Operation(summary = "Consulter un traitement")
    @GetMapping("/{id}")
    public Traitement findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Modifier un traitement")
    @PutMapping("/{id}")
    public Traitement update(@PathVariable Long id, @Valid @RequestBody Traitement t) {
        return service.update(id, t);
    }

    @Operation(summary = "Supprimer un traitement (et ses prescriptions / prises)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
