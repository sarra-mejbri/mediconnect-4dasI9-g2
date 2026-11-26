package tn.esprit.traitements.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.traitements.entity.Prescription;
import tn.esprit.traitements.entity.Traitement;
import tn.esprit.traitements.exception.ResourceNotFoundException;
import tn.esprit.traitements.repository.PrescriptionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository repository;
    private final TraitementService traitementService;

    public Prescription create(Long traitementId, Prescription p) {
        Traitement t = traitementService.findById(traitementId);
        p.setId(null);
        p.setTraitement(t);
        return repository.save(p);
    }

    public List<Prescription> findByTraitement(Long traitementId) {
        traitementService.findById(traitementId); // 404 si le traitement n'existe pas
        return repository.findByTraitement_Id(traitementId) ;
    }

    public List<Prescription> findAll() {
        return repository.findAll();
    }

    public Prescription findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Prescription", id));
    }

    public Prescription update(Long id, Prescription data) {
        Prescription p = findById(id);
        p.setMedicamentId(data.getMedicamentId());
        p.setPosologie(data.getPosologie());
        p.setFrequenceParJour(data.getFrequenceParJour());
        p.setHeuresPrise(data.getHeuresPrise());
        return repository.save(p);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }
}
