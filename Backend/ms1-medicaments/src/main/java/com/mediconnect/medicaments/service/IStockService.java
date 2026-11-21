package com.mediconnect.medicaments.service;

import com.mediconnect.medicaments.entity.Stock;

import java.util.List;

public interface IStockService {

    Stock addStock(Stock stock);

    Stock updateStock(Stock stock);

    Stock retrieveStock(Long id);

    List<Stock> retrieveAllStocks();

    void deleteStock(Long id);

    // Affectation entre entités (Séance 8)
    Stock affecterStockAPharmacieEtMedicament(Stock stock, Long idPharmacie, Long idMedicament);

    // Méthodes avec Keywords
    List<Stock> retrieveStocksFaibles(int seuil);

    List<Stock> retrieveStocksByPharmacie(Long idPharmacie);
}
