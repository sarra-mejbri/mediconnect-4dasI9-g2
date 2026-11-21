package com.mediconnect.medicaments.service;

import com.mediconnect.medicaments.entity.Medicament;

import java.util.List;

public interface IMedicamentService {

    Medicament addMedicament(Medicament medicament);

    Medicament updateMedicament(Medicament medicament);

    Medicament retrieveMedicament(Long id);

    List<Medicament> retrieveAllMedicaments();

    void deleteMedicament(Long id);

    // Méthodes avec Keywords
    List<Medicament> searchMedicamentsByNom(String nom);

    List<Medicament> getMedicamentsByForme(String forme);
}
