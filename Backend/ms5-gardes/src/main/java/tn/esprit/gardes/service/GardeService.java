package tn.esprit.gardes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.gardes.dto.GardeRequest;
import tn.esprit.gardes.dto.GardeResponse;
import tn.esprit.gardes.entity.Garde;
import tn.esprit.gardes.entity.Zone;
import tn.esprit.gardes.exception.ResourceConflictException;
import tn.esprit.gardes.exception.ResourceNotFoundException;
import tn.esprit.gardes.repository.GardeRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GardeService {

    private final GardeRepository repository;
    private final ZoneService zoneService;

    public GardeResponse create(GardeRequest request) {
        validateHours(request);
        ensureNoOverlap(request, null);
        Zone zone = zoneService.findById(request.zoneId());
        Garde garde = Garde.builder()
                .pharmacieId(request.pharmacieId())
                .zone(zone)
                .date(request.date())
                .heureDebut(request.heureDebut())
                .heureFin(request.heureFin())
                .build();
        return toResponse(repository.save(garde));
    }

    @Transactional(readOnly = true)
    public List<GardeResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public GardeResponse findById(Long id) {
        return toResponse(getEntity(id));
    }

    @Transactional(readOnly = true)
    public List<GardeResponse> findOnCallPharmacies(String zone, LocalDate date) {
        if (zone == null || zone.isBlank()) {
            throw new IllegalArgumentException("Le parametre zone est obligatoire");
        }
        if (date == null) {
            throw new IllegalArgumentException("Le parametre date est obligatoire");
        }
        return repository.findByZone_NomIgnoreCaseAndDateOrderByHeureDebut(zone.trim(), date)
                .stream().map(this::toResponse).toList();
    }

    public GardeResponse update(Long id, GardeRequest request) {
        validateHours(request);
        ensureNoOverlap(request, id);
        Garde garde = getEntity(id);
        garde.setPharmacieId(request.pharmacieId());
        garde.setZone(zoneService.findById(request.zoneId()));
        garde.setDate(request.date());
        garde.setHeureDebut(request.heureDebut());
        garde.setHeureFin(request.heureFin());
        return toResponse(repository.save(garde));
    }

    public void delete(Long id) {
        repository.delete(getEntity(id));
    }

    private Garde getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Garde", id));
    }

    private void validateHours(GardeRequest request) {
        if (!request.heureFin().isAfter(request.heureDebut())) {
            throw new IllegalArgumentException("heureFin doit etre apres heureDebut");
        }
    }

    private void ensureNoOverlap(GardeRequest request, Long excludedId) {
        boolean overlap = excludedId == null
                ? repository.existsByPharmacieIdAndDateAndHeureDebutLessThanAndHeureFinGreaterThan(
                        request.pharmacieId(), request.date(), request.heureFin(), request.heureDebut())
                : repository.existsByPharmacieIdAndDateAndHeureDebutLessThanAndHeureFinGreaterThanAndIdNot(
                        request.pharmacieId(), request.date(), request.heureFin(), request.heureDebut(), excludedId);
        if (overlap) {
            throw new ResourceConflictException("Cette pharmacie possede deja une garde sur ce creneau");
        }
    }

    private GardeResponse toResponse(Garde garde) {
        Zone zone = garde.getZone();
        return new GardeResponse(garde.getId(), garde.getPharmacieId(), garde.getDate(),
                garde.getHeureDebut(), garde.getHeureFin(), zone.getId(), zone.getNom(), zone.getVille());
    }
}
