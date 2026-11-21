# MS1 - Médicaments et Pharmacies (MediConnect)

Projet Spring Boot respectant le programme du module **Architecture des Systèmes d'Information (4 DAS I9)**.

---

## 1. Structure du Projet

L'architecture suit strictement le découpage en couches classique :
```
com.mediconnect.medicaments/
├── entity/                     # Entités JPA + Lombok
│   ├── Pharmacie.java
│   ├── Medicament.java
│   ├── Stock.java
│   ├── Reservation.java
│   └── StatutReservation.java
├── repository/                 # Interfaces JpaRepository avec Keywords
│   ├── PharmacieRepository.java
│   ├── MedicamentRepository.java
│   ├── StockRepository.java
│   └── ReservationRepository.java
├── service/                    # Couche métier (Interface + Implémentation)
│   ├── IPharmacieService.java / PharmacieServiceImpl.java
│   ├── IMedicamentService.java / MedicamentServiceImpl.java
│   ├── IStockService.java / StockServiceImpl.java
│   └── IReservationService.java / ReservationServiceImpl.java
├── controller/                 # Contrôleurs REST
│   ├── PharmacieController.java
│   ├── MedicamentController.java
│   ├── StockController.java
│   └── ReservationController.java
└── Ms1MedicamentsApplication.java
```

---

## 2. Entités JPA & Relations

* **`Pharmacie`** : `id`, `nom`, `adresse`, `ville`, `telephone`
  * `@OneToMany(mappedBy = "pharmacie")` vers `Stock`
* **`Medicament`** : `id`, `nom`, `dosage`, `forme`, `surOrdonnance`
  * `@OneToMany(mappedBy = "medicament")` vers `Stock`
* **`Stock`** : `id`, `quantite`, `seuilAlerte`, `majLe`
  * `@ManyToOne` vers `Pharmacie`
  * `@ManyToOne` vers `Medicament`
  * `@OneToMany(mappedBy = "stock")` vers `Reservation`
* **`Reservation`** : `id`, `patientId`, `quantite`, `creeLe`, `statut`
  * `@ManyToOne` vers `Stock`
  * `StatutReservation` : `EN_ATTENTE`, `CONFIRMEE`, `RETIREE`, `ANNULEE`

---

## 3. Méthodes Dérivées (Keywords Spring Data JPA)

Conformément au **Chapitre 11** du cours, les repositories utilisent les mots-clés suivants :

* **`PharmacieRepository`** :
  * `findByNom(String nom)`
  * `findByNomContaining(String nom)`
  * `findByVille(String ville)`
  * `findByVilleAndNomContaining(String ville, String nom)`
* **`MedicamentRepository`** :
  * `findByNomContaining(String nom)`
  * `findByForme(String forme)`
  * `findBySurOrdonnance(boolean surOrdonnance)`
  * `findByNomContainingAndSurOrdonnance(String nom, boolean surOrdonnance)`
* **`StockRepository`** :
  * `findByPharmacieId(Long pharmacieId)`
  * `findByMedicamentId(Long medicamentId)`
  * `findByPharmacieIdAndMedicamentId(Long pharmacieId, Long medicamentId)`
  * `findByQuantiteLessThanEqual(int seuil)` *(pour détecter les alertes de stock faible)*
* **`ReservationRepository`** :
  * `findByPatientId(String patientId)`
  * `findByStatut(StatutReservation statut)`
  * `findByPatientIdAndStatut(String patientId, StatutReservation statut)`
  * `findByOrderByCreeLeDesc()`

---

## 4. Endpoints REST du CRUD

### Pharmacies (`/pharmacie`)
* `POST /pharmacie/add` : Ajouter une pharmacie
* `PUT /pharmacie/update` : Mettre à jour une pharmacie
* `GET /pharmacie/get/{id}` : Récupérer une pharmacie par son ID
* `GET /pharmacie/all` : Récupérer toutes les pharmacies
* `DELETE /pharmacie/delete/{id}` : Supprimer une pharmacie
* `GET /pharmacie/by-ville/{ville}` : Recherche par ville (Keyword)

### Médicaments (`/medicament`)
* `POST /medicament/add` : Ajouter un médicament
* `PUT /medicament/update` : Mettre à jour un médicament
* `GET /medicament/get/{id}` : Récupérer un médicament par son ID
* `GET /medicament/all` : Récupérer tous les médicaments
* `DELETE /medicament/delete/{id}` : Supprimer un médicament
* `GET /medicament/search/{nom}` : Recherche par nom contenant (Keyword)
* `GET /medicament/forme/{forme}` : Filtrer par forme (Keyword)

### Stocks (`/stock`)
* `POST /stock/add` : Ajouter un stock
* `POST /stock/affecter/{idPharmacie}/{idMedicament}` : Affecter un stock à une pharmacie et un médicament
* `PUT /stock/update` : Mettre à jour un stock
* `GET /stock/get/{id}` : Récupérer un stock
* `GET /stock/all` : Récupérer tous les stocks
* `DELETE /stock/delete/{id}` : Supprimer un stock
* `GET /stock/faibles/{seuil}` : Récupérer les stocks sous le seuil d'alerte (Keyword)
* `GET /stock/pharmacie/{idPharmacie}` : Récupérer les stocks d'une pharmacie (Keyword)

### Réservations (`/reservation`)
* `POST /reservation/add/{idStock}` : Créer une réservation (vérifie et décrémente automatiquement le stock)
* `PUT /reservation/update` : Mettre à jour une réservation
* `GET /reservation/get/{id}` : Récupérer une réservation
* `GET /reservation/all` : Récupérer toutes les réservations
* `DELETE /reservation/delete/{id}` : Supprimer une réservation (restitue le stock si active)
* `PUT /reservation/changer-statut/{id}/{statut}` : Changer le statut (si `ANNULEE`, le stock est restitué)
* `GET /reservation/patient/{patientId}` : Réservations d'un patient (Keyword)
* `GET /reservation/statut/{statut}` : Réservations par statut (Keyword)

---

## 5. Configuration (`application.properties`)

```properties
server.port=8081
spring.datasource.url=jdbc:mysql://localhost:3306/mediconnect_ms1?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
