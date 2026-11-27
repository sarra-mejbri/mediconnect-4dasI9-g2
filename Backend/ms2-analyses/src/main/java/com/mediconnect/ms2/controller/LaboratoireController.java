package com.mediconnect.ms2.controller;

import com.mediconnect.ms2.entity.Laboratoire;
import com.mediconnect.ms2.service.LaboratoireService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/labs")
@Tag(name = "Laboratoires", description = "CRUD des laboratoires d'analyses (MS2)")
public class LaboratoireController {

    private final LaboratoireService service;

    public LaboratoireController(LaboratoireService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lister les laboratoires (filtre optionnel par ville)")
    @ApiResponse(responseCode = "200", description = "Liste des laboratoires")
    public List<Laboratoire> getAll(
            @Parameter(description = "Ville, ex : Tunis") @RequestParam(required = false) String ville) {
        return service.findAll(ville);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Afficher un laboratoire par son id")
    @ApiResponse(responseCode = "200", description = "Laboratoire trouve")
    @ApiResponse(responseCode = "404", description = "Laboratoire introuvable")
    public Laboratoire getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @Operation(summary = "Ajouter un laboratoire")
    @ApiResponse(responseCode = "201", description = "Laboratoire cree (en-tete Location)")
    @ApiResponse(responseCode = "400", description = "Donnees invalides")
    public ResponseEntity<Laboratoire> create(@Valid @RequestBody Laboratoire laboratoire) {
        Laboratoire cree = service.create(laboratoire);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(cree.getId()).toUri();
        return ResponseEntity.created(location).body(cree);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un laboratoire")
    @ApiResponse(responseCode = "200", description = "Laboratoire modifie")
    @ApiResponse(responseCode = "400", description = "Donnees invalides")
    @ApiResponse(responseCode = "404", description = "Laboratoire introuvable")
    public Laboratoire update(@PathVariable Long id, @Valid @RequestBody Laboratoire laboratoire) {
        return service.update(id, laboratoire);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un laboratoire")
    @ApiResponse(responseCode = "204", description = "Laboratoire supprime")
    @ApiResponse(responseCode = "404", description = "Laboratoire introuvable")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
