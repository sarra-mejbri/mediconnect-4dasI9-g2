-- Données de test MS4 (INSERT IGNORE : pas de doublons au redémarrage)
-- Le patient P001 a deux traitements : un patient peut avoir plusieurs traitements.

INSERT IGNORE INTO traitement (id, patient_id, medecin_id, date_debut, date_fin, statut) VALUES
(1, 'P001', 'M001', '2026-11-01', '2026-11-10', 'TERMINE'),
(2, 'P001', 'M002', '2026-11-15', '2026-12-15', 'EN_COURS'),
(3, 'P002', 'M001', '2026-11-18', '2026-11-25', 'EN_COURS'),
(4, 'P003', 'M003', '2026-10-01', '2026-10-05', 'ANNULE');

INSERT IGNORE INTO prescription (id, medicament_id, posologie, frequence_par_jour, heures_prise, traitement_id) VALUES
(1, 10, '1 comprimé de 500 mg', 3, '08:00,14:00,20:00', 1),
(2, 12, '1 gélule', 1, '08:00', 2),
(3, 15, '2 comprimés', 2, '09:00,21:00', 2),
(4, 10, '1 comprimé de 1 g', 2, '08:00,20:00', 3),
(5, 20, '10 ml de sirop', 3, '08:00,13:00,19:00', 4);

INSERT IGNORE INTO prise (id, date_prevue, date_prise, statut, prescription_id) VALUES
(1, '2026-11-15 08:00:00', '2026-11-15 08:05:00', 'CONFIRMEE', 2),
(2, '2026-11-16 08:00:00', NULL, 'OUBLIEE', 2),
(3, '2026-11-20 08:00:00', NULL, 'PREVUE', 2),
(4, '2026-11-20 09:00:00', NULL, 'PREVUE', 3),
(5, '2026-11-20 08:00:00', '2026-11-20 08:10:00', 'CONFIRMEE', 4),
(6, '2026-11-20 20:00:00', NULL, 'PREVUE', 4);

INSERT IGNORE INTO rappel (id, patient_id, type, date_envoi, message, envoye, prise_id) VALUES
(1, 'P001', 'PRISE', '2026-11-20 07:50:00', 'Pensez à prendre votre gélule de 8h', true, 3),
(2, 'P001', 'PRISE', '2026-11-20 08:50:00', 'Pensez à prendre vos 2 comprimés de 9h', false, 4),
(3, 'P002', 'RENDEZ_VOUS', '2026-11-21 18:00:00', 'Rendez-vous demain à 10h avec le Dr M001', false, NULL),
(4, 'P001', 'RESULTAT', '2026-11-19 16:00:00', 'Votre résultat d''analyse est disponible', true, NULL);
