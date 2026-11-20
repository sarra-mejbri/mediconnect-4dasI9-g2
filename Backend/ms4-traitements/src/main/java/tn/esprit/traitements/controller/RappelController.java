package tn.esprit.traitements.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tn.esprit.traitements.entity.Rappel;
import tn.esprit.traitements.service.RappelService;

import java.net.URI;
import java.util.List;

@Tag(name = "Rappels")
@RestController
@RequestMapping("/api/reminders")
@RequiredArgsConstructor
public class RappelController {

    private final RappelService service;

    @Operation(summary = "Créer un rappel (priseId optionnel)")
    @PostMapping
    public ResponseEntity<Rappel> create(@Valid @RequestBody Rappel r,
                                         @RequestParam(required = false) Long priseId) {
        Rappel saved = service.create(r, priseId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replaceQuery(null)
                .path("/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @Operation(summary = "Lister les rappels (filtre optionnel par patient)")
    @GetMapping
    public List<Rappel> findAll(@RequestParam(required = false) String patientId) {
        return service.findAll(patientId);
    }

    @Operation(summary = "Consulter un rappel")
    @GetMapping("/{id}")
    public Rappel findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Modifier un rappel")
    @PutMapping("/{id}")
    public Rappel update(@PathVariable Long id, @Valid @RequestBody Rappel r) {
        return service.update(id, r);
    }

    @Operation(summary = "Supprimer un rappel")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
