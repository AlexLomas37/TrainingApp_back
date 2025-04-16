# TrainingApp

Projet de session pour le cours IFT785 – Approches orientées objets

## Context

L'application permet de gérer des entraînements dans divers disciplines comme le sport ou les jeux vidéo.
Elle est conçue pour être extensible et modulaire, facilitant l'ajout de nouvelles fonctionnalités à l'avenir.

## Features
 - Gestion des disciplines
 - Gestion des entraînements
 - Gestion des exercices
 - Gestion des sessions d'entraînement
 - Gestion des session d'exercices
 - Retour de données statistiques sur les entraînements et des exercices

## Design pattern implémentés
- Factory : Récupère dynamiquement un objet Training ou Exercice pour choisir la stratégie de statistiques à appliquer.
- Strategy : Permet de choisir la stratégie de statistiques à appliquer sur un entraînement ou un exercice.
- Decorator : Permet de retourner des exercices/trainings avec leurs statistiques sans changer la structure de base.
- Repository : Permet de gérer la persistante des données.
- Command : (Implémenté dans l'application front) Permet de gérer le lancement et l'arret des entrainements.
- Adapter : (Implémenté dans l'application front) Permet la communication entre les données de l'api et ApexChart.
- Dependancy Injection : Permet de gérer les dépendances entre les classes et d'injecter des objets dans les classes qui en ont besoin.

## Installation

Cette API peut être utilisée avec une interface graphique disponible sur ce dépot git : 

### Backend
- Cloner le dépot : `giit clone https://github.com/AlexLomas37/TrainingApp_back.git`
- Ouvrir le projet dans IntelleJ IDEA
- Configurer le JDK 17
- Configurer la base de données H2
- Lancer le projet ou alors générer un fichier war et le déployer sur un serveur d'application comme Tomcat.
- Accéder à l'API via http://localhost:8090

### Frontend (optionnel)
- Cloner le dépot : `git clone https://github.com/AlexLomas37/TrainingApp_front.git`
- Ouvrir un terminal à la racine du projet
- Lancer la commande `npm install` pour installer les dépendances
- Lancer la commande `npm run dev` pour démarrer le serveur de développement
- Accéder à l'application via http://localhost:5000

## Routes
Lien vers swagger-ui : http://localhost:8090/swagger-ui/index.html

### Test
| Méthode | Route  | Description                                              |
|---------|--------|----------------------------------------------------------|
| GET     | /      | Affiche un message pour dire que l'api est fonctionnelle |

### Discipline
| Méthode | Route                       | Description                                      |
|---------|-----------------------------|--------------------------------------------------|
| GET     | /disciplines                | Récupère la liste des disciplines                |
| GET     | /disciplines/{id}           | Récupère une discipline par son identifiant      |
| GET     | /disciplines/{id}/trainings | Récupère la liste des trainings d'une discipline |
| POST    | /disciplines                | Crée une nouvelle discipline                     |
| PUT     | /disciplines/{id}           | Met à jour une discipline existante              |
| PATCH   | /disciplines/{id}           | Met à jour partiellement une discipline          |
| DELETE  | /disciplines/{id}           | Supprime une discipline                          |

### Entraînement
| Méthode | Route                     | Description                                       |
|---------|---------------------------|---------------------------------------------------|
| GET     | /trainings                | Récupère la liste des entraînements               |
| GET     | /trainings/{id}           | Récupère un entraînement par son identifiant      |
| GET     | /trainings/{id}/exercices | Récupère la liste des exercices d'un entraînement |
| POST    | /trainings                | Crée un nouvel entraînement                       |
| PUT     | /trainings/{id}           | Met à jour un entraînement existant               |
| PATCH   | /trainings/{id}           | Met à jour partiellement un entraînement          |
| DELETE  | /trainings/{id}           | Supprime un entraînement                          |

### Exercice
| Méthode | Route                          | Description                                    |
|---------|--------------------------------|------------------------------------------------|
| GET     | /exercices                     | Récupère la liste des exercices                |
| GET     | /exercices/{id}                | Récupère un exercice par son identifiant       |
| POST    | /exercices                     | Crée un nouvel exercice                        |
| PUT     | /exercices/{id}                | Met à jour un exercice existant                |
| PATCH   | /exercices/{id}                | Met à jour partiellement un exercice           |
| DELETE  | /exercices/{id}                | Supprime un exercice                           |

### Session d'entraînement
| Méthode | Route                                    | Description                                                     |
|---------|------------------------------------------|-----------------------------------------------------------------|
| GET     | /training-sessions                       | Récupère la liste des sessions d'entraînement                   |
| GET     | /training-sessions/{id}                  | Récupère une session d'entraînement par son identifiant         |
| GET     | /training-sessions/training/{idTraining} | Récupère la liste des sessions d'entraînement d'un entraînement |
| POST    | /training-sessions                       | Crée une nouvelle session d'entraînement                        |
| PUT     | /training-sessions/{id}                  | Met à jour une session d'entraînement existante                 |
| PATCH   | /training-sessions/{id}                  | Met à jour partiellement une session d'entraînement             |
| DELETE  | /training-sessions/{id}                  | Supprime une session d'entraînement                             |

### Session d'exercice
| Méthode | Route                                    | Description                                             |
|---------|------------------------------------------|---------------------------------------------------------|
| GET     | /exercice-sessions                       | Récupère la liste des sessions d'exercices              |
| GET     | /exercice-sessions/{id}                  | Récupère une session d'exercice par son identifiant     |
| GET     | /exercice-sessions/exercice/{idExercice} | Récupère la liste des sessions d'exercice d'un exercice |
| POST    | /exercice-sessions                       | Crée une nouvelle session d'exercice                    |
| PUT     | /exercice-sessions/{id}                  | Met à jour une session d'exercice existante             |
| PATCH   | /exercice-sessions/{id}                  | Met à jour partiellement une session d'exercice         |
| DELETE  | /exercice-sessions/{id}                  | Supprime une session d'exercice                         |

### Statistiques
| Méthode | Route                               | Description                                                                         |
|---------|-------------------------------------|-------------------------------------------------------------------------------------|
| GET     | /statistics/trainings/total         | Récupère le total des                                                               |
| GET     | /statistics/trainings/heatmap       | Récupère la carte de fréquentations des sessions entraînements                      |
| GET     | /statistics/exercises/curve/nbTimes | Récupère les statistiques d'un exercice en fonction des x dernières fois            |
| GET     | /statistics/exercises/curve/dates   | Récupère les statistiques d'un exercice en fonction du temps                        |
| GET     | /statistics/exercises/avg/nbTimes   | Récupère les différentes moyennes pour un exercice en fonction des x dernières fois |
| GET     | /statistics/exercises/avg/dates     | Récupère les différentes moyennes pour un exercice en fonction du temps             |

### Type de statistiques
| Méthode | Route            | Description                                                                         |
|---------|------------------|-------------------------------------------------------------------------------------|
| GET     | /statistic-types | Récupère la liste des types de statistiques                                         |

### Décorateurs
| Méthode | Route                      | Description                                  |
|---------|----------------------------|----------------------------------------------|
| GET     | /decorators/trainings/{id} | Décore un entraînement avec ses statistiques |
| GET     | /decorators/exercices/{id} | Décore un exercice avec ses statistiques     |

Note : Il y a un fichier postman qui regroupe divers tests de routes à la racine du projet, dans le répertoire `Postman`.

## Technologies
 - Java 17
 - Spring Boot
 - mysql
 - IntelleJ IDEA

## Date de remise
 17 avril 2025

## Auteur
Alexandre Lomaszewicz