package ca.usherbrooke.trainingapi.controller;

import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.repository.ExerciceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur gérant les opérations liées aux exercices.
 */
@RestController
public class ExerciceController {

    /**
     * Repository pour accéder aux données des exercices.
     */
    @Autowired
    private ExerciceRepository exerciceRepository;

    /**
     * Récupère la liste de tous les exercices.
     *
     * @return la liste des exercices
     */
    @GetMapping("/exercices")
    public Iterable<Exercice> getExercices() {
        return exerciceRepository.findAll();
    }

    /**
     * Récupère un exercice par son identifiant.
     *
     * @param id l'identifiant de l'exercice
     * @return l'exercice correspondant ou null
     */
    @GetMapping("/exercices/{id}")
    public Exercice getExerciceById(@PathVariable int id) {
        return exerciceRepository.findById(id).orElse(null);
    }
}
