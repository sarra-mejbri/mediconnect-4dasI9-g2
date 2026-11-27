package tn.esprit.gardes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tn.esprit.gardes.dto.GardeRequest;
import tn.esprit.gardes.dto.GardeResponse;
import tn.esprit.gardes.service.GardeService;

import java.net.URI;
import java.util.List;

@Tag(name = "Gardes")
@RestController
@RequestMapping("/api/on-call/shifts")
@RequiredArgsConstructor
public class GardeController {

    private final GardeService service;

    @Operation(summary = "Declarer une garde")
    @PostMapping
    public ResponseEntity<GardeResponse> create(@Valid @RequestBody GardeRequest request) {
        GardeResponse saved = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(saved.id()).toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    public List<GardeResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public GardeResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public GardeResponse update(@PathVariable Long id, @Valid @RequestBody GardeRequest request) {
        return service.update(id, request);
    }

    @Operation(summary = "Supprimer une garde")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
