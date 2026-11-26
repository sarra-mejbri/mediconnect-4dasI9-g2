package tn.esprit.traitements.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.traitements.entity.Prescription;
import tn.esprit.traitements.entity.Prise;
import tn.esprit.traitements.enums.StatutPrise;
import tn.esprit.traitements.exception.ResourceNotFoundException;
import tn.esprit.traitements.repository.PriseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriseService {

    private final PriseRepository repository;
    private final PrescriptionService prescriptionService;
    private final TraitementService traitementService;

    public Prise create(Long prescriptionId, Prise p) {
        Prescription prescription = prescriptionService.findById(prescriptionId);
        p.setId(null);
        p.setPrescription(prescription);
        if (p.getStatut() == null) p.setStatut(StatutPrise.PREVUE);
        return repository.save(p);
    }

    public List<Prise> findByPrescription(Long prescriptionId) {
        prescriptionService.findById(prescriptionId);
        return repository.findByPrescription_Id(prescriptionId);
    }

    public List<Prise> findByTraitement(Long traitementId) {
        traitementService.findById(traitementId);
        return repository.findByPrescription_Traitement_Id(traitementId);
    }

    public Prise findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Prise", id));
    }

    public Prise update(Long id, Prise data) {
        Prise p = findById(id);
        p.setDatePrevue(data.getDatePrevue());
        p.setDatePrise(data.getDatePrise());
        if (data.getStatut() != null) p.setStatut(data.getStatut());
        return repository.save(p);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }
}
