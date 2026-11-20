package tn.esprit.traitements.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.traitements.entity.Rappel;
import tn.esprit.traitements.exception.ResourceNotFoundException;
import tn.esprit.traitements.repository.RappelRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RappelService {

    private final RappelRepository repository;
    private final PriseService priseService;

    /** priseId est optionnel : un rappel peut être lié à une prise ou non (rendez-vous, résultat). */
    public Rappel create(Rappel r, Long priseId) {
        r.setId(null);
        r.setPrise(priseId == null ? null : priseService.findById(priseId));
        return repository.save(r);
    }

    public List<Rappel> findAll(String patientId) {
        return patientId == null ? repository.findAll() : repository.findByPatientId(patientId);
    }

    public Rappel findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rappel", id));
    }

    public Rappel update(Long id, Rappel data) {
        Rappel r = findById(id);
        r.setPatientId(data.getPatientId());
        r.setType(data.getType());
        r.setDateEnvoi(data.getDateEnvoi());
        r.setMessage(data.getMessage());
        r.setEnvoye(data.isEnvoye());
        return repository.save(r);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }
}
