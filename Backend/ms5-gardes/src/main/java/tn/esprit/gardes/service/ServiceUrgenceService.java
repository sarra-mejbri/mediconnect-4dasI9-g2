package tn.esprit.gardes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.gardes.dto.ServiceUrgenceRequest;
import tn.esprit.gardes.dto.ServiceUrgenceResponse;
import tn.esprit.gardes.entity.ServiceUrgence;
import tn.esprit.gardes.entity.Zone;
import tn.esprit.gardes.exception.ResourceNotFoundException;
import tn.esprit.gardes.repository.ServiceUrgenceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceUrgenceService {

    private static final double EARTH_RADIUS_KM = 6371.0;

    private final ServiceUrgenceRepository repository;
    private final ZoneService zoneService;

    public ServiceUrgenceResponse create(ServiceUrgenceRequest request) {
        ServiceUrgence entity = new ServiceUrgence();
        apply(entity, request);
        return toResponse(repository.save(entity), null);
    }

    @Transactional(readOnly = true)
    public List<ServiceUrgenceResponse> findAll() {
        return repository.findAll().stream().map(entity -> toResponse(entity, null)).toList();
    }

    @Transactional(readOnly = true)
    public ServiceUrgenceResponse findById(Long id) {
        return toResponse(getEntity(id), null);
    }

    @Transactional(readOnly = true)
    public List<ServiceUrgenceResponse> findNearest(double latitude, double longitude, double rayonKm) {
        validateCoordinates(latitude, longitude, rayonKm);
        return repository.findNearest(latitude, longitude, rayonKm).stream()
                .map(entity -> toResponse(entity,
                        distanceKm(latitude, longitude, entity.getLatitude(), entity.getLongitude())))
                .toList();
    }

    public ServiceUrgenceResponse update(Long id, ServiceUrgenceRequest request) {
        ServiceUrgence entity = getEntity(id);
        apply(entity, request);
        return toResponse(repository.save(entity), null);
    }

    public void delete(Long id) {
        repository.delete(getEntity(id));
    }

    private ServiceUrgence getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service d'urgence", id));
    }

    private void apply(ServiceUrgence entity, ServiceUrgenceRequest request) {
        entity.setNom(request.nom());
        entity.setType(request.type());
        entity.setAdresse(request.adresse());
        entity.setLatitude(request.latitude());
        entity.setLongitude(request.longitude());
        entity.setTelephone(request.telephone());
        entity.setOuvert24h(request.ouvert24h());
        entity.setZone(zoneService.findById(request.zoneId()));
    }

    private ServiceUrgenceResponse toResponse(ServiceUrgence entity, Double distanceKm) {
        Zone zone = entity.getZone();
        Double roundedDistance = distanceKm == null ? null : Math.round(distanceKm * 100.0) / 100.0;
        return new ServiceUrgenceResponse(entity.getId(), entity.getNom(), entity.getType(),
                entity.getAdresse(), entity.getLatitude(), entity.getLongitude(), entity.getTelephone(),
                entity.isOuvert24h(), zone.getId(), zone.getNom(), zone.getVille(), roundedDistance);
    }

    private void validateCoordinates(double latitude, double longitude, double rayonKm) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("lat doit etre comprise entre -90 et 90");
        }
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("lng doit etre comprise entre -180 et 180");
        }
        if (rayonKm <= 0 || rayonKm > 500) {
            throw new IllegalArgumentException("rayonKm doit etre compris entre 0 et 500");
        }
    }

    private double distanceKm(double lat1, double lng1, double lat2, double lng2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        return EARTH_RADIUS_KM * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}
