# MediConnect - Plateforme Intelligente de Coordination des Services Médicaux

Projet Microservices · Classe : 4 DAS I9 (Année universitaire 2026 – 2027)

## Équipe
- **Bochra Damak** : MS1 — Médicaments & Pharmacies
- **Yassine Gasmi** : MS2 — Analyses & Laboratoires
- **Rawen Aouina** : MS3 — Rendez-vous Médicaux
- **Sarra Mejbri** : MS4 — Traitements & Rappels
- **Bechir Belgaied** : MS5 — Gardes & Urgences
- **Rania Ben Haj Hmida** : MS6 — Hospitalisation & Séjours
- **Toute l'équipe** : MS7 — Prédiction & Recommandation (Python / FastAPI) & Infrastructure

---

## Structure du Projet
```
mediconnect-4dasI9-g2/
├── Backend/
│   ├── eureka-server/
│   ├── gateway/
│   ├── config-server/
│   ├── ms1-medicaments/        # CRUD complet Médicaments, Pharmacies, Stocks, Réservations
│   ├── ms2-dons-sang/
│   ├── ms3-rendez-vous/
│   ├── ms4-traitements/
│   ├── ms5-gardes/
│   ├── ms6-vaccinations/
│   └── ms7-statistiques/
├── Frontend/                   # Application Angular
├── Documentation/
│   ├── Diagrams/               # Diagrammes UML et d'architecture
│   ├── Dossier-Technique/      # Dossier technique complet (.docx)
│   └── Presentation/           # Présentation (.pdf)
├── docker-compose.yml
└── README.md
```

---

## Microservice MS1 - Médicaments & Pharmacies
Consultez la documentation spécifique dans [Backend/ms1-medicaments/README.md](Backend/ms1-medicaments/README.md) pour les détails d'implémentation, les endpoints REST, les règles de validation et la documentation Swagger UI (`http://localhost:8081/swagger-ui.html`).
