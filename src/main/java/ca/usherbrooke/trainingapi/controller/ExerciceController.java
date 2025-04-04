package ca.usherbrooke.trainingapi.controller;

import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.Training;
import ca.usherbrooke.trainingapi.repository.ExerciceRepository;
import ca.usherbrooke.trainingapi.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur gérant les opérations liées aux exercices.
 */
@RestController
@RequestMapping("/exercices")
public class ExerciceController {

    /**
     * Repository pour accéder aux données des exercices.
     */
    @Autowired
    private ExerciceRepository exerciceRepository;

    /**
     * Repository pour accéder aux données des entraînements.
     */
    @Autowired
    private TrainingRepository trainingRepository;

    /**
     * Récupère la liste de tous les exercices.
     *
     * @return la liste des exercices
     */
    @GetMapping
    public Iterable<Exercice> getExercices() {
        return exerciceRepository.findAll();
    }

    /**
     * Récupère un exercice par son identifiant.
     *
     * @param id l'identifiant de l'exercice
     * @return l'exercice correspondant ou null
     */
    @GetMapping("/{id}")
    public Exercice getExerciceById(@PathVariable int id) {
        return exerciceRepository.findById(id).orElse(null);
    }

    /**
     * Crée un nouvel exercice.
     *
     * @param exercice l'exercice à créer
     * @return l'exercice créé
     */
    @PostMapping
    public Exercice createExercice(@RequestBody Exercice exercice) {
        Training training = trainingRepository
                .findById(exercice.getTraining().getId())
                .orElseThrow(() -> new RuntimeException("Entrainement non trouvé"));
        exercice.setTraining(training);
        return exerciceRepository.save(exercice);
    }

    /**
     * Met à jour un exercice existant.
     *
     * @param id l'identifiant de l'exercice à mettre à jour
     * @param exercice les nouvelles données de l'exercice
     * @return l'exercice mis à jour
     */
    @PutMapping("/{id}")
    public Exercice updateExercice(@PathVariable int id, @RequestBody Exercice exercice) {
        Exercice existingExercice = exerciceRepository.findById(id).orElse(null);
        if (existingExercice != null) {
            existingExercice.setName(exercice.getName());
            existingExercice.setDescription(exercice.getDescription());
            return exerciceRepository.save(existingExercice);
        }
        return null;
    }

    /**
     * Met à jour partiellement un exercice existant.
     *
     * @param id l'identifiant de l'exercice à mettre à jour
     * @param exercice les nouvelles données de l'exercice
     * @return l'exercice mis à jour
     */
    @PatchMapping("/{id}")
    public Exercice patchExercice(@PathVariable int id, @RequestBody Exercice exercice) {
        Exercice existingExercice = exerciceRepository.findById(id).orElse(null);
        if (existingExercice != null) {
            if (exercice.getName() != null) {
                existingExercice.setName(exercice.getName());
            }
            if (exercice.getDescription() != null) {
                existingExercice.setDescription(exercice.getDescription());
            }
            if (exercice.getTime() > 0) {
                existingExercice.setTime(exercice.getTime());
            }
            if(exercice.getRepetitions() >= 0) {
                existingExercice.setRepetitions(exercice.getRepetitions());
            }
            if(exercice.getTraining() != null) {
                Training training = trainingRepository
                        .findById(exercice.getTraining().getId())
                        .orElseThrow(() -> new RuntimeException("Entrainement non trouvé"));
                existingExercice.setTraining(training);
            }

            return exerciceRepository.save(existingExercice);
        }
        return null;
    }

    /**
     * Supprime un exercice par son identifiant.
     *
     * @param id l'identifiant de l'exercice à supprimer
     */
    @DeleteMapping("/{id}")
    public void deleteExercice(@PathVariable int id) {
        exerciceRepository.deleteById(id);
    }
}
