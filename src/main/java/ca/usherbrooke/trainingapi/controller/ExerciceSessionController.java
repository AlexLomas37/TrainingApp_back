package ca.usherbrooke.trainingapi.controller;

import ca.usherbrooke.trainingapi.model.ExerciceSession;
import ca.usherbrooke.trainingapi.repository.ExerciceSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExerciceSessionController {

    @Autowired
    private ExerciceSessionRepository exerciceSessionRepository;

    /**
     * Récupère la liste de toutes les sessions d'exercice.
     *
     * @return la liste des sessions d'exercice
     */
    @GetMapping("/exercice-sessions")
    public Iterable<ExerciceSession> getExerciceSessions() {
        return exerciceSessionRepository.findAll();
    }

    /**
     * Récupère une session d'exercice par son identifiant.
     *
     * @param id l'identifiant de la session d'exercice
     * @return la session d'exercice correspondante ou null
     */
    @GetMapping("/exercice-sessions/{id}")
    public ExerciceSession getExerciceSessionById(@PathVariable int id) {
        return exerciceSessionRepository.findById(id).orElse(null);
    }

    @GetMapping("/exercice-sessions/{idExo}")
    public Iterable<ExerciceSession> getExerciceSessionsByExoId(@PathVariable int idExo) {
        return exerciceSessionRepository.findByExerciceId(idExo);
    }
}
