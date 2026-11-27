package tn.esprit.gardes.controller;

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
import tn.esprit.gardes.dto.ServiceUrgenceRequest;
import tn.esprit.gardes.dto.ServiceUrgenceResponse;
import tn.esprit.gardes.service.ServiceUrgenceService;

import java.net.URI;
import java.util.List;

@Tag(name = "Services d'urgence")
@RestController
@RequestMapping("/api/emergency-services")
@RequiredArgsConstructor
public class ServiceUrgenceController {

    private final ServiceUrgenceService service;

    @PostMapping
    public ResponseEntity<ServiceUrgenceResponse> create(@Valid @RequestBody ServiceUrgenceRequest request) {
        ServiceUrgenceResponse saved = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(saved.id()).toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    public List<ServiceUrgenceResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ServiceUrgenceResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public ServiceUrgenceResponse update(@PathVariable Long id,
                                         @Valid @RequestBody ServiceUrgenceRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
