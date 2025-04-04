package ca.usherbrooke.trainingapi.controller;

import ca.usherbrooke.trainingapi.model.TrainingSession;
import ca.usherbrooke.trainingapi.repository.TrainingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainingSessionController {

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    /**
     * Récupère la liste de toutes les sessions d'entraînement.
     *
     * @return la liste des sessions d'entraînement
     */
    @GetMapping("/training-sessions")
    public Iterable<TrainingSession> getTrainingSessions() {
        return trainingSessionRepository.findAll();
    }

    /**
     * Récupère une session d'entraînement par son identifiant.
     *
     * @param id l'identifiant de la session d'entraînement
     * @return la session d'entraînement correspondante ou null
     */
    @GetMapping("/training-sessions/{id}")
    public TrainingSession getTrainingSessionById(@PathVariable int id) {
        return trainingSessionRepository.findById(id).orElse(null);
    }

    /**
     * Récupère la liste des sessions d'entraînement associées à un entraînement.
     *
     * @param idTraining l'identifiant de l'entraînement
     * @return les sessions d'entraînement associées à l'entraînement
     */
    @GetMapping("/training-sessions/training/{idTraining}")
    public Iterable<TrainingSession> getTrainingSessionsByTrainingId(@PathVariable int idTraining) {
        return trainingSessionRepository.findByTrainingId(idTraining);
    }
}
