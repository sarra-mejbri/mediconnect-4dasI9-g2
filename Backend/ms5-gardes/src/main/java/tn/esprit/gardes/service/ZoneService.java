package tn.esprit.gardes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.gardes.entity.Zone;
import tn.esprit.gardes.exception.ResourceConflictException;
import tn.esprit.gardes.exception.ResourceNotFoundException;
import tn.esprit.gardes.repository.GardeRepository;
import tn.esprit.gardes.repository.ServiceUrgenceRepository;
import tn.esprit.gardes.repository.ZoneRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ZoneService {

    private final ZoneRepository zoneRepository;
    private final GardeRepository gardeRepository;
    private final ServiceUrgenceRepository serviceUrgenceRepository;

    public Zone create(Zone zone) {
        zone.setId(null);
        return zoneRepository.save(zone);
    }

    @Transactional(readOnly = true)
    public List<Zone> findAll() {
        return zoneRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Zone findById(Long id) {
        return zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone", id));
    }

    public Zone update(Long id, Zone data) {
        Zone zone = findById(id);
        zone.setNom(data.getNom());
        zone.setVille(data.getVille());
        zone.setLatitude(data.getLatitude());
        zone.setLongitude(data.getLongitude());
        return zoneRepository.save(zone);
    }

    public void delete(Long id) {
        Zone zone = findById(id);
        if (gardeRepository.existsByZone_Id(id) || serviceUrgenceRepository.existsByZone_Id(id)) {
            throw new ResourceConflictException("Impossible de supprimer une zone utilisee");
        }
        zoneRepository.delete(zone);
    }
}
