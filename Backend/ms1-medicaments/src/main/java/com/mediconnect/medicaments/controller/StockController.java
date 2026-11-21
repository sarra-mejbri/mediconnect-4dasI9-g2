package com.mediconnect.medicaments.controller;

import com.mediconnect.medicaments.entity.Stock;
import com.mediconnect.medicaments.service.IStockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "3. Gestion des Stocks")
@RestController
@AllArgsConstructor
@RequestMapping("/stock")
public class StockController {

    IStockService stockService;

    @Operation(summary = "Ajouter un stock")
    @PostMapping("/add")
    public Stock addStock(@RequestBody Stock stock) {
        return stockService.addStock(stock);
    }

    @Operation(summary = "Mettre à jour un stock")
    @PutMapping("/update")
    public Stock updateStock(@RequestBody Stock stock) {
        return stockService.updateStock(stock);
    }

    @Operation(summary = "Récupérer un stock par son ID")
    @GetMapping("/get/{id}")
    public Stock retrieveStock(@PathVariable("id") Long id) {
        return stockService.retrieveStock(id);
    }

    @Operation(summary = "Récupérer tous les stocks")
    @GetMapping("/all")
    public List<Stock> retrieveAllStocks() {
        return stockService.retrieveAllStocks();
    }

    @Operation(summary = "Supprimer un stock par son ID")
    @DeleteMapping("/delete/{id}")
    public void deleteStock(@PathVariable("id") Long id) {
        stockService.deleteStock(id);
    }

    @Operation(summary = "Affecter un stock à une pharmacie et un médicament")
    @PostMapping("/affecter/{idPharmacie}/{idMedicament}")
    public Stock affecterStockAPharmacieEtMedicament(
            @RequestBody Stock stock,
            @PathVariable("idPharmacie") Long idPharmacie,
            @PathVariable("idMedicament") Long idMedicament) {
        return stockService.affecterStockAPharmacieEtMedicament(stock, idPharmacie, idMedicament);
    }

    @Operation(summary = "Récupérer les stocks sous le seuil d'alerte (Keyword LessThanEqual)")
    @GetMapping("/faibles/{seuil}")
    public List<Stock> retrieveStocksFaibles(@PathVariable("seuil") int seuil) {
        return stockService.retrieveStocksFaibles(seuil);
    }

    @Operation(summary = "Récupérer les stocks d'une pharmacie (Keyword)")
    @GetMapping("/pharmacie/{idPharmacie}")
    public List<Stock> retrieveStocksByPharmacie(@PathVariable("idPharmacie") Long idPharmacie) {
        return stockService.retrieveStocksByPharmacie(idPharmacie);
    }
}
