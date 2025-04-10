package ca.usherbrooke.trainingapi.controller;

import ca.usherbrooke.trainingapi.Services.ExerciceSessionService;
import ca.usherbrooke.trainingapi.model.ExerciceSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExerciceSessionController {

    @Autowired
    private ExerciceSessionService exerciceSessionService;

    /**
     * Récupère la liste de toutes les sessions d'exercice.
     *
     * @return la liste des sessions d'exercice
     */
    @GetMapping("/exercice-sessions")
    public Iterable<ExerciceSession> getAllExerciceSessions() {
        return exerciceSessionService.getExerciceSessions();
    }

    /**
     * Récupère une session d'exercice par son identifiant.
     *
     * @param id l'identifiant de la session d'exercice
     * @return la session d'exercice correspondante ou null
     */
    @GetMapping("/exercice-sessions/{id}")
    public ExerciceSession getExerciceSessionById(@PathVariable int id) {
        return exerciceSessionService.getExerciceSessionById(id);
    }

    /**
     * Récupère la liste des sessions d'exercice associées à un exercice.
     *
     * @param idExo l'identifiant de l'exercice
     * @return les sessions d'exercice associées à l'exercice
     */
    @GetMapping("/exercice-sessions/exercice/{idExo}")
    public Iterable<ExerciceSession> getExerciceSessionsByExoId(@PathVariable int idExo) {
        return exerciceSessionService.getExercicesSessionsByExoId(idExo);
    }

    /*@GetMapping("/exercice-sessions/training-session/{idTrainingSession}")
    public Iterable<ExerciceSession> getExerciceSessionsByTrainingSessionId(@PathVariable int idTrainingSession) {
        //return exerciceSessionRepository.findByTrainingSessionId(idTrainingSession);
    }*/

    /**
     * Crée une nouvelle session d'exercice.
     *
     * @param exerciceSession la session d'exercice à créer
     * @return la session d'exercice créée
     */
    @PostMapping("/exercice-sessions")
    public ExerciceSession createExerciceSession(@RequestBody ExerciceSession exerciceSession) {
        return exerciceSessionService.saveExerciceSession(exerciceSession);
    }

    /**
     * Met à jour une session d'exercice existante.
     *
     * @param id l'identifiant de la session d'exercice à mettre à jour
     */
    @DeleteMapping("/exercice-sessions/{id}")
    public void deleteExerciceSession(@PathVariable int id) {
        exerciceSessionService.deleteExerciceSession(id);
    }
}
