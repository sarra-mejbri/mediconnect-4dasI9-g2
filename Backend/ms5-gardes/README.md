# MS5 - Gardes et urgences

Microservice de Bechir Belgaied pour MediConnect.

- Java 17, Spring Boot 3.3.5 et Spring Cloud 2023.0.3
- PostgreSQL (choix actualise pour MS5)
- Eureka, Config Server, API Gateway et Actuator
- Swagger UI : `http://localhost:8081/swagger-ui.html`

## Configuration

Le service ne conserve localement que son nom, son port et l'adresse du Config Server.
La configuration PostgreSQL se trouve dans
`Backend/config-server/src/main/resources/config/ms5-gardes.properties`.

Valeurs par defaut :

| Parametre | Valeur |
|---|---|
| Base | `ms5_gardes` |
| Utilisateur | `postgres` |
| Mot de passe | `postgres` |

Ces valeurs peuvent etre remplacees avec `MS5_DB_URL`, `MS5_DB_USERNAME` et
`MS5_DB_PASSWORD`.

Creer la base avant le premier lancement :

```sql
CREATE DATABASE ms5_gardes;
```

## Ordre de lancement

1. PostgreSQL
2. `Backend/config-server` (port 8888)
3. `Backend/eureka-server` (port 8761)
4. `Backend/ms5-gardes` (port 8081)
5. `Backend/gateway` (port 8080)

## Contrat principal

| Methode | Endpoint | Resultat attendu |
|---|---|---|
| GET | `/api/on-call/pharmacies?zone=Centre-ville&date=2026-11-28` | gardes de la zone a la date demandee |
| GET | `/api/on-call/emergency-services?lat=36.8065&lng=10.1815&rayonKm=50` | urgences triees par distance |
| POST | `/api/on-call/shifts` | `201 Created` et en-tete `Location` |
| DELETE | `/api/on-call/shifts/{id}` | `204 No Content` |

CRUD complementaire :

- `/api/zones`
- `/api/on-call/shifts`
- `/api/emergency-services`

Exemple de creation d'une garde :

```json
{
  "pharmacieId": 25,
  "zoneId": 1,
  "date": "2026-11-28",
  "heureDebut": "08:00:00",
  "heureFin": "20:00:00"
}
```

## Verification de la seance 5

| Verification | URL |
|---|---|
| Configuration centralisee | `http://localhost:8888/ms5-gardes/default` |
| Enregistrement Eureka | `http://localhost:8761` (`MS5-GARDES` doit etre `UP`) |
| Informations Actuator | `http://localhost:8081/actuator/info` |
| Sante du service et de PostgreSQL | `http://localhost:8081/actuator/health` |
| Appel direct | `http://localhost:8081/api/zones` |
| Appel via Gateway | `http://localhost:8080/api/zones` |

## Tests

Depuis `Backend/ms5-gardes` :

```bash
mvn test
```
