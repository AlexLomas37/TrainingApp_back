package ca.usherbrooke.trainingapi.controller;

import ca.usherbrooke.trainingapi.Services.ExerciceService;
import ca.usherbrooke.trainingapi.model.Exercice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur gérant les opérations liées aux exercices.
 */
@RestController
@RequestMapping("/exercices")
public class ExerciceController {

    @Autowired
    private ExerciceService exerciceService;

    /**
     * Récupère la liste de tous les exercices.
     *
     * @return la liste des exercices
     */
    @GetMapping
    public Iterable<Exercice> getExercices() {
        return exerciceService.getAllExercices();
    }

    /**
     * Récupère un exercice par son identifiant.
     *
     * @param id l'identifiant de l'exercice
     * @return l'exercice correspondant ou null
     */
    @GetMapping("/{id}")
    public Exercice getExerciceById(@PathVariable int id) {
        return exerciceService.getExerciceById(id);
    }

    /**
     * Crée un nouvel exercice.
     *
     * @param exercice l'exercice à créer
     * @return l'exercice créé
     */
    @PostMapping
    public Exercice createExercice(@RequestBody Exercice exercice) {
        return exerciceService.saveExercice(exercice);
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
        return exerciceService.updateExercice(id, exercice);
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
        return exerciceService.updateExerciceByPatch(id, exercice);
    }

    /**
     * Supprime un exercice par son identifiant.
     *
     * @param id l'identifiant de l'exercice à supprimer
     */
    @DeleteMapping("/{id}")
    public void deleteExercice(@PathVariable int id) {
        exerciceService.deleteExercice(id);
    }
}
