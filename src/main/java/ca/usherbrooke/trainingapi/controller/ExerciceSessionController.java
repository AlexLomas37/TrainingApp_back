package ca.usherbrooke.trainingapi.controller;

import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.ExerciceSession;
import ca.usherbrooke.trainingapi.model.TrainingSession;
import ca.usherbrooke.trainingapi.repository.ExerciceRepository;
import ca.usherbrooke.trainingapi.repository.ExerciceSessionRepository;
import ca.usherbrooke.trainingapi.repository.TrainingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExerciceSessionController {

    @Autowired
    private ExerciceSessionRepository exerciceSessionRepository;
    @Autowired
    private ExerciceRepository exerciceRepository;
    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

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

    @GetMapping("/exercice-sessions/exercice/{idExo}")
    public Iterable<ExerciceSession> getExerciceSessionsByExoId(@PathVariable int idExo) {
        return exerciceSessionRepository.findByExerciceId(idExo);
    }

    /*@GetMapping("/exercice-sessions/training-session/{idTrainingSession}")
    public Iterable<ExerciceSession> getExerciceSessionsByTrainingSessionId(@PathVariable int idTrainingSession) {
        //return exerciceSessionRepository.findByTrainingSessionId(idTrainingSession);
    }*/

    @PostMapping("/exercice-sessions")
    public ExerciceSession createExerciceSession(@RequestBody ExerciceSession exerciceSession) {
        Exercice exercice = exerciceRepository
                .findById(exerciceSession.getExercice().getId())
                .orElseThrow(() -> new RuntimeException("Exercice non trouvé"));
        exerciceSession.setExercice(exercice);
        TrainingSession trainingSession = trainingSessionRepository
                .findById(exerciceSession.getTrainingSession().getId())
                .orElseThrow(() -> new RuntimeException("Session d'entraînement non trouvée"));
        exerciceSession.setTrainingSession(trainingSession);
        return exerciceSessionRepository.save(exerciceSession);
    }

    @DeleteMapping("/exercice-sessions/{id}")
    public void deleteExerciceSession(@PathVariable int id) {
        ExerciceSession exerciceSession = exerciceSessionRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Session d'exercice non trouvée"));
        exerciceSessionRepository.delete(exerciceSession);
    }
}
