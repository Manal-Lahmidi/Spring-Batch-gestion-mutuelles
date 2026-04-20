# Gestion des Dossiers de Mutuelle avec Spring Batch et Calcul de Remboursement

## Contexte du Projet
Dans un système de mutuelle, chaque dossier déposé par un assuré peut inclure une consultation et des traitements médicaux. Ce projet consiste à créer une application de traitement par lots avec **Spring Batch** pour :
- Automatiser la gestion des dossiers,
- Valider les données,
- Calculer les montants à rembourser à partir d'une base de médicaments référentiels.

---

## Objectifs
Développer une application batch capable de :
1. Lire des dossiers de mutuelle depuis une source de données (fichier JSON).
2. Valider les informations essentielles de chaque dossier.
3. Calculer le total des remboursements, en utilisant :
   - Un pourcentage sur le prix de la consultation.
   - Les prix de référence et taux de remboursement pour les médicaments prescrits.
4. Archiver les dossiers traités dans une base de données.

---

## Fonctionnalités

### 1. Lecture des Dossiers
Utilisation d'un **JsonItemReader** pour lire les données JSON.  
Un dossier contient :
- **Assuré** : nom, numéro d'affiliation, immatriculation.
- **Bénéficiaire** : nom, lien de parenté, date de dépôt du dossier.
- **Consultation et frais** : montant total des frais, prix de la consultation, nombre de pièces jointes.
- **Traitements** : chaque traitement inclut le code-barre, nom, type de médicament, prix, et disponibilité.

---

### 2. Base de Médicaments Référentiels
Création d'une source de données comprenant :
- Nom du médicament.
- Prix de référence.
- Pourcentage de remboursement applicable.

---

### 3. Validation des Données
Règles de validation :
- Le **nom de l’assuré** et le **numéro d’affiliation** ne doivent pas être vides.
- Les **prix** (consultation et montant total des frais) doivent être positifs.
- La **liste des traitements** ne doit pas être vide.

---

### 4. Calcul du Remboursement
- Appliquer un pourcentage fixe de remboursement sur le prix de la consultation.
- Mapper chaque traitement à un médicament référentiel pour calculer le remboursement en appliquant le pourcentage défini.

---

### 5. Enchaînement des Processeurs
Utilisation d'un **CompositeItemProcessor** pour enchaîner :
1. **ValidationProcessor** : vérifie les contraintes de validation.
2. **CalculProcessor** : calcule le total du remboursement en combinant :
   - **ConsultationProcessor** : calcule le remboursement de la consultation.
   - **TraitementMappingProcessor** : mappe les traitements aux médicaments référentiels.
   - **TraitementRemboursementProcessor** : calcule le remboursement par traitement.
   - **TotalRemboursementProcessor** : additionne les remboursements pour obtenir le total.

---

### 6. Écriture des Données
Les dossiers traités sont enregistrés dans une base de données ou un fichier de sortie pour archivage.

---

## Exemple de Fichier JSON
```json
[
  {
    "nomAssure": "Ibrahimi",
    "numeroAffiliation": "AFF123456",
    "immatriculation": "IMM098765",
    "lienParente": "fils",
    "montantTotalFrais": 150.0,
    "prixConsultation": 50.0,
    "nombrePiecesJointes": 3,
    "nomBeneficiaire": "Omar",
    "dateDepotDossier": "2024-11-10",
    "traitements": [
      {
        "codeBarre": "1234567890",
        "existe": true,
        "nomMedicament": "Paracétamol",
        "typeMedicament": "Antalgique",
        "prixMedicament": 5.0
      },
      {
        "codeBarre": "0987654321",
        "existe": false,
        "nomMedicament": "Ibuprofène",
        "typeMedicament": "Anti-inflammatoire",
        "prixMedicament": 8.0
      }
    ]
  },
  {
    "nomAssure": "Farouk",
    "numeroAffiliation": "AFF654321",
    "immatriculation": "IMM123098",
    "lienParente": "épouse",
    "montantTotalFrais": 200.0,
    "prixConsultation": 60.0,
    "nombrePiecesJointes": 2,
    "nomBeneficiaire": "Samira",
    "dateDepotDossier": "2024-11-11",
    "traitements": [
      {
        "codeBarre": "1122334455",
        "existe": true,
        "nomMedicament": "Amoxicilline",
        "typeMedicament": "Antibiotique",
        "prixMedicament": 12.0
      }
    ]
  }
]
