package com.mediconnect.medicaments.service;

import com.mediconnect.medicaments.entity.Pharmacie;
import com.mediconnect.medicaments.repository.PharmacieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PharmacieServiceImpl implements IPharmacieService {

    PharmacieRepository pharmacieRepository;

    @Override
    public Pharmacie addPharmacie(Pharmacie pharmacie) {
        return pharmacieRepository.save(pharmacie);
    }

    @Override
    public Pharmacie updatePharmacie(Pharmacie pharmacie) {
        return pharmacieRepository.save(pharmacie);
    }

    @Override
    public Pharmacie retrievePharmacie(Long id) {
        return pharmacieRepository.findById(id).orElse(null);
    }

    @Override
    public List<Pharmacie> retrieveAllPharmacies() {
        return pharmacieRepository.findAll();
    }

    @Override
    public void deletePharmacie(Long id) {
        pharmacieRepository.deleteById(id);
    }

    @Override
    public List<Pharmacie> getPharmaciesByVille(String ville) {
        return pharmacieRepository.findByVille(ville);
    }
}
