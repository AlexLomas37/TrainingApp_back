package ca.usherbrooke.trainingapi.controller;

import ca.usherbrooke.trainingapi.Services.TrainingService;
import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * Contrôleur gérant les opérations liées aux entraînements.
 */
@RestController
@RequestMapping("/trainings")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    /**
     * Récupère la liste de tous les entraînements.
     *
     * @return la liste des entraînements
     */
    @GetMapping
    public Iterable<Training> getTrainings() {
        return trainingService.getTrainings();
    }

    /**
     * Récupère un entraînement par son identifiant.
     *
     * @param id l'identifiant de l'entraînement
     * @return l'objet Training correspondant ou null
     */
    @GetMapping("/{id}")
    public Training getTrainingById(@PathVariable int id) {
        return trainingService.getTrainingById(id);
    }

    /**
     * Récupère la liste des exercices associés à un entraînement.
     *
     * @param id l'identifiant de l'entraînement
     * @return les exercices associés à l'entraînement
     */
    @GetMapping("/{id}/exercices")
    public Iterable<Exercice> getTrainingExercices(@PathVariable int id) {
        return trainingService.getExercicesByTrainingId(id);
    }

    /**
     * Crée un nouvel entraînement.
     *
     * @param training l'entraînement à créer
     * @return l'entraînement créé
     */
    @PostMapping
    public Training createTraining(@RequestBody Training training) {
        return trainingService.saveTraining(training);
    }

    /**
     * Met à jour un entraînement existant en ne modifiant que les champs spécifiés.
     *
     * @param id l'identifiant de l'entraînement à mettre à jour
     * @param training les nouvelles données de l'entraînement
     * @return l'entraînement mis à jour
     */
    @PatchMapping("/{id}")
    public Training patchTraining(@PathVariable int id, @RequestBody Training training) {
        return trainingService.patchTraining(id, training);
    }

    /**
     * Met à jour un entraînement existant.
     *
     * @param id l'identifiant de l'entraînement à mettre à jour
     * @param training les nouvelles données de l'entraînement
     * @return l'entraînement mis à jour
     */
    @PutMapping("/{id}")
    public Training updateTraining(@PathVariable int id, @RequestBody Training training) {
        return trainingService.updateTraining(id, training);
    }

    /**
     * Supprime un entraînement par son identifiant.
     *
     * @param id l'identifiant de l'entraînement à supprimer
     */
    @DeleteMapping("/{id}")
    public void deleteTraining(@PathVariable int id) {
        trainingService.deleteTrainingById(id);
    }
}
