package com.mediconnect.medicaments.service;

import com.mediconnect.medicaments.entity.Medicament;
import com.mediconnect.medicaments.entity.Pharmacie;
import com.mediconnect.medicaments.entity.Stock;
import com.mediconnect.medicaments.repository.MedicamentRepository;
import com.mediconnect.medicaments.repository.PharmacieRepository;
import com.mediconnect.medicaments.repository.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StockServiceImpl implements IStockService {

    StockRepository stockRepository;
    PharmacieRepository pharmacieRepository;
    MedicamentRepository medicamentRepository;

    @Override
    public Stock addStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock updateStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock retrieveStock(Long id) {
        return stockRepository.findById(id).orElse(null);
    }

    @Override
    public List<Stock> retrieveAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }

    @Override
    public Stock affecterStockAPharmacieEtMedicament(Stock stock, Long idPharmacie, Long idMedicament) {
        Pharmacie pharmacie = pharmacieRepository.findById(idPharmacie).orElse(null);
        Medicament medicament = medicamentRepository.findById(idMedicament).orElse(null);

        stock.setPharmacie(pharmacie);
        stock.setMedicament(medicament);

        return stockRepository.save(stock);
    }

    @Override
    public List<Stock> retrieveStocksFaibles(int seuil) {
        return stockRepository.findByQuantiteLessThanEqual(seuil);
    }

    @Override
    public List<Stock> retrieveStocksByPharmacie(Long idPharmacie) {
        return stockRepository.findByPharmacieId(idPharmacie);
    }
}
