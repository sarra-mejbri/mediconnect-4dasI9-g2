# MS4 – Traitements et rappels (MediConnect)

Spring Boot 3 · Java 17 · MySQL · Swagger

## Lancer
1. Démarrer MySQL (XAMPP/WAMP ou Docker) : utilisateur `root`, mot de passe vide
   (sinon modifier `application.properties`).
2. `mvn spring-boot:run` (ou lancer `Ms4TraitementsApplication` depuis l'IDE).
3. La base `ms4_traitements` et les tables sont créées automatiquement,
   puis remplies avec `data.sql`.
4. Swagger : http://localhost:8084/swagger-ui.html

## Endpoints (CRUD)
| Ressource | Endpoints |
|---|---|
| Traitement | `POST/GET /api/treatments` (`?patientId=`), `GET/PUT/DELETE /api/treatments/{id}` |
| Prescription | `POST/GET /api/treatments/{id}/prescriptions`, `GET /api/prescriptions`, `GET/PUT/DELETE /api/prescriptions/{id}` |
| Prise | `POST/GET /api/prescriptions/{id}/intakes`, `GET /api/treatments/{id}/intakes`, `GET/PUT/DELETE /api/intakes/{id}` |
| Rappel | `POST /api/reminders` (`?priseId=` optionnel), `GET /api/reminders` (`?patientId=`), `GET/PUT/DELETE /api/reminders/{id}` |

Codes : 201 + Location (création), 200, 204 (suppression), 400 (données invalides), 404 (introuvable).
