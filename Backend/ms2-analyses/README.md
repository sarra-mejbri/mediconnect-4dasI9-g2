# MS2 – Analyses médicales et laboratoires (MediConnect)

Spring Boot 3 · Java 17 · PostgreSQL · Swagger · Eureka · Config Server

## Lancer
1. Démarrer PostgreSQL : utilisateur `postgres`, mot de passe `postgres`,
   puis créer la base une seule fois : `CREATE DATABASE ms2_analyses;`
2. Démarrer dans l'ordre : `eureka-server` (8761) → `config-server` (8888) → `ms2-analyses` (8082) → `gateway` (8080).
3. La table `laboratoire` est créée automatiquement par Hibernate.
4. Swagger : http://localhost:8082/swagger-ui.html
5. Via la Gateway : http://localhost:8080/api/labs

La configuration (base, JPA, Swagger) est dans
`config-server/src/main/resources/config/ms2-analyses.properties`.

## Endpoints (CRUD)
| Ressource | Endpoints |
|---|---|
| Laboratoire | `POST/GET /api/labs` (`?ville=`), `GET/PUT/DELETE /api/labs/{id}` |

Codes : 201 + Location (création), 200, 204 (suppression), 400 (données invalides), 404 (introuvable).
