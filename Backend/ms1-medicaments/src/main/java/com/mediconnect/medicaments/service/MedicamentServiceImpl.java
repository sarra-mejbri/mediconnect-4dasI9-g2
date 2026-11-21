package com.mediconnect.medicaments.service;

import com.mediconnect.medicaments.entity.Medicament;
import com.mediconnect.medicaments.repository.MedicamentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MedicamentServiceImpl implements IMedicamentService {

    MedicamentRepository medicamentRepository;

    @Override
    public Medicament addMedicament(Medicament medicament) {
        return medicamentRepository.save(medicament);
    }

    @Override
    public Medicament updateMedicament(Medicament medicament) {
        return medicamentRepository.save(medicament);
    }

    @Override
    public Medicament retrieveMedicament(Long id) {
        return medicamentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Medicament> retrieveAllMedicaments() {
        return medicamentRepository.findAll();
    }

    @Override
    public void deleteMedicament(Long id) {
        medicamentRepository.deleteById(id);
    }

    @Override
    public List<Medicament> searchMedicamentsByNom(String nom) {
        return medicamentRepository.findByNomContaining(nom);
    }

    @Override
    public List<Medicament> getMedicamentsByForme(String forme) {
        return medicamentRepository.findByForme(forme);
    }
}
