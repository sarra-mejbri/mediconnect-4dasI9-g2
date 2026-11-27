package com.mediconnect.ms2.service;

import com.mediconnect.ms2.entity.Laboratoire;
import com.mediconnect.ms2.exception.ResourceNotFoundException;
import com.mediconnect.ms2.repository.LaboratoireRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaboratoireService {

    private final LaboratoireRepository repository;

    public LaboratoireService(LaboratoireRepository repository) {
        this.repository = repository;
    }

    public List<Laboratoire> findAll(String ville) {
        if (ville == null || ville.isBlank()) {
            return repository.findAll();
        }
        return repository.findByVilleIgnoreCase(ville.trim());
    }

    public Laboratoire findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Laboratoire " + id + " introuvable"));
    }

    public Laboratoire create(Laboratoire laboratoire) {
        laboratoire.setId(null); // l'id est genere par la base
        return repository.save(laboratoire);
    }

    public Laboratoire update(Long id, Laboratoire donnees) {
        Laboratoire existant = findById(id);
        existant.setNom(donnees.getNom());
        existant.setAdresse(donnees.getAdresse());
        existant.setVille(donnees.getVille());
        existant.setTelephone(donnees.getTelephone());
        return repository.save(existant);
    }

    public void delete(Long id) {
        Laboratoire existant = findById(id);
        repository.delete(existant);
    }
}
