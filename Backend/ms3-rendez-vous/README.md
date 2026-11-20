# MS3 — Rendez-vous médicaux

API Spring Boot pour les médecins, les créneaux et les rendez-vous MediConnect.

## Démarrage local

1. Créer la base MySQL :

   ```sql
   CREATE DATABASE rendezvous_db;
   ```

2. Configurer les variables d'environnement avant le démarrage :

   ```powershell
   $env:DB_URL = "jdbc:mysql://localhost:3306/rendezvous_db?serverTimezone=Africa/Tunis"
   $env:DB_USERNAME = "root"
   $env:DB_PASSWORD = "<mot-de-passe-mysql>"
   $env:KEYCLOAK_ISSUER_URI = "http://localhost:8081/realms/mediconnect"
   ```

   `KEYCLOAK_ISSUER_URI` doit être l'issuer exact du realm utilisé par le projet. L'API attend un JWT Keycloak. Les rôles `Patient`, `Médecin`/`Medecin`, `Doctor` et `Admin` sont lus depuis `realm_access.roles`, `resource_access.*.roles` ou `roles`. L'identifiant patient vient du claim `userId`, puis `patientId`, puis `sub`.

3. Démarrer l'application depuis la racine du projet :

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

   Le schéma est créé/mis à jour par JPA (`ddl-auto=update`).

## Swagger

- UI : http://localhost:8090/swagger-ui/index.html
- JSON OpenAPI : http://localhost:8090/v3/api-docs

Dans Swagger, cliquer sur **Authorize** et coller le JWT Keycloak. Les endpoints Swagger restent accessibles sans jeton ; les opérations API exigent un JWT.

## Endpoints

| Méthode | Endpoint | Accès |
|---|---|---|
| `GET` | `/api/doctors?specialite=&ville=` | Utilisateur authentifié |
| `GET` | `/api/doctors/{id}` | Utilisateur authentifié |
| `POST` | `/api/doctors` | Admin |
| `PUT` / `DELETE` | `/api/doctors/{id}` | Admin |
| `GET` | `/api/doctors/{id}/slots` | Utilisateur authentifié |
| `POST` | `/api/doctors/{id}/slots` | Médecin propriétaire ou Admin |
| `GET` | `/api/slots/{id}` | Utilisateur authentifié |
| `PUT` / `DELETE` | `/api/slots/{id}` | Médecin propriétaire ou Admin |
| `POST` | `/api/appointments` | Patient |
| `GET` | `/api/appointments` | Patient (ses RDV), Médecin (ses RDV), Admin (tous) |
| `GET` / `PUT` | `/api/appointments/{id}` | Patient propriétaire, Médecin concerné ou Admin |
| `PATCH` | `/api/appointments/{id}/status` | Médecin concerné ou Admin |
| `PATCH` | `/api/appointments/{id}/cancel` | Patient propriétaire, Médecin concerné ou Admin |
| `DELETE` | `/api/appointments/{id}` | Annulation logique, mêmes droits |

## Exemples Postman

Ajouter `Authorization: Bearer <JWT>` à chaque requête API.

Créer un médecin (Admin) :

```http
POST /api/doctors
Content-Type: application/json
```

```json
{
  "userId": "keycloak-doctor-sub",
  "nom": "Dr Sami Ben Ali",
  "specialite": "Cardiologie",
  "ville": "Tunis"
}
```

Créer un créneau (médecin concerné ou Admin) :

```http
POST /api/doctors/1/slots
Content-Type: application/json
```

```json
{
  "debut": "2027-01-15T09:00:00",
  "fin": "2027-01-15T09:30:00"
}
```

Réserver (Patient). `patientId` est déterminé à partir du JWT et ne doit pas être envoyé :

```http
POST /api/appointments
Content-Type: application/json
```

```json
{
  "creneauId": 1,
  "motif": "Consultation de suivi"
}
```

Annuler et conserver l'historique :

```http
PATCH /api/appointments/1/cancel
```

La réservation verrouille le médecin pendant la transaction. Elle refuse un créneau déjà occupé et tout chevauchement pour ce médecin ; l'annulation libère le créneau. Un rendez-vous annulé reste en base et le créneau peut être réservé à nouveau.

## Captures Swagger

### Vue générale de l'API

![Vue générale de Swagger avec les groupes de contrôleurs et les endpoints](images/swagger-overview.png)

### Endpoints médecins et rendez-vous

![Endpoints CRUD des médecins, des créneaux et des rendez-vous](images/swagger-doctors-appointments.png)

### Statuts et schémas de requête

![Endpoints de statut et d'annulation avec les schémas OpenAPI](images/swagger-endpoints-schemas.png)
