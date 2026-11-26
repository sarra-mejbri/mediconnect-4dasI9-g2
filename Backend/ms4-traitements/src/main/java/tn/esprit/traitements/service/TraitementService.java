package tn.esprit.traitements.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.traitements.entity.Traitement;
import tn.esprit.traitements.enums.StatutTraitement;
import tn.esprit.traitements.exception.ResourceNotFoundException;
import tn.esprit.traitements.repository.TraitementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TraitementService {

    private final TraitementRepository repository;

    public Traitement create(Traitement t) {
        t.setId(null);
        if (t.getStatut() == null) t.setStatut(StatutTraitement.EN_COURS);
        return repository.save(t);
    }

    public List<Traitement> findAll(String patientId) {
        return patientId == null ? repository.findAll() : repository.findByPatientId(patientId);
    }

    public Traitement findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Traitement", id));
    }

    public Traitement update(Long id, Traitement data) {
        Traitement t = findById(id);
        t.setPatientId(data.getPatientId());
        t.setMedecinId(data.getMedecinId());
        t.setDateDebut(data.getDateDebut());
        t.setDateFin(data.getDateFin());
        if (data.getStatut() != null) t.setStatut(data.getStatut());
        return repository.save(t);
    }

    public void delete(Long id) {
        repository.delete(findById(id)); // supprime aussi ses prescriptions et prises (cascade)
    }
}
