package com.mediconnect.medicaments.service;

import com.mediconnect.medicaments.entity.Pharmacie;

import java.util.List;

public interface IPharmacieService {

    Pharmacie addPharmacie(Pharmacie pharmacie);

    Pharmacie updatePharmacie(Pharmacie pharmacie);

    Pharmacie retrievePharmacie(Long id);

    List<Pharmacie> retrieveAllPharmacies();

    void deletePharmacie(Long id);

    // Méthode avec Keywords
    List<Pharmacie> getPharmaciesByVille(String ville);
}
